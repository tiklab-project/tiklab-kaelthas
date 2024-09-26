package io.thoughtware.kaelthas.db.dbGraphicsMonitor.service;


import io.thoughtware.kaelthas.db.dbGraphics.model.DbGraphics;
import io.thoughtware.kaelthas.db.dbGraphicsMonitor.model.DbGraphicsMonitor;

import java.util.List;

public interface DbGraphicsMonitorService {

    //根据图形id删除关联表
    void deleteByCation(String graphicsId, String monitorId);

    //根据提供的监控项ids添加中间表
    void createGraphicsMonitor(DbGraphics dbGraphics);

    //查询图形下的监控项ids
    List<String> findMonitorIds(String graphicsId);

    List<DbGraphicsMonitor> findMonitors(String graphicsId);
}
