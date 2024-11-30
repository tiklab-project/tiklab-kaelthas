package io.tiklab.kaelthas.kubernetes.setting.service;

import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;

/**
 * k8s的设置
 */
public interface KuSettingService {

    /**
     * 修改k8s
     */
    void updateKbInfo(Kubernetes kubernetes);

    /**
     * 删除k8s
     */
    void deleteKuInfo(String id);

    /**
     * 根据id查询k8s的信息
     */
    Kubernetes findKuInfoById(String id);
}
