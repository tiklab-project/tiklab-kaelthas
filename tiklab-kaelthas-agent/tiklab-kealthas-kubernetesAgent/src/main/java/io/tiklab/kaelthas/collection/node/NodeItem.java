package io.tiklab.kaelthas.collection.node;

import com.alibaba.fastjson.JSON;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.openapi.models.V1NodeCondition;
import io.kubernetes.client.openapi.models.V1PodList;
import io.tiklab.kaelthas.collection.utils.ConversionAllTypeUtil;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.model.KuMonitor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * node的监控项采集,方法类
 */
public class NodeItem {
    public static void getNodeReportData(KuMonitor kuMonitor, History history, CoreV1Api api, AppsV1Api appsV1Api) {

        String nodeName = ConversionAllTypeUtil.getParamValue(kuMonitor.getExpression());
        if (StringUtils.isBlank(nodeName)) {
            return;
        }

        switch (kuMonitor.getKuItemId()) {
            case "101":
                try {
                    V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, "node=" + nodeName, null, null, null, null);
                    history.setReportData(String.valueOf(podList.getItems().size()));
                    break;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "102":
                //查看pod下的condition状态列表
                try {
                    V1Node node = api.readNode(nodeName, null);
                    // 输出节点状态
                    List<String> stringList = new ArrayList<>();
                    for (V1NodeCondition condition : node.getStatus().getConditions()) {
                        stringList.add(condition.getType());
                    }
                    history.setReportData(JSON.toJSONString(stringList));
                    break;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "103":

                try {
                    V1Node node = api.readNode(nodeName, null);
                    history.setReportData(String.valueOf(node.getStatus().getCapacity().get("cpu").getNumber()));
                    break;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "106":
                try {
                    V1Node node = api.readNode(nodeName, null);
                    history.setReportData(String.valueOf(node.getStatus().getAllocatable().get("cpu").getNumber()));
                    break;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "107":
                //单位G
                try {
                    V1Node node = api.readNode(nodeName, null);
                    BigDecimal memory = Objects.requireNonNull(node.getStatus().getCapacity()).get("memory").getNumber();
                    BigDecimal divide = memory.divide(new BigDecimal(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP);
                    history.setReportData(String.valueOf(divide));
                    break;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "110":
                //单位G
                try {
                    V1Node node = api.readNode(nodeName, null);
                    BigDecimal memory = Objects.requireNonNull(Objects.requireNonNull(node.getStatus()).getAllocatable()).get("memory").getNumber();
                    BigDecimal divide = memory.divide(new BigDecimal(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP);
                    history.setReportData(String.valueOf(divide));
                    break;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "111":
                try {
                    V1Node node = api.readNode(nodeName, null);
                    String totalStorageStr = Objects.requireNonNull(Objects.requireNonNull(node.getStatus()).getCapacity()).get("ephemeral-storage").getNumber().toString();
                    double nodeTotalStorage = Double.parseDouble(totalStorageStr.replaceAll("[^0-9]", ""));
                    double value = Math.round(nodeTotalStorage / (1024 * 1024 * 1024) * 100.0) / 100.0;
                    history.setReportData(String.valueOf(value));
                    break;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            case "112":
                try {
                    V1Node node = api.readNode(nodeName, null);
                    String totalStorageStr = Objects.requireNonNull(Objects.requireNonNull(node.getStatus()).getAllocatable()).get("ephemeral-storage").getNumber().toString();
                    double nodeTotalStorage = Double.parseDouble(totalStorageStr.replaceAll("[^0-9]", ""));
                    double value = Math.round(nodeTotalStorage / (1024 * 1024 * 1024) * 100.0) / 100.0;
                    history.setReportData(String.valueOf(value));
                    break;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
