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
     * 根据监控数据库id和时间蛋查询上报的数据
     * @param dbId 监控数据库的id
     *
     */
    List<DbHistory> findInformationToGatherTime(String dbId, String beforeDateTime);

    /**
     * 查询数据库监控的存储信息,beforeTime时间之后的
     */
    List<DbHistory> findHistoryByGatherTime(String dbId, String beforeTime);



    /**
     * 根据监控数据库的id和时间查询时间之后的存储数据
     */
    List<DbHistory> findDbHistoryByDbId(String dbId,String beforeTime);


}
