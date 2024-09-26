package com.io.kubernetescollection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

public class PodMetricsFetcher {

    private static final String METRICS_SERVER_URL_TEMPLATE = "https://%s/apis/metrics.k8s.io/v1beta1/namespaces/%s/pods/%s";
    private static final String API_SERVER_HOST = "172.11.1.40:6443"; // 替换为实际的 API Server 地址
    private static final String BEARER_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg"; // 使用有效的 Bearer Token

    public static void main(String[] args) {

        String namespace = "default"; // 替换为实际的命名空间
        String podName = "my-pod"; // 替换为实际的 Pod 名称数组

        // 配置 OkHttpClient
        OkHttpClient client = createHttpClient();

        String metricsUrl = String.format(METRICS_SERVER_URL_TEMPLATE, API_SERVER_HOST, namespace, podName);

        Request request = new Request.Builder()
                .url(metricsUrl)
                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseData);

                // 提取并显示 Pod 的 CPU 和内存使用情况
                String totalCpuUsage = "0";
                String totalMemoryUsage = "0";

                JsonNode containersNode = jsonNode.path("containers");
                if (containersNode.isArray()) {
                    for (JsonNode containerNode : containersNode) {
                        String cpuUsage = containerNode.path("usage").path("cpu").asText();
                        String memoryUsage = containerNode.path("usage").path("memory").asText();

                        // 汇总 CPU 和内存使用情况
                        totalCpuUsage = addCpuUsage(totalCpuUsage, cpuUsage);
                        totalMemoryUsage = addMemoryUsage(totalMemoryUsage, memoryUsage);
                    }
                }

                // 输出 Pod 的 CPU 和内存使用情况
                System.out.println("Pod Name: " + podName);
                System.out.println("Total CPU Usage: " + totalCpuUsage);
                System.out.println("Total Memory Usage: " + totalMemoryUsage);
            } else {
                System.err.println("Failed to fetch metrics for Pod " + podName + ": " + response.code() + " " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 创建 OkHttpClient
    private static OkHttpClient createHttpClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            /*HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);*/

            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(10, TimeUnit.SECONDS);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 汇总 CPU 使用情况（以纳秒为单位）
    private static String addCpuUsage(String totalCpuUsage, String cpuUsage) {
        long totalCpu = parseCpuUsage(totalCpuUsage);
        long containerCpu = parseCpuUsage(cpuUsage);
        return String.valueOf(totalCpu + containerCpu);
    }

    // 汇总内存使用情况（以字节为单位）
    private static String addMemoryUsage(String totalMemoryUsage, String memoryUsage) {
        long totalMemory = parseMemoryUsage(totalMemoryUsage);
        long containerMemory = parseMemoryUsage(memoryUsage);
        return String.valueOf(totalMemory + containerMemory);
    }

    // 解析 CPU 使用情况
    private static long parseCpuUsage(String cpuUsage) {
        return Long.parseLong(cpuUsage.replace("n", "")); // 处理纳秒值
    }

    // 解析内存使用情况
    private static long parseMemoryUsage(String memoryUsage) {
        return Long.parseLong(memoryUsage.replace("Ki", "")); // 处理 KiB 值
    }
}
