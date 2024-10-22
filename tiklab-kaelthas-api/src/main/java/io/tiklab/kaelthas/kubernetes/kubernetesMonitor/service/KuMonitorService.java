package io.tiklab.kaelthas.kubernetes.kubernetesMonitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.model.KuMonitor;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

@JoinProvider(model = KuMonitor.class)
public interface KuMonitorService {

    Pagination<KuMonitor> findKuMonitorPage(KuMonitor kuMonitor);


    String createKuMonitor(KuMonitor kuMonitor);

    void deleteKuMonitor(String id);

    void updateKuMonitor(KuMonitor kuMonitor);

    List<KuMonitor> findAllKuMonitor(String kuId);

    List<KuMonitor> findKuAndMonitor();

    void deleteByKuId(String id);
}
