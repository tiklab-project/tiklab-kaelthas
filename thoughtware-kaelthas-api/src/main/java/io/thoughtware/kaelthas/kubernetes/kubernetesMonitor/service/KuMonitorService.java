package io.thoughtware.kaelthas.kubernetes.kubernetesMonitor.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.kubernetes.kubernetesMonitor.model.KubernetesMonitor;

import java.util.List;

@JoinProvider(model = KubernetesMonitor.class)
public interface KuMonitorService {

    Pagination<KubernetesMonitor> findKuMonitorPage(KubernetesMonitor kubernetesMonitor);


    String createKuMonitor(KubernetesMonitor kubernetesMonitor);

    void deleteKuMonitor(String id);

    void updateKuMonitor(KubernetesMonitor kubernetesMonitor);

    List<KubernetesMonitor> findAllKuMonitor();

    List<KubernetesMonitor> findKuAndMonitor();

    void deleteByKuId(String id);
}
