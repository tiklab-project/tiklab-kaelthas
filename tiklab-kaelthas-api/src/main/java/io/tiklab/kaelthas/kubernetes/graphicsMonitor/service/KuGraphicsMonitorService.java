package io.tiklab.kaelthas.kubernetes.graphicsMonitor.service;

import io.tiklab.kaelthas.kubernetes.graphics.model.KuGraphics;
import io.tiklab.kaelthas.kubernetes.graphicsMonitor.model.KuGraphicsMonitor;

import java.util.List;

/**
 * 图形和监控项的关联表
 */
public interface KuGraphicsMonitorService {

    //创建关联表
    void createKuGraphicsMonitor(KuGraphics kuGraphics);

    //根据图形id和监控项id删除关联表
    void deleteByCondition(String graphicsId, String monitorId);

    //根据图形id查询出监控项ids
    List<String> findMonitorIds(KuGraphicsMonitor kuGraphicsMonitor);

    /**
     * 返回 monitorIds
     */
    List<KuGraphicsMonitor> findGraphicsMonitors(String id);

    //根据图形的ids删除关联表
    void deleteByGraphicsIds(List<String> stringList);
}
