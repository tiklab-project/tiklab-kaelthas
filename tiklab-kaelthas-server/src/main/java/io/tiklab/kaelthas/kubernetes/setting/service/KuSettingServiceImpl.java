package io.tiklab.kaelthas.kubernetes.setting.service;

import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * k8s的设置
 */
@Service
public class KuSettingServiceImpl implements KuSettingService{

    @Autowired
    private KubernetesService kubernetesService;

    /**
     * 修改k8s
     */
    @Override
    public void updateKbInfo(Kubernetes kubernetes) {
        kubernetesService.updateKbInfo(kubernetes);
    }

    /**
     * 删除k8s
     */
    @Override
    public void deleteKuInfo(String id) {
        kubernetesService.deleteKuInfo(id);
    }

    /**
     * 根据id查询k8s的信息
     */
    @Override
    public Kubernetes findKuInfoById(String id) {
        return kubernetesService.findKuInfoById(id);
    }
}
