package io.tiklab.kaelthas.kubernetes.kubernetesInfo.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.toolkit.join.annotation.FindAll;

import java.util.List;

public interface KubernetesService {
    Pagination<Kubernetes> findKbInfoPage(Kubernetes kubernetes);

    String createKbInfo(Kubernetes kubernetes);

    void updateKbInfo(Kubernetes kubernetes);

    void deleteKuInfo(String id);

    List<Kubernetes> findKuDropDown();

    Kubernetes findKuInfoById(String id);

    @FindAll
    List<Kubernetes> findAll();
}
