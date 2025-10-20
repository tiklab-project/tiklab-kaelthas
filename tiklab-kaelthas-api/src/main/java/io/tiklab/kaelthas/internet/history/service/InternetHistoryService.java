package io.tiklab.kaelthas.internet.history.service;

import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.internet.history.model.InternetHistoryQuery;

import java.util.List;
import java.util.Map;

public interface InternetHistoryService {

    //上传监控网络信息存入历史数据表当中
    void insertForList(List<InternetHistory> entityList);

    /**
     * 查询网络监控数据库数据库历史
     * @param internetHistoryQuery internetHistoryQuery
     */
    List<List<InternetHistory>> findGraphicsLine(InternetHistoryQuery internetHistoryQuery);


    /**
     * 查询距离当前时间指定分钟的数据 （当前月的时间）
     * @param beforeTime 时间
     */
    List<InternetHistory> findInHistoryByTime(String beforeTime);

    /**
     * 查询出网络的详情页
     */
    Map<String, Object> findInternetOverview(String internetId);


    /**
     * 根据网络监控的id和指定的时间后查询存储数据
     */
    List<InternetHistory> findInternetToGatherTime(String internetId, String beforeTime,String expression);
}
