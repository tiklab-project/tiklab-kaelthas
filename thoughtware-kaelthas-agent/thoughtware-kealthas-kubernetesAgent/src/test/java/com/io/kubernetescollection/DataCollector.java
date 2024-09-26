package com.io.kubernetescollection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataCollector {

    private static final int INSERT_THRESHOLD = 100;
    private List<String> collectedData = new ArrayList<>();
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {
        DataCollector collector = new DataCollector();
        collector.startScheduledTask();
    }

    public void startScheduledTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // 定时任务：每秒执行一次数据采集
        scheduler.scheduleAtFixedRate(this::collectData, 0, 1, TimeUnit.SECONDS);
    }

    public void collectData() {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        // 创建并行任务列表
        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (int i = 1; i <= 40; i++) {
            int caseId = i;
            // 并行执行case分支
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> processWithCase(caseId), executor);
            futures.add(future);
        }

        // 收集所有case分支的结果
        List<String> results = futures.stream()
                .map(CompletableFuture::join) // 等待所有任务完成
                .toList();

        // 收集处理后的数据
        collectedData.addAll(results);

        // 当收集的数据达到100条时，执行插入操作
        if (collectedData.size() >= INSERT_THRESHOLD) {
            insertData(collectedData);
            collectedData.clear(); // 插入后清空列表，准备下一批数据
        }

        // 关闭线程池
        executor.shutdown();
    }

    // 模拟case分支的执行
    private String processWithCase(int caseId) {
        try {
            // 模拟每个case需要200毫秒的执行时间
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Processed data from case " + caseId;
    }

    // 模拟插入操作
    private void insertData(List<String> data) {
        System.out.println("Inserting " + data.size() + " records into the database...");
        // 实际插入操作逻辑
    }
}
