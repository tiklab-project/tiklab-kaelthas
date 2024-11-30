package io.tiklab.kaelthas.db.graphicsMonitor.service;


import io.tiklab.kaelthas.db.graphics.model.DbGraphics;
import io.tiklab.kaelthas.db.graphicsMonitor.model.DbGraphicsMonitor;

import java.util.List;

/**
 * 监控数据库的监控项和图形的中间表
 */
public interface DbGraphicsMonitorService {

    //根据图形id删除关联表
    void deleteByCation(String graphicsId, String monitorId,String dbId);

    //根据提供的监控项ids添加中间表
    void createGraphicsMonitor(DbGraphics dbGraphics);

    //查询图形下的监控项ids
    List<String> findMonitorIds(String graphicsId);

    /**
     * 根据图形的id查询关联表的信息
     */
    List<DbGraphicsMonitor> findMonitors(String graphicsId);
}
