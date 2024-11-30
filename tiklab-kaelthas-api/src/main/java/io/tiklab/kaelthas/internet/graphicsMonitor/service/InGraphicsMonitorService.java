package io.tiklab.kaelthas.internet.graphicsMonitor.service;

import java.util.List;

/**
 * 图形和监控项的关联表
 */
public interface InGraphicsMonitorService {

    //创建图形和监控项的关联表
    void createGraphicsMonitorList(String string, List<String> monitorIds);

    //根据图形id删除关联表
    void deleteByGraphics(String graphicsId);

    //根据图形id查询出监控项的ids
    List<String> findMonitorIds(String id);

    //根据图形的ids删除图形
    void deleteByGraphicsIds(List<String> stringList);
}
