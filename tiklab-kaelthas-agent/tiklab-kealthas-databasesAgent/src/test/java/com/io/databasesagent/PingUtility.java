package com.io.databasesagent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class PingUtility {

    public static void main(String[] args) {
        String ipAddress = "172.11.1.24";  // 目标设备IP地址
        int pingCount = 4;  // 发送的Ping请求数量

        try {
            // 调用系统Ping命令（Windows 和 Linux/MacOS 命令略有不同）
//            String pingCommand = "ping -c " + pingCount + " " + ipAddress;  // Linux/MacOS
             String pingCommand = "ping -n " + pingCount + " " + ipAddress;  // Windows

            // 执行Ping命令
            Process process = Runtime.getRuntime().exec(pingCommand);

            // 读取命令的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line;
            StringBuilder pingResult = new StringBuilder();

            // 逐行读取Ping的输出结果
            while ((line = reader.readLine()) != null) {
                pingResult.append(line).append("\n");
                System.out.println(line);  // 打印每一行输出到控制台
            }

            // 解析并提取所需信息（RTT，丢包率等）
            extractPingInfo(pingResult.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 方法：解析Ping命令的输出结果并提取信息
    private static void extractPingInfo(String pingOutput) {
        // 通过正则表达式或字符串处理方法提取信息
        if (pingOutput.contains("min/avg/max")) {
            // Linux/MacOS系统的输出
            String rttLine = pingOutput.substring(pingOutput.indexOf("min/avg/max"));
            System.out.println("Ping RTT信息: " + rttLine);
        } else if (pingOutput.contains("平均")) {
            // Windows系统的输出，RTT信息通常在 "平均" 后
            String rttLine = pingOutput.substring(pingOutput.indexOf("平均"));
            System.out.println("Ping RTT信息: " + rttLine);
        }

        // 解析丢包率
        if (pingOutput.contains("packet loss")) {
            String packetLossLine = pingOutput.substring(pingOutput.indexOf("packet loss"));
            System.out.println("丢包率信息: " + packetLossLine);
        }
    }
}
