package io.tiklab.kaelthas.collection.service;

import com.alibaba.fastjson.JSON;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.tiklab.kaelthas.collection.dao.KuCollectionDao;
import io.tiklab.kaelthas.collection.utils.ConversionAllTypeUtil;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.kubernetes.monitor.model.KuMonitor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 定时获取概况页的信息
 */
@Component
public class ClusterItemOverview {

    @Autowired
    private KuCollectionDao kuCollectionDao;

    @Autowired
    private HistoryService historyService;

    //定时采集k8s的信息,用于概况页面展示
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void getClusterOverview() {
        //获取所有的k8s信息,将集群的基本信息进行收集
        List<KuMonitor> kuMonitors = kuCollectionDao.findKuMonitor();

        if (kuMonitors.isEmpty()) {
            return;
        }

        List<History> list  = new LinkedList<>();

        List<KuMonitor> monitorList = getKubernetesMonitors(kuMonitors);

        for (KuMonitor kuMonitor : monitorList) {

            History history = getHistory(kuMonitor);
            list.add(history);
        }

        historyService.insertForList(list);

    }

    //模拟监控项的id(1-22),采集指定的监控项
    @NotNull
    private static List<KuMonitor> getKubernetesMonitors(List<KuMonitor> kuMonitors) {
        List<KuMonitor> monitorList = new LinkedList<>();

        for (int i = 1; i < 23; i++) {
            for (KuMonitor kuMonitor : kuMonitors) {
                KuMonitor monitor = new KuMonitor();
                monitor.setKuId(kuMonitor.getId());
                monitor.setName(kuMonitor.getName());
                monitor.setIp(kuMonitor.getIp());
                monitor.setPort(kuMonitor.getPort());
                monitor.setApiToken(kuMonitor.getApiToken());
                monitor.setKuItemId(String.valueOf(i));
                monitorList.add(monitor);
            }
        }
        return monitorList;
    }

    //使用拉取的k8s信息通过k8s的api来采集指标数据
    private static History getHistory(KuMonitor kuMonitor) {
        try {
            String token = kuMonitor.getApiToken(); // 替换为你的 Token
            String apiServerUrl = "https://" + kuMonitor.getIp() + ":" + kuMonitor.getPort(); // 替换为你的 API Server 地址

            // 配置 ApiClient
            ApiClient client = new ClientBuilder()
                    .setBasePath(apiServerUrl)
                    .setVerifyingSsl(false) // 是否验证 SSL 证书
                    .build();

            // 设置认证 Token
            client.setApiKeyPrefix("Bearer");
            client.setApiKey(token);

            // 将配置设置为默认
            Configuration.setDefaultApiClient(client);

            CoreV1Api api = new CoreV1Api();
            AppsV1Api appsV1Api = new AppsV1Api();
            History history = new History();
            history.setHostId(kuMonitor.getKuId());
            history.setMonitorId(kuMonitor.getKuItemId());
            history.setGatherTime(ConversionAllTypeUtil.getDataTimeNow());
            getClusterData(kuMonitor, history, api, appsV1Api);
            return history;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }


    //根据监控的类型来采集指定的数据
    private static void getClusterData(KuMonitor kuMonitor, History history, CoreV1Api api, AppsV1Api appsV1Api) throws ApiException {
        switch (kuMonitor.getKuItemId()) {
            case "1":
                try {
                    V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, null, null);
                    int size = nodeList.getItems().size();
                    history.setReportData(String.valueOf(size));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "2":
                try {
                    V1NamespaceList namespaceList = api.listNamespace(null, null, null, null, null, null, null, null, null, false);
                    history.setReportData(String.valueOf(namespaceList.getItems().size()));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "3":
                try {
                    int deploymentTotal = appsV1Api.listDeploymentForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems().size();
                    history.setReportData(String.valueOf(deploymentTotal));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "4":
                try {
                    int statefulSetTotal = appsV1Api.listStatefulSetForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems().size();
                    history.setReportData(String.valueOf(statefulSetTotal));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "5":
                try {
                    int daemonSetTotal = appsV1Api.listDaemonSetForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems().size();
                    history.setReportData(String.valueOf(daemonSetTotal));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "6":
                try {
                    int serviceTotal = api.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems().size();
                    history.setReportData(String.valueOf(serviceTotal));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "7":
                try {
                    List<V1Pod> pods = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems();
                    history.setReportData(String.valueOf(pods.size()));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "8":
                try {
                    List<V1Pod> pods8 = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems();
                    int containerTotal = pods8.stream().mapToInt(pod -> pod.getSpec().getContainers().size()).sum();
                    history.setReportData(String.valueOf(containerTotal));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "9":
                try {
                    V1NodeList nodeList9 = api.listNode(null, null, null, null, null, null, null, null, null, null);

                    // 获取所有节点的 CPU 总量和可分配量
                    int totalCapacity = 0;
                    for (V1Node node : nodeList9.getItems()) {
                        V1NodeStatus status = node.getStatus();
                        assert status != null;
                        String cpuCapacity = String.valueOf(Objects.requireNonNull(status.getCapacity()).get("cpu").getNumber());
                        if (StringUtils.isNotBlank(cpuCapacity)) {
                            totalCapacity += Integer.parseInt(cpuCapacity);
                        }
                    }
                    history.setReportData(String.valueOf(totalCapacity));
                } catch (ApiException | NumberFormatException e) {
                    break;
                }
                break;
            case "10":
                try {
                    V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
                    int totalCpuRequests = 0;
                    for (V1Pod pod : podList.getItems()) {
                        List<V1Container> containers = Objects.requireNonNull(pod.getSpec()).getContainers();
                        for (V1Container container : containers) {
                            V1ResourceRequirements resources = container.getResources();

                            if (Objects.requireNonNull(resources).getRequests() != null && resources.getRequests().get("cpu") != null) {
                                totalCpuRequests += resources.getRequests().get("cpu").getNumber().intValue();
                            }
                        }
                    }
                    history.setReportData(String.valueOf(totalCpuRequests));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "11":
                try {
                    V1PodList podList2 = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
                    int totalCpuLimits = 0;
                    for (V1Pod pod : podList2.getItems()) {
                        List<V1Container> containers = Objects.requireNonNull(pod.getSpec()).getContainers();
                        for (V1Container container : containers) {
                            V1ResourceRequirements resources = container.getResources();

                            if (Objects.requireNonNull(resources).getLimits() != null && resources.getLimits().get("cpu") != null) {
                                totalCpuLimits += resources.getLimits().get("cpu").getNumber().intValue();
                            }
                        }
                    }
                    history.setReportData(String.valueOf(totalCpuLimits));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "12":
                try {
                    // 获取所有节点的 CPU 总量和可分配量
                    V1NodeList nodeList12 = api.listNode(null, null, null, null, null, null, null, null, null, null);
                    int totalAllocatable = 0;
                    for (V1Node node : nodeList12.getItems()) {
                        V1NodeStatus status = node.getStatus();
                        String cpuAllocatable = String.valueOf(Objects.requireNonNull(Objects.requireNonNull(status).getAllocatable()).get("cpu").getNumber());
                        if (StringUtils.isNotBlank(cpuAllocatable)) {
                            totalAllocatable += Integer.parseInt(cpuAllocatable);
                        }
                    }
                    history.setReportData(String.valueOf(totalAllocatable));
                } catch (ApiException | NumberFormatException e) {
                    break;
                }
                break;
            case "13":
                try {
                    V1NodeList nodeList13 = api.listNode(null, null, null, null, null, null, null, null, null, null);

                    long totalMemory = 0;

                    // 遍历每个节点并计算内存资源总量
                    for (V1Node node : nodeList13.getItems()) {
                        // 获取内存容量 (total memory)
                        String memoryTotalStr = node.getStatus().getCapacity().get("memory").toSuffixedString();
                        totalMemory += convertMemoryToMi(memoryTotalStr);
                    }
                    history.setReportData(String.valueOf(totalMemory));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "14":
                try {
                    //单位为(M)
                    long totalMemoryRequests = 0;
                    // 获取所有命名空间中的 Pod
                    V1PodList podList14 = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, false);

                    // 遍历所有 Pod
                    for (V1Pod pod : podList14.getItems()) {
                        // 遍历 Pod 中的容器
                        if (pod.getSpec() != null) {
                            for (V1Container container : pod.getSpec().getContainers()) {
                                // 获取容器的资源请求
                                Map<String, Quantity> requests = container.getResources().getRequests();
                                if (requests != null) {
                                    Quantity memoryRequestStr = requests.get("memory");
                                    if (memoryRequestStr != null) {
                                        totalMemoryRequests += parseMemoryValue(memoryRequestStr.getNumber().toString());
                                    }
                                }
                            }
                        }
                    }
                    double result = totalMemoryRequests / 1024.00 / 1024.00;
                    String format3 = String.format("%.2f", result);
                    history.setReportData(format3);
                } catch (ApiException e) {
                    break;
                }
                break;
            case "15":
                try {
                    //单位为(M)
                    long totalMemoryLimits = 0;
                    // 获取所有命名空间中的 Pod
                    V1PodList podList15 = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, false);

                    // 遍历所有 Pod
                    for (V1Pod pod : podList15.getItems()) {
                        // 遍历 Pod 中的容器
                        if (pod.getSpec() != null) {
                            for (V1Container container : pod.getSpec().getContainers()) {
                                // 获取容器的资源限制
                                Map<String, Quantity> limits = container.getResources().getLimits();
                                if (limits != null) {
                                    Quantity memoryLimitStr = limits.get("memory");
                                    if (memoryLimitStr != null) {
                                        totalMemoryLimits += parseMemoryValue(memoryLimitStr.getNumber().toString());
                                    }
                                }
                            }
                        }
                    }
                    double result15 = totalMemoryLimits / 1024.00 / 1024.00;
                    String format2 = String.format("%.2f", result15);
                    history.setReportData(format2);
                } catch (ApiException e) {
                    break;
                }
                break;
            case "16":
                try {
                    long totalAllocatableMemory = 0;

                    V1NodeList nodeList16 = api.listNode(null, null, null, null, null, null, null, null, null, null);

                    // 遍历每个节点并计算内存资源总量
                    for (V1Node node : nodeList16.getItems()) {
                        // 获取节点名称
                        String nodeName = node.getMetadata().getName();

                        // 获取可分配内存 (allocatable memory)
                        String memoryAllocatableStr = node.getStatus().getAllocatable().get("memory").toSuffixedString();
                        totalAllocatableMemory += convertMemoryToMi(memoryAllocatableStr);

                    }
                    history.setReportData(String.format("%.2f", totalAllocatableMemory / 1024.00 / 1024.00));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "17":
                try {
                    long totalStorage = 0;
                    V1NodeList nodeList17 = api.listNode(null, null, null, null, null, null, null, null, null, false);
                    // 遍历每个节点，累加存储信息
                    for (V1Node node : nodeList17.getItems()) {
                        // 获取节点的存储总量
                        String totalStorageStr = node.getStatus().getCapacity().get("ephemeral-storage").getNumber().toString();
                        long nodeTotalStorage = Long.parseLong(totalStorageStr.replaceAll("[^0-9]", ""));
                        totalStorage += nodeTotalStorage;
                    }

                    history.setReportData(String.format("%.2f", totalStorage / Math.pow(2, 30)));
                } catch (ApiException | NumberFormatException e) {
                    break;
                }
                break;
            case "18":
                try {
                    long allocatableStorage = 0;
                    V1NodeList nodeList18 = api.listNode(null, null, null, null, null, null, null, null, null, false);
                    // 遍历每个节点，累加存储信息
                    for (V1Node node : nodeList18.getItems()) {
                        // 获取节点的可分配存储量
                        String allocatableStorageStr = node.getStatus().getAllocatable().get("ephemeral-storage").getNumber().toString();
                        long nodeAllocatableStorage = Long.parseLong(allocatableStorageStr.replaceAll("[^0-9]", ""));
                        allocatableStorage += nodeAllocatableStorage;
                    }

                    history.setReportData(String.format("%.2f", allocatableStorage / Math.pow(2, 30)));
                } catch (ApiException | NumberFormatException e) {
                    break;
                }
                break;
            case "19":
                try {
                    V1NodeList nodeList19 = api.listNode(null, null, null, null, null, null, null, null, null, null);

                    //节点的当前状态
                    // 获取所有节点的 CPU 总量和可分配量
                    List<Map<String, String>> list = getMapList(nodeList19);
                    String jsonString = JSON.toJSONString(list);
                    history.setReportData(jsonString);
                } catch (ApiException e) {
                    break;
                }
                break;
            case "20":
                try {
                    //当前部署状态
                    List<Map<String, String>> list20 = new ArrayList<>();
                    // 4. 遍历所有命名空间并获取 Deployment 列表
                    V1NamespaceList namespaceList20 = api.listNamespace(null, null, null, null, null, null, null, null, null, null);
                    for (V1Namespace item : namespaceList20.getItems()) {
                        String namespace = item.getMetadata().getName();
                        V1DeploymentList deploymentList = appsV1Api.listNamespacedDeployment(namespace, null, null, null, null, null, null, null, null, null, null);
                        for (V1Deployment deployment : deploymentList.getItems()) {
                            Map<String, String> map = getStringStringMap(deployment, namespace);
                            list20.add(map);
                        }
                    }
                    history.setReportData(JSON.toJSONString(list20));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "21":
                try {
                    //服务的当前状态
                    V1ServiceList serviceList = api.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
                    List<Map<String, String>> list21 = new ArrayList<>();

                    for (V1Service service : serviceList.getItems()) {
                        String serviceName = service.getMetadata().getName();
                        String namespace = service.getMetadata().getNamespace();

                        V1PodList podList21 = api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null, null);
                        Map<String, String> map = new HashMap<>();
                        boolean podHealthy = true;
                        for (V1Pod pod : podList21.getItems()) {
                            if (pod.getMetadata().getLabels() != null && pod.getMetadata().getLabels().get("app") != null) {
                                V1PodStatus podStatus = pod.getStatus();
                                String phase = podStatus.getPhase();

                                if (!"Running".equals(phase)) {
                                    podHealthy = false;
                                }
                            }
                        }
                        if (podHealthy) {
                            map.put("phase", "Running");
                            map.put("service", serviceName);
                        } else {
                            map.put("phase", "Abnormal");
                            map.put("service", serviceName);
                        }
                        list21.add(map);
                    }
                    history.setReportData(JSON.toJSONString(list21));
                } catch (ApiException e) {
                    break;
                }
                break;
            case "22":
                try {

                    // 获取所有命名空间中的 Pod
                    V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
                    List<V1Pod> pods = podList.getItems();

                    List<Object> list22 = new LinkedList<>();

                    for (V1Pod pod : pods) {
                        String podName = pod.getMetadata().getName();
                        String phase = pod.getStatus().getPhase();

                        // 统计非 Running 状态的 Pod
                        if (!"Running".equalsIgnoreCase(phase)) {
                            list22.add(phase);
                            list22.add(podName);
                        }
                    }
                    history.setReportData(JSON.toJSONString(list22));

                } catch (ApiException e) {
                    break;
                }
                break;
        }
    }

    //进行单位的转化
    private static long convertMemoryToMi(String memoryStr) {
        if (memoryStr.endsWith("Ki")) {
            return Long.parseLong(memoryStr.replace("Ki", "")) / 1024;
        } else if (memoryStr.endsWith("Mi")) {
            return Long.parseLong(memoryStr.replace("Mi", ""));
        } else if (memoryStr.endsWith("Gi")) {
            return Long.parseLong(memoryStr.replace("Gi", "")) * 1024;
        } else {
            // 默认返回 0 以防无法识别的单位
            return 0;
        }
    }

    //进行单位的转换
    private static int parseMemoryValue(String memoryStr) {
        if (memoryStr == null || memoryStr.isEmpty()) {
            return 0;
        }
        // 假设内存单位是 Ki、Mi、Gi，转换为字节
        int value = Integer.parseInt(memoryStr.replaceAll("[^0-9]", ""));
        if (memoryStr.endsWith("Ki")) {
            value = value / 1024;
        } else if (memoryStr.endsWith("Mi")) {
            value = value / (1024 * 1024);
        } else if (memoryStr.endsWith("Gi")) {
            value = value / (1024 * 1024 * 1024);
        }
        // 添加其他单位转换逻辑，如需要
        return value;
    }

    //获取指标Deployment Status
    @NotNull
    private static Map<String, String> getStringStringMap(V1Deployment deployment, String namespace) {
        Map<String, String> map = new HashMap<>();
        map.put("namespace", namespace);
        map.put("deployment", deployment.getMetadata().getName());
        map.put("replicas", Objects.requireNonNull(Objects.requireNonNull(deployment.getStatus()).getReplicas()).toString());
        int statusNum = 0;
        for (V1DeploymentCondition condition : deployment.getStatus().getConditions()) {
            if ("False".equals(condition.getStatus())) {
                statusNum += statusNum;
            }
        }
        if (statusNum == 0) {
            map.put("status", "True");
        } else {
            map.put("status", "False");
        }
        return map;
    }

    //获取指标Node Status
    @NotNull
    private static List<Map<String, String>> getMapList(V1NodeList nodeList) {
        List<Map<String, String>> list = new ArrayList<>();
        for (V1Node node : nodeList.getItems()) {
            for (V1NodeCondition condition : node.getStatus().getConditions()) {
                Map<String, String> map = new HashMap<>();
                map.put("name", Objects.requireNonNull(node.getMetadata()).getName());
                map.put("reason", condition.getReason());
                map.put("status", condition.getStatus());
                map.put("type", condition.getType());
                list.add(map);
            }
        }
        return list;
    }

}
