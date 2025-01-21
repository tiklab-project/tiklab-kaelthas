package io.tiklab.kaelthas.kubernetes.overview.service;

import io.tiklab.kaelthas.kubernetes.history.service.KubernetesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KuOverviewServiceImpl implements KuOverviewService{

    @Autowired
    private KubernetesHistoryService kubernetesHistoryService;

    /**
     * 根据id查询k8s概况页展示的信息数据
     */
    @Override
    public Map<String, Object> findKuOverviewTotal(String kuId) {
        return kubernetesHistoryService.findKuOverviewTotal(kuId);
    }
}
