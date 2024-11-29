package io.tiklab.kaelthas.db.dbMonitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.db.dbMonitor.model.DbMonitor;

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

    //根据dbId查询监控项的数量
    List<DbMonitor> findMonitorNum(String dbId);

    void deleteByDbId(String id);
}
