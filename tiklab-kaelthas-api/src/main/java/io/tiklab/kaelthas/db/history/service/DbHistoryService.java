package io.tiklab.kaelthas.db.history.service;

import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.db.history.model.DbHistoryQuery;
import io.tiklab.kaelthas.history.model.History;

import java.util.List;

public interface DbHistoryService {

    //上传数据库信息存入历史数据表当中
    void insertForList(List<DbHistory> entityList);

    /**
     * 查询监控数据库数据库历史
     * @param dbHistoryQuery dbHistoryQuery
     */
    List<List<DbHistory>> findGraphicsLine(DbHistoryQuery dbHistoryQuery);

    /**
     * 查询距离当前时间指定分钟的数据
     * @param beforeTime 时间
     */
    List<DbHistory> findDbHistoryByTime(String beforeTime);
}
