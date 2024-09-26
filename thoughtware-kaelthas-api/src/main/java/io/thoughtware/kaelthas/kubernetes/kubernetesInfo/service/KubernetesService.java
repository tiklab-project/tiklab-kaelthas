package io.thoughtware.kaelthas.kubernetes.kubernetesInfo.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.thoughtware.toolkit.join.annotation.FindAll;

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
