package com.io.kubernetescollection;


import com.alibaba.fastjson.JSON;

import java.util.*;

public class YamlValueFetcher {
    public static void main(String[] args) {
        String s = "[{\"reason\":\"CalicoIsUp\",\"name\":\"k8s-master\",\"type\":\"NetworkUnavailable\",\"status\":\"False\"},{\"reason\":\"KubeletHasSufficientMemory\",\"name\":\"k8s-master\",\"type\":\"MemoryPressure\",\"status\":\"False\"},{\"reason\":\"KubeletHasNoDiskPressure\",\"name\":\"k8s-master\",\"type\":\"DiskPressure\",\"status\":\"False\"},{\"reason\":\"KubeletHasSufficientPID\",\"name\":\"k8s-master\",\"type\":\"PIDPressure\",\"status\":\"False\"},{\"reason\":\"KubeletReady\",\"name\":\"k8s-master\",\"type\":\"Ready\",\"status\":\"True\"},{\"reason\":\"CalicoIsUp\",\"name\":\"k8s-node1\",\"type\":\"NetworkUnavailable\",\"status\":\"False\"},{\"reason\":\"KubeletHasSufficientMemory\",\"name\":\"k8s-node1\",\"type\":\"MemoryPressure\",\"status\":\"False\"},{\"reason\":\"KubeletHasNoDiskPressure\",\"name\":\"k8s-node1\",\"type\":\"DiskPressure\",\"status\":\"False\"},{\"reason\":\"KubeletHasSufficientPID\",\"name\":\"k8s-node1\",\"type\":\"PIDPressure\",\"status\":\"False\"},{\"reason\":\"KubeletReady\",\"name\":\"k8s-node1\",\"type\":\"Ready\",\"status\":\"True\"},{\"reason\":\"CalicoIsUp\",\"name\":\"k8s-node2\",\"type\":\"NetworkUnavailable\",\"status\":\"False\"},{\"reason\":\"KubeletHasSufficientMemory\",\"name\":\"k8s-node2\",\"type\":\"MemoryPressure\",\"status\":\"False\"},{\"reason\":\"KubeletHasNoDiskPressure\",\"name\":\"k8s-node2\",\"type\":\"DiskPressure\",\"status\":\"False\"},{\"reason\":\"KubeletHasSufficientPID\",\"name\":\"k8s-node2\",\"type\":\"PIDPressure\",\"status\":\"False\"},{\"reason\":\"KubeletReady\",\"name\":\"k8s-node2\",\"type\":\"Ready\",\"status\":\"True\"}]";
        Set<String> stringSet = new HashSet<>();

        List<Map<String, String>> list = JSON.parseObject(s, List.class);
        List<List<String>> listList = new LinkedList<>();
        for (Map<String, String> map : list) {
            List<String> list1 = new LinkedList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringSet.add(entry.getKey());
                list1.add(entry.getValue());
            }
            listList.add(list1);
        }

        listList.add(stringSet.stream().toList());
        Collections.reverse(listList);
        System.out.println(listList);
    }

}
