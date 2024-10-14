package com.io.kubernetescollection;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class KubernetesCpuMetrics {
    public static void main(String[] args) throws IOException, ApiException {
        long startTime = System.currentTimeMillis();
        /*String kubeConfigPath = "D:\\code\\companycode\\xmonitor\\tiklab-xmonitor\\tiklab-xmonitor-kubernetesCollection\\src\\main\\resources\\config"; // 如 "/home/user/.kube/config"

        ApiClient client = Config.fromConfig(kubeConfigPath);*/

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

        long timeMillis = System.currentTimeMillis();

        //io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        AppsV1Api appsV1Api = new AppsV1Api();

        // 获取所有节点的 CPU 总量和可分配量
        V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, null, null);

        int totalCapacity = 0;
        int totalAllocatable = 0;


        for (V1Node node : nodeList.getItems()) {
            V1NodeStatus status = node.getStatus();
            String nodeName = node.getMetadata().getName();
            String cpuCapacity = String.valueOf(status.getCapacity().get("cpu").getNumber());
            String cpuAllocatable = String.valueOf(status.getAllocatable().get("cpu").getNumber());

            System.out.println("Node: " + nodeName);
            System.out.println("CPU Capacity (Cores): " + cpuCapacity);
            System.out.println("CPU Allocatable (Cores): " + cpuAllocatable);
            if (StringUtils.isNotBlank(cpuCapacity)) {
                totalCapacity += Integer.parseInt(cpuCapacity);
            }

            if (StringUtils.isNotBlank(cpuAllocatable)) {
                totalAllocatable += Integer.parseInt(cpuAllocatable);
            }
        }

        System.out.println("node length = " + nodeList.getItems().size());


        // 获取 Pod 总数
        List<V1Pod> pods = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems();
        int podTotal = pods.size();
        System.out.println("Pod Total: " + podTotal);

        // 获取 Container 总数
        int containerTotal = pods.stream().mapToInt(pod -> pod.getSpec().getContainers().size()).sum();
        System.out.println("Container Total: " + containerTotal);




        // 获取所有 Pod 的 CPU 请求量和限制量
        V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
        int totalCpuRequests = 0;
        int totalCpuLimits = 0;

        for (V1Pod pod : podList.getItems()) {
            List<V1Container> containers = pod.getSpec().getContainers();

            for (V1Container container : containers) {
                V1ResourceRequirements resources = container.getResources();

                if (resources.getRequests() != null && resources.getRequests().get("cpu") != null) {
                    totalCpuRequests += resources.getRequests().get("cpu").getNumber().intValue();
                }

                if (resources.getLimits() != null && resources.getLimits().get("cpu") != null) {
                    totalCpuLimits += resources.getLimits().get("cpu").getNumber().intValue();
                }

            }
        }
        System.out.println("\ntotalCapacity:  " + totalCapacity);
        System.out.println("Total CPU Requests (Cores): " + totalCpuRequests);
        System.out.println("Total CPU Limits (Cores): " + totalCpuLimits);
        System.out.println("totalAllocatable:  " + totalAllocatable +"\n");


        // 获取 Deployment 总数
        int deploymentTotal = appsV1Api.listDeploymentForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems().size();
        System.out.println("Deployment Total: " + deploymentTotal);

        // 获取 StatefulSet 总数
        int statefulSetTotal = appsV1Api.listStatefulSetForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems().size();
        System.out.println("StatefulSet Total: " + statefulSetTotal);

        // 获取 DaemonSet 总数
        int daemonSetTotal = appsV1Api.listDaemonSetForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems().size();
        System.out.println("DaemonSet Total: " + daemonSetTotal);

        // 获取 Service 总数
        int serviceTotal = api.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null, null).getItems().size();
        System.out.println("Service Total: " + serviceTotal);


        // 获取 Node 状态
        System.out.println("Nodes Status:");
        for (V1Node node : nodeList.getItems()) {
            System.out.println("Name: " + node.getMetadata().getName() + ", Status: " + node.getStatus().getConditions());
        }

        // 获取 Deployment 状态和规格副本数
        V1DeploymentList deploymentList = appsV1Api.listDeploymentForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
        System.out.println("\nDeployments Status and Replicas:");
        for (V1Deployment deployment : deploymentList.getItems()) {
            System.out.println("Name: " + deployment.getMetadata().getName() + ", Status: " + deployment.getStatus() + ", Replicas: " + deployment.getSpec().getReplicas());
        }

        // 获取 Service 状态
        /*V1ServiceList serviceList = api.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
        System.out.println("\nServices Status:");
        for (V1Service service : serviceList.getItems()) {
            System.out.println("Name: " + service.getMetadata().getName() + ", Status: " + service.getStatus());
        }

        // 获取 Pod 状态 (Not Running 和 Waiting)
        System.out.println("\nPods Status Not Running:");
        List<V1Pod> notRunningPods = podList.getItems().stream()
                .filter(pod -> pod.getStatus().getPhase() != null && !pod.getStatus().getPhase().equals("Running"))
                .collect(Collectors.toList());
        for (V1Pod pod : notRunningPods) {
            System.out.println("Name: " + pod.getMetadata().getName() + ", Status: " + pod.getStatus().getPhase());
        }*/

        System.out.println("\nPods Status Waiting:");
        List<V1Pod> waitingPods = podList.getItems().stream()
                .filter(pod -> pod.getStatus().getConditions() != null &&
                        pod.getStatus().getConditions().stream().anyMatch(cond -> "Pending".equals(cond.getReason())))
                .collect(Collectors.toList());
        for (V1Pod pod : waitingPods) {
            System.out.println("Name: " + pod.getMetadata().getName() + ", Status: " + pod.getStatus().getPhase());
        }

        long endTime = System.currentTimeMillis();
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println("代码运行的时间:" + (endTime - startTime));

        System.out.println("配置设置的时间:" + (timeMillis - startTime));




    }
}
