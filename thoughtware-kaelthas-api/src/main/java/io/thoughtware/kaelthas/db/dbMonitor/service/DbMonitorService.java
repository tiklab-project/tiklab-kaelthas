package io.thoughtware.kaelthas.db.dbMonitor.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.db.dbMonitor.model.DbMonitor;

import java.util.List;

@JoinProvider(model = DbMonitor.class)
public interface DbMonitorService {

    //分页查询
    Pagination<DbMonitor> findDbMonitorPage(DbMonitor dbMonitor);

    //创建监控项
    String createDbMonitor(DbMonitor dbMonitor);

    //根据id删除监控项
    void deleteDbMonitor(String id);

    //修改监控项信息
    void updateDbMonitor(DbMonitor dbMonitor);

    //根据dbId查询当前数据库下的所有监控项信息
    List<DbMonitor> findAllMonitor(DbMonitor dbMonitor);

    //根据monitorId查询监控项的信息
    DbMonitor findListById(String monitorId);

    //根据dbId查询监控项的数量
    List<DbMonitor> findMonitorNum(String dbId);
}
