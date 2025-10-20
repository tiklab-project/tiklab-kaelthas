package io.tiklab.kaelthas.db.history.service;

import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.db.history.model.DbHistoryQuery;

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


    /**
     * 根据监控数据库的id和时间、监控表达式查询时间之后的存储数据
     * @param dbId 数据库id
     * @param beforeTime 相对当前时间之前的时间点
     * @param expression 监控的表达式
     */
    List<DbHistory> findInHistoryByGatherTime(String dbId, String beforeTime,String expression);


}
