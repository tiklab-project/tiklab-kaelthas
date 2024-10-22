package io.tiklab.kaelthas.collection.serviceItem;

import com.alibaba.fastjson.JSON;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.tiklab.kaelthas.collection.utils.ConversionAllTypeUtil;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.model.KuMonitor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ServiceItem {

    public static void getNServiceReportData(KuMonitor kuMonitor, History history, CoreV1Api api, AppsV1Api appsV1Api) {
        String params = ConversionAllTypeUtil.getParamValue(kuMonitor.getExpression());
        if (StringUtils.isBlank(params)) {
            return;
        }

        String[] split = params.split(",");

        if (split.length != 3) {
            return;
        }


        switch (kuMonitor.getKuItemId()){
            case "201":

                try {
                    V1PodList podList = api.listNamespacedPod(split[0], null, null, null, null, null, null, null, null, null, null);
                    int podCount = 0;
                    for (var pod : podList.getItems()) {
                        if (pod.getMetadata().getLabels() != null && split[1].equals(pod.getMetadata().getLabels().get(split[2]))) {
                            podCount++;
                        }
                    }
                    history.setReportData(String.valueOf(podCount));
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "202":

                try {
                    V1PodList podList = api.listNamespacedPod(split[0], null, null, null, null, null, null, null, null, null, null);
                    List<Map<String, String>> mapList = new ArrayList<>();
                    for (var pod : podList.getItems()) {
                        if (pod.getMetadata().getLabels() != null && split[1].equals(pod.getMetadata().getLabels().get(split[2]))) {
                            Map<String, String> map = new HashMap<>();

                            map.put("name", pod.getMetadata().getName());
                            map.put("phase", pod.getStatus().getPhase());
                            mapList.add(map);
                        }
                    }
                    history.setReportData(JSON.toJSONString(mapList));
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "203":

                try {
                    V1ServiceList serviceList = api.listNamespacedService(split[0], null, null, null, null, null, null, null, null, null, null);
                    String serviceName = serviceList.getItems().stream()
                            .map(service -> service.getMetadata().getName())
                            .filter(split[1]::equals)
                            .findFirst()
                            .orElse(null);

                    if (serviceName == null) {
                        return;
                    }

                    V1PodList podList = api.listNamespacedPod(split[0], null, null, null, null, null, null, null, null, null, null);

                    BigDecimal cpuRequestCount = BigDecimal.ZERO;


                    for (var pod : podList.getItems()) {
                        if (pod.getMetadata().getLabels() != null && serviceName.equals(pod.getMetadata().getLabels().get(split[2]))) {
                            for (V1Container container : pod.getSpec().getContainers()) {
                                BigDecimal cpuRequest = container.getResources().getRequests().get("cpu").getNumber();

                                cpuRequestCount = cpuRequestCount.add(cpuRequest);
                            }
                        }
                    }
                    history.setReportData(String.valueOf(cpuRequestCount.multiply(new BigDecimal(1000))));

                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "204":
                try {
                    V1ServiceList serviceList = api.listNamespacedService(split[0], null, null, null, null, null, null, null, null, null, null);
                    String serviceName = serviceList.getItems().stream()
                            .map(service -> service.getMetadata().getName())
                            .filter(split[1]::equals)
                            .findFirst()
                            .orElse(null);

                    if (serviceName == null) {
                        return;
                    }

                    V1PodList podList = api.listNamespacedPod(split[0], null, null, null, null, null, null, null, null, null, null);

                    BigDecimal cpuLimitCount = BigDecimal.ZERO;


                    for (var pod : podList.getItems()) {
                        if (pod.getMetadata().getLabels() != null && serviceName.equals(pod.getMetadata().getLabels().get(split[2]))) {
                            for (V1Container container : pod.getSpec().getContainers()) {
                                BigDecimal cpuLimit = container.getResources().getLimits().get("cpu").getNumber();

                                cpuLimitCount = cpuLimitCount.add(cpuLimit);
                            }
                        }
                    }
                    history.setReportData(String.valueOf(cpuLimitCount.multiply(new BigDecimal(1000))));

                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "205":

                try {
                    V1ServiceList serviceList = api.listNamespacedService(split[0], null, null, null, null, null, null, null, null, null, null);
                    String serviceName = serviceList.getItems().stream()
                            .map(service -> service.getMetadata().getName())
                            .filter(split[1]::equals)
                            .findFirst()
                            .orElse(null);

                    if (serviceName == null) {
                        return;
                    }

                    V1PodList podList = api.listNamespacedPod(split[0], null, null, null, null, null, null, null, null, null, null);


                    BigDecimal memoryRequestCount = BigDecimal.ZERO;

                    for (var pod : podList.getItems()) {
                        if (pod.getMetadata().getLabels() != null && serviceName.equals(pod.getMetadata().getLabels().get(split[2]))) {
                            for (V1Container container : pod.getSpec().getContainers()) {
                                BigDecimal memoryRequest = Objects.requireNonNull(Objects.requireNonNull(container.getResources()).getRequests()).get("memory").getNumber();

                                memoryRequestCount = memoryRequestCount.add(memoryRequest);
                            }
                        }
                    }
                    history.setReportData(String.valueOf(memoryRequestCount.divide(new BigDecimal(1024),2, RoundingMode.DOWN).divide(new BigDecimal(1024),2, RoundingMode.DOWN)));

                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "206":

                try {
                    V1ServiceList serviceList = api.listNamespacedService(split[0], null, null, null, null, null, null, null, null, null, null);
                    String serviceName = serviceList.getItems().stream()
                            .map(service -> Objects.requireNonNull(service.getMetadata()).getName())
                            .filter(split[1]::equals)
                            .findFirst()
                            .orElse(null);

                    if (serviceName == null) {
                        return;
                    }

                    V1PodList podList = api.listNamespacedPod(split[0], null, null, null, null, null, null, null, null, null, null);

                    BigDecimal memoryLimitCount = BigDecimal.ZERO;


                    for (var pod : podList.getItems()) {
                        if (Objects.requireNonNull(pod.getMetadata()).getLabels() != null && serviceName.equals(pod.getMetadata().getLabels().get(split[2]))) {
                            for (V1Container container : Objects.requireNonNull(pod.getSpec()).getContainers()) {
                                BigDecimal memoryLimit = Objects.requireNonNull(Objects.requireNonNull(container.getResources()).getLimits()).get("memory").getNumber();

                                memoryLimitCount = memoryLimitCount.add(memoryLimit);
                            }
                        }
                    }
                    history.setReportData(String.valueOf(memoryLimitCount.divide(new BigDecimal(1024),2, RoundingMode.DOWN).divide(new BigDecimal(1024),2, RoundingMode.DOWN)));
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }

        }
    }
}
