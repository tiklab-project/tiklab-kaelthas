package com.io.kubernetescollection;

import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;

import java.util.Map;
import java.util.Objects;

public class KubernetesMetrics {

    public static void main(String[] args) throws Exception {
        // 初始化 Kubernetes 客户端
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

        // 创建 CoreV1Api 实例
        CoreV1Api api = new CoreV1Api();

        // 指定要查询的命名空间
        String namespace = "default"; // 替换为你的命名空间

        // 获取指定命名空间下的所有 Pods
        V1PodList podList = api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null, null);

        // 遍历 Pod 列表
        for (V1Pod pod : podList.getItems()) {
            String podName = pod.getMetadata().getName();

            System.out.println("Pod Name: " + podName);

            // 获取每个容器的资源请求和限制
            if (pod.getSpec() != null && pod.getSpec().getContainers() != null) {
                pod.getSpec().getContainers().forEach(container -> {
                    String containerName = container.getName();
                    Map<String, Quantity> requests = Objects.requireNonNull(container.getResources()).getRequests();
                    Map<String, Quantity> limits = container.getResources().getLimits();

                    System.out.println("  Container Name: " + containerName);
                    if (requests != null) {
                        System.out.println("    CPU Requests: " + requests.get("cpu").getNumber());
                        System.out.println("    Memory Requests: " + requests.get("memory").getNumber());
                    }
                    if (limits != null) {
                        System.out.println("    CPU Limits: " + limits.get("cpu").getNumber());
                        System.out.println("    Memory Limits: " + limits.get("memory").getNumber());
                    }
                });
            }
        }
    }
}
