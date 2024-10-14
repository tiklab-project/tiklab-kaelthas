package com.io.databasesagent;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.util.Set;

public class JMXExample {
    public static void main(String[] args) {
        try {
            MBeanServerConnection mbeanServer = ManagementFactory.getPlatformMBeanServer();
            
            // 获取 JVM 的运行时间
            RuntimeMXBean runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(
                    mbeanServer, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
            System.out.println("JVM 运行时间: " + runtimeMXBean.getUptime());

            // 获取 JVM 内存使用情况
            MemoryMXBean memoryMXBean = ManagementFactory.newPlatformMXBeanProxy(
                    mbeanServer, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            System.out.println("堆内存使用: " + heapMemoryUsage.getUsed() + " / " + heapMemoryUsage.getMax());

            // 获取线程数
            Set<ObjectName> threads = mbeanServer.queryNames(new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME + ",*"), null);
            System.out.println("线程数: " + threads.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
