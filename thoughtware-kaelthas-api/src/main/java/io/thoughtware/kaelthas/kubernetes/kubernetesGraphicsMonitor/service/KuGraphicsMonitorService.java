package io.thoughtware.kaelthas.kubernetes.kubernetesGraphicsMonitor.service;

import io.thoughtware.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;
import io.thoughtware.kaelthas.kubernetes.kubernetesGraphicsMonitor.model.KuGraphicsMonitor;

import java.util.List;

public interface KuGraphicsMonitorService {
    void createKuGraphicsMonitor(KuGraphics kuGraphics);

    void deleteByCondition(String graphicsId, String monitorId);

    List<String> findMonitorIds(KuGraphicsMonitor kuGraphicsMonitor);

    List<KuGraphicsMonitor> findGraphicsMonitors(String id);

    void deleteByGraphicsIds(List<String> stringList);
}
