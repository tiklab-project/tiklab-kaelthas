package io.tiklab.kaelthas.host.graphicsMonitor.service;

import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.graphicsMonitor.model.GraphicsMonitor;

import java.util.List;

@JoinProvider(model = GraphicsMonitor.class)
public interface GraphicsMonitorService {

    void insertByGraphics(GraphicsMonitor graphicsMonitor);

    void deleteByGraphicsId(String graphicsId);

    void deleteByMonitorIds(String[] strings);

    //根据监控项id删除数据
    void deleteByMonitorId(String id);

    //根据图形id查询出图形中存在的监控项信息
    List<GraphicsMonitor> findByGraphics(String graphicsId);

    //根据图形的ids删除图形与监控项的关联表
    void deleteByGraphicsIds(List<String> stringList);
}
