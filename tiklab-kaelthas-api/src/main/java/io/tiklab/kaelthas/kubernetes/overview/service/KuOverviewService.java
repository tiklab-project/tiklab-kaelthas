package io.tiklab.kaelthas.kubernetes.overview.service;

import java.util.Map;

/**
 * k8s概况页
 */
public interface KuOverviewService {

    /**
     * 根据id查询k8s概况页展示的信息数据
     */
    Map<String, Object> findKuOverviewTotal(String kuId);
}
