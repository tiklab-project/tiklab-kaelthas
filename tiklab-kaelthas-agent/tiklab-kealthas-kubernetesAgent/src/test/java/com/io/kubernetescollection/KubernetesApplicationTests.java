package com.io.kubernetescollection;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class KubernetesApplicationTests {

    private static final String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg"; // 替换为你的 Token
    private static final String apiServerUrl = "https://172.11.1.40:6443"; // 替换为你的 API Server 地址

    private static final CoreV1Api api = new CoreV1Api();

    private static final AppsV1Api appsV1Api = new AppsV1Api();

    static {
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
    }


    @Test
    void contextLoads() throws ApiException {
        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

        CoreV1Api apiInstance = new CoreV1Api();

        V1PodList podList = apiInstance.listNamespacedPod("default", null, null, null, null, "app=my-app", null, null, null, null, null);

        for (V1Pod pod : podList.getItems()) {
            List<V1Container> containers = pod.getSpec().getContainers();
            for (V1Container container : containers) {
                String cpuRequest = container.getResources().getRequests().get("cpu").toSuffixedString();
                String memRequest = container.getResources().getRequests().get("memory").toSuffixedString();
                String cpuLimit = container.getResources().getLimits().get("cpu").toSuffixedString();
                String memLimit = container.getResources().getLimits().get("memory").toSuffixedString();

                System.out.println("Pod Name: " + pod.getMetadata().getName());
                System.out.println("Container Name: " + container.getName());
                System.out.println("CPU Request: " + cpuRequest);
                System.out.println("Memory Request: " + memRequest);
                System.out.println("CPU Limit: " + cpuLimit);
                System.out.println("Memory Limit: " + memLimit);
            }
        }

        V1ServiceList serviceList = apiInstance.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
        for (V1Service service : serviceList.getItems()) {
            System.out.println("Service Name: " + service.getMetadata().getName() + ", NameSpace: " + service.getMetadata().getNamespace());
        }

        // 获取指定命名空间和服务名称的服务对象
        V1Service service = apiInstance.readNamespacedService("my-service", "default", null);

        // 获取服务的标签选择器
        Map<String, String> selector = service.getSpec().getSelector();

        if (selector != null) {
            System.out.println("Service Selector:");
            System.out.println(selector);
            selector.forEach((key, value) -> System.out.println(key + ": " + value));
        }

        // 创建 CoreV1Api 实例
        CoreV1Api coreApi = new CoreV1Api();


        /*if (selector != null) {
            // 查找与服务匹配的所有 Pods
            String labelSelector = selector.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            List<V1Pod> pods = coreApi.listNamespacedPod("default", null, null, null, labelSelector, null, null, null, null, null, null).getItems();

            Map<String, Long> cpuUsage = new HashMap<>();

            // 计算每个 Pod 的 CPU 使用情况
            for (V1Pod pod : pods) {
                String podName = pod.getMetadata().getName();
                // 在这里，假设你有其他逻辑来获取 Pod 的 CPU 使用情况
                // 由于 Kubernetes API 不提供直接的 CPU 使用信息，你需要使用 Prometheus 或其他工具
                // 示例中省略了实际的 CPU 使用获取步骤
                long cpu = getPodCPUUsage(podName);
                cpuUsage.put(podName, cpu);
            }

            // 输出 CPU 使用情况
            cpuUsage.forEach((pod, cpu) -> System.out.println("Pod: " + pod + " CPU Usage: " + cpu + "m"));

        }*/


    }

    @Test
    void getPodMetricsList() {

        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

        String namespace = "default"; // 替换为实际的命名空间
        String podName = "my-pod"; // 替换为实际的 Pod 名称

        String metricsUrl = String.format(apiServerUrl, namespace, podName);

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(metricsUrl)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseData);

                // 解析和打印 CPU 和内存使用情况
                JsonNode containersNode = jsonNode.path("containers");
                if (containersNode.isArray()) {
                    for (JsonNode containerNode : containersNode) {
                        String containerName = containerNode.path("name").asText();
                        String cpuUsage = containerNode.path("usage").path("cpu").asText();
                        String memoryUsage = containerNode.path("usage").path("memory").asText();

                        System.out.println("Container: " + containerName);
                        System.out.println("  CPU Usage: " + cpuUsage);
                        System.out.println("  Memory Usage: " + memoryUsage);
                    }
                }
            } else {
                System.err.println("Failed to fetch metrics: " + response.code() + " " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void getAllMemory() {
        try {

            String apiServerUrl = "https://172.11.1.40:6443";
            String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

            // 初始化总内存指标
            long totalMemory = 0;
            long totalAllocatableMemory = 0;
            long totalMemoryRequests = 0;
            long totalMemoryLimits = 0;

            // 列出所有节点
            V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, null, false);

            // 遍历每个节点并计算内存资源总量
            for (V1Node node : nodeList.getItems()) {
                // 获取节点名称
                String nodeName = node.getMetadata().getName();

                // 获取内存容量 (total memory)
                String memoryTotalStr = node.getStatus().getCapacity().get("memory").toSuffixedString();
                totalMemory += convertMemoryToMi(memoryTotalStr);

                // 获取可分配内存 (allocatable memory)
                String memoryAllocatableStr = node.getStatus().getAllocatable().get("memory").toSuffixedString();
                totalAllocatableMemory += convertMemoryToMi(memoryAllocatableStr);

                // 获取节点上的 Pod 的内存请求和限制
                totalMemoryRequests += getNodeMemoryRequests(api, nodeName);
                totalMemoryLimits += getNodeMemoryLimits(api, nodeName);
            }

            // 打印总内存指标
            System.out.println("Total Memory: " + totalMemory + " Mi");
            System.out.println("Total Memory Allocatable: " + totalAllocatableMemory + " Mi");
            System.out.println("Total Memory Requests: " + totalMemoryRequests + " Mi");
            System.out.println("Total Memory Limits: " + totalMemoryLimits + " Mi");

            // 变量初始化，用于存储总量和可分配量
            double totalStorage = 0;
            double allocatableStorage = 0;


            // 遍历每个节点，累加存储信息
            for (V1Node node : nodeList.getItems()) {
                // 获取节点的存储总量
                String totalStorageStr = node.getStatus().getCapacity().get("ephemeral-storage").getNumber().toString();
                double nodeTotalStorage = parseStorageValue(totalStorageStr, "byte");

                // 获取节点的可分配存储量
                String allocatableStorageStr = node.getStatus().getAllocatable().get("ephemeral-storage").getNumber().toString();
                double nodeAllocatableStorage = parseStorageValue(allocatableStorageStr, "byte");

                System.out.println("totalStorageStr = " + totalStorageStr);
                System.out.println("allocatableStorageStr = " + allocatableStorageStr);

                totalStorage += nodeTotalStorage;
                allocatableStorage += nodeAllocatableStorage;
            }

            // 打印结果
            System.out.println("TotalStorage in Cluster:" + totalStorage + " bytes");
            System.out.println("AllocatableStorage in Cluster:" + allocatableStorage + " bytes");


            /*// 获取所有命名空间中的 Pod
            V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, false);

            // 初始化变量，用于存储内存请求和限制的总和
            BigInteger totalMemoryRequests = BigInteger.ZERO;
            BigInteger totalMemoryLimits = BigInteger.ZERO;

            // 遍历所有 Pod
            for (V1Pod pod : podList.getItems()) {
                // 遍历 Pod 中的容器
                if (pod.getSpec() != null) {
                    for (V1Container container : pod.getSpec().getContainers()) {
                        // 获取容器的资源请求
                        Map<String, Quantity> requests = container.getResources().getRequests();
                        if (requests != null) {
                            Quantity memoryRequestStr = requests.get("memory");
                            if (memoryRequestStr != null) {
                                totalMemoryRequests = totalMemoryRequests.add(parseMemoryValue(memoryRequestStr.getNumber().toString()));
                            }
                        }

                        // 获取容器的资源限制
                        Map<String, Quantity> limits = container.getResources().getLimits();
                        if (limits != null) {
                            Quantity memoryLimitStr = limits.get("memory");
                            if (memoryLimitStr != null) {
                                totalMemoryLimits = totalMemoryLimits.add(parseMemoryValue(memoryLimitStr.getNumber().toString()));
                            }
                        }
                    }
                }
            }

            // 打印结果
            System.out.println("Total Memory Requests: " + totalMemoryRequests + " bytes");
            System.out.println("Total Memory Limits: " + totalMemoryLimits + " bytes");
*/

        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#listNode");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static BigInteger parseMemoryValue(String memoryStr) {
        if (memoryStr == null || memoryStr.isEmpty()) {
            return BigInteger.ZERO;
        }
        // 假设内存单位是 Ki、Mi、Gi，转换为字节
        BigInteger value = new BigInteger(memoryStr.replaceAll("[^0-9]", ""));
        if (memoryStr.endsWith("Ki")) {
            value = value.multiply(BigInteger.valueOf(1024));
        } else if (memoryStr.endsWith("Mi")) {
            value = value.multiply(BigInteger.valueOf(1024 * 1024));
        } else if (memoryStr.endsWith("Gi")) {
            value = value.multiply(BigInteger.valueOf(1024 * 1024 * 1024));
        }
        // 添加其他单位转换逻辑，如需要
        return value;
    }

    // 将存储字符串转换为字节数
    private static double parseStorageValue(String storageStr, String dataUnit) {
        if (storageStr == null || storageStr.isEmpty()) {
            return 0;
        }
        // 假设存储单位是Ki，转换为字节
        double value = Double.parseDouble(storageStr.replaceAll("[^0-9]", ""));
        switch (dataUnit) {
            case "byte":
                return Math.round(value / (1024 * 1024 * 1024) * 100.0) / 100.0;
            case "k":
                return Math.round(value / (1024 * 1024) * 100.0) / 100.0;
            case "m":
                return Math.round(value / 1024 * 100.0) / 100.0;
            case "g":
                return Math.round(value);
        }
        // 添加其他单位转换逻辑，如需要
        return value;
    }

    // 计算节点上的 Pod 请求的内存总量 (以 Mi 为单位)
    private static long getNodeMemoryRequests(CoreV1Api api, String nodeName) throws ApiException {
        long totalMemoryRequests = 0;

        // 获取所有 Pod
        V1PodList podList = api.listPodForAllNamespaces(null, null, null, "spec.nodeName=" + nodeName, null, null, null, null, null, false);
        for (V1Pod pod : podList.getItems()) {
            List<V1Container> containers = pod.getSpec().getContainers();
            for (V1Container container : containers) {
                V1ResourceRequirements resources = container.getResources();
                if (resources != null && resources.getRequests() != null && resources.getRequests().get("memory") != null) {
                    String memoryRequestStr = resources.getRequests().get("memory").toSuffixedString();
                    System.out.println("memoryRequestStr = " + memoryRequestStr);
                    totalMemoryRequests += convertMemoryToMi(memoryRequestStr);
                }
            }
        }
        return totalMemoryRequests;
    }

    // 计算节点上的 Pod 限制的内存总量 (以 Mi 为单位)
    private static long getNodeMemoryLimits(CoreV1Api api, String nodeName) throws ApiException {
        long totalMemoryLimits = 0;

        // 获取所有 Pod
        V1PodList podList = api.listPodForAllNamespaces(null, null, null, "spec.nodeName=" + nodeName, null, null, null, null, null, false);
        for (V1Pod pod : podList.getItems()) {
            List<V1Container> containers = pod.getSpec().getContainers();
            for (V1Container container : containers) {
                V1ResourceRequirements resources = container.getResources();
                if (resources != null && resources.getLimits() != null && resources.getLimits().get("memory") != null) {
                    String memoryLimitStr = resources.getLimits().get("memory").toSuffixedString();
                    totalMemoryLimits += convertMemoryToMi(memoryLimitStr);
                }
            }
        }
        return totalMemoryLimits;
    }

    // 将内存字符串转换为 Mi (Mebibytes)
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

    @Test
    void conversionType() {
        double v = Double.parseDouble("127647796629".replaceAll("[^0-9]", ""));

        System.out.println(Math.round(v / (1024 * 1024 * 1024) * 100.0) / 100.0);
    }

    @Test
    void nodeStatus() {

        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg"; // 替换为你的 Token
        String apiServerUrl = "https://172.11.1.40:6443"; // 替换为你的 API Server 地址

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

        try {

            // 获取所有节点的 CPU 总量和可分配量
            V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, null, null);
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
            String jsonString = JSON.toJSONString(list);
            System.out.println(jsonString);

        } catch (ApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void deploymentStatus() {

        try {

            String apiServerUrl = "https://172.11.1.40:6443";
            String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

            // 2. 创建 API 实例
            CoreV1Api coreApi = new CoreV1Api();
            AppsV1Api appsApi = new AppsV1Api();

            // 3. 获取所有命名空间
            V1NamespaceList namespaceList = coreApi.listNamespace(null, null, null, null, null, null, null, null, null, null);

            List<Map<String, String>> list = new ArrayList<>();
            // 4. 遍历所有命名空间并获取 Deployment 列表
            for (V1Namespace item : namespaceList.getItems()) {
                String namespace = item.getMetadata().getName();
                V1DeploymentList deploymentList = appsApi.listNamespacedDeployment(namespace, null, null, null, null, null, null, null, null, null, null);

                for (V1Deployment deployment : deploymentList.getItems()) {
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
                    list.add(map);
                }

            }

            String jsonString = JSON.toJSONString(list);
            System.out.println(jsonString);

            /*for (var namespace : namespaceList.getItems()) {
                String namespaceName = namespace.getMetadata().getName();
                System.out.println("Namespace: " + namespaceName);

                // 获取指定命名空间中的 Deployment 列表
                V1DeploymentList deploymentList = appsApi.listNamespacedDeployment(namespaceName, null, null, null, null, null, null, null, null, null, null);

                // 输出 Deployment 信息
                for (V1Deployment deployment : deploymentList.getItems()) {
                    String deploymentName = deployment.getMetadata().getName();
                    String status = deployment.getStatus().toString(); // 获取 Deployment 状态信息

                    // 打印 Deployment 状态
                    System.out.println("  Deployment Name: " + deploymentName);
                    System.out.println("  Deployment Status: " + status);
                }
            }*/
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void serviceStatus() {
        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

        // 2. 创建 API 实例
        try {
            CoreV1Api coreApi = new CoreV1Api();
            V1ServiceList serviceList = coreApi.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
            List<Map<String, String>> list = new ArrayList<>();

            for (V1Service service : serviceList.getItems()) {
                String serviceName = service.getMetadata().getName();
                String namespace = service.getMetadata().getNamespace();

                V1PodList podList = coreApi.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null, null);
                Map<String, String> map = new HashMap<>();
                int statusNum = 0;
                for (V1Pod pod : podList.getItems()) {
                    if (pod.getMetadata().getLabels() != null && pod.getMetadata().getLabels().get("app") != null) {
                        V1PodStatus podStatus = pod.getStatus();
                        String phase = podStatus.getPhase();

                        if (!"Running".equals(phase)) {
                            statusNum += 1;
                        }

                        /*System.out.println("pod.getMetadata().getLabels() = " + pod.getMetadata().getLabels());
                        System.out.println("pod.getMetadata().getLabels().get(\"app\") = " + pod.getMetadata().getLabels().get("app"));
                        System.out.println("serviceName = " + serviceName);
                        System.out.println("Pod: " + pod.getMetadata().getName() + " - Phase: " + phase);*/
                    }
                }
                if (statusNum == 0) {
                    map.put("phase", "Running");
                    map.put("service", serviceName);
                    list.add(map);
                }
            }
            String jsonString = JSON.toJSONString(list);
            System.out.println(jsonString);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPodStatus() {
        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

        try {
            // 创建 Kubernetes API 客户端
            CoreV1Api api = new CoreV1Api();

            // 获取所有命名空间中的 Pod
            V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
            List<V1Pod> pods = podList.getItems();


            Map<String,Object> map = new HashMap<>();
            map.put("name","Pod Status Not Running");

            List<Object> list = new LinkedList<>();

            for (V1Pod pod : pods) {
                String podName = pod.getMetadata().getName();
                String phase = pod.getStatus().getPhase();

                // 统计非 Running 状态的 Pod
                if (!"Running".equalsIgnoreCase(phase)) {
                    list.add(phase);
                    list.add(podName);
                    //System.out.println("Pod: " + podName + "Phase: " + phase);
                }
            }
            map.put("data",list);
            System.out.println(list);

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getNodeNum() {

        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

        try {
            // 获取节点上的所有 Pods
            V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, "node=" + "k8s-node2", null, null, null, null);

            // 输出节点上的 Pod 总数
            System.out.println("Total Pods on Node: " + podList.getItems());

        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getNodeStatus() {

        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

        try {
            V1Node node = api.readNode("k8s-master", null);
            // 输出节点状态
            List<String> stringList = new ArrayList<>();
            for (V1NodeCondition condition : node.getStatus().getConditions()) {
                stringList.add(condition.getType());
            }
            System.out.println("Node Name: " + "k8s-master");
            System.out.println(stringList);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void cpuResources() {
        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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
        try {
            V1Node node = api.readNode("k8s-master", null);
            System.out.println("node.getStatus() = " + node.getStatus());

            System.out.println("node.getStatus().getAllocatable().get(\"cpu\").getNumber() = " + node.getStatus().getAllocatable().get("cpu").getNumber());
            System.out.println("node.getStatus().getCapacity().get(\"cpu\").getNumber() = " + node.getStatus().getCapacity().get("cpu").getNumber());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getNodeMemoryAll() {
        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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
        try {
            V1Node node = api.readNode("k8s-master", null);
//            System.out.println("node.getStatus() = " + node.getStatus());
//
//            System.out.println("node.getStatus().getAllocatable().get(\"memory\").getNumber() = " + node.getStatus().getAllocatable().get("memory").getNumber());
//            System.out.println("node.getStatus().getCapacity().get(\"memory\").getNumber() = " + node.getStatus().getCapacity().get("memory").getNumber());
            System.out.println("node = " + node);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void nodeStorage() {
        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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
        try {
            V1Node node = api.readNode("k8s-master", null);
            String totalStorageStr = Objects.requireNonNull(Objects.requireNonNull(node.getStatus()).getCapacity()).get("ephemeral-storage").getNumber().toString();
            double nodeTotalStorage = Double.parseDouble(totalStorageStr.replaceAll("[^0-9]", ""));
            double value = Math.round(nodeTotalStorage / (1024 * 1024 * 1024) * 100.0) / 100.0;
            System.out.println(totalStorageStr);
            System.out.println("nodeTotalStorage = " + value);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getServicePodTotal() {

        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

        String namespace = "default"; // Replace with your namespace

        String serverName = "my-service";

        try {
            V1ServiceList serviceList = api.listNamespacedService(namespace, null, null, null, null, null, null, null, null, null, null);
            String serviceName = serviceList.getItems().stream()
                    .map(service -> service.getMetadata().getName())
                    .filter(serverName::equals)
                    .findFirst()
                    .orElse(null);

            if (serviceName == null) {
                return;
            }

            // Get all pods in the namespace
            V1PodList podList = api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null, null);
            Map<String, Integer> servicePodCount = new HashMap<>();

            int podCount = 0;
            List<Map<String, String>> mapList = new ArrayList<>();
            for (var pod : podList.getItems()) {
                if (pod.getMetadata().getLabels() != null && serviceName.equals(pod.getMetadata().getLabels().get("app"))) {
                    podCount++;
                    Map<String, String> map = new HashMap<>();

                    map.put("name", pod.getMetadata().getName());
                    map.put("phase", pod.getStatus().getPhase());
                    mapList.add(map);
                }
            }

            servicePodCount.put(serviceName, podCount);
            System.out.println("servicePodCount = " + servicePodCount);

            String jsonString = JSON.toJSONString(mapList);
            System.out.println("jsonString = " + jsonString);

        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void serviceCPUResources(){
        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

        String namespace = "default"; // Replace with your namespace

        String serverName = "my-service";

        try {
            V1ServiceList serviceList = api.listNamespacedService(namespace, null, null, null, null, null, null, null, null, null, null);
            String serviceName = serviceList.getItems().stream()
                    .map(service -> service.getMetadata().getName())
                    .filter(serverName::equals)
                    .findFirst()
                    .orElse(null);

            if (serviceName == null) {
                return;
            }

            V1PodList podList = api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null, null);

            BigDecimal cpuRequestCount = BigDecimal.ZERO;
            BigDecimal cpuLimitCount = BigDecimal.ZERO;
            BigDecimal memoryRequestCount = BigDecimal.ZERO;
            BigDecimal memoryLimitCount = BigDecimal.ZERO;


            for (var pod : podList.getItems()) {
                if (pod.getMetadata().getLabels() != null && serviceName.equals(pod.getMetadata().getLabels().get("app"))) {
                    for (V1Container container : pod.getSpec().getContainers()) {
                        BigDecimal cpuRequest = container.getResources().getRequests().get("cpu").getNumber();
                        BigDecimal cpuLimit = container.getResources().getLimits().get("cpu").getNumber();
                        BigDecimal memoryRequest = container.getResources().getRequests().get("memory").getNumber();
                        BigDecimal memoryLimit = container.getResources().getLimits().get("memory").getNumber();

                        cpuRequestCount = cpuRequestCount.add(cpuRequest);
                        cpuLimitCount = cpuLimitCount.add(cpuLimit);
                        memoryRequestCount = memoryRequestCount.add(memoryRequest);
                        memoryLimitCount = memoryLimitCount.add(memoryLimit);
                    }
                }
            }
            System.out.println("cpuRequestCount = " + cpuRequestCount);
            System.out.println("cpuLimitCount = " + cpuLimitCount);
            System.out.println("memoryRequestCount = " + memoryRequestCount);
            System.out.println("memoryLimitCount = " + memoryLimitCount);

        } catch (ApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getServicePodInfo(){
        try {

            String apiServerUrl = "https://172.11.1.40:6443";
            String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

            // 尝试获取 API 版本信息
            String version = api.getAPIResources().toString();
            if (version != null) {
                System.out.println("Successfully connected to the Kubernetes API Server.");
                System.out.println("API Server Version: " + version);
            }
        } catch (Exception e) {
            System.err.println("Failed to connect to the Kubernetes API Server: " + e.getMessage());
        }
    }

    @Test
    void getStorageTotal(){

        String apiServerUrl = "https://172.11.1.40:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg";

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

            // 将字节数转换为 GiB
            double giB = totalStorage / Math.pow(2, 30);
            String format = String.format("%.2f", giB);

            // 格式化为保留两位小数
            System.out.printf("Bytes: %d\n", totalStorage);
            System.out.println("format = " + format);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testBigDecimal(){
        BigDecimal bigDecimal = new BigDecimal("1334.11124");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String format = decimalFormat.format(bigDecimal);
        System.out.println(format);
    }
}
