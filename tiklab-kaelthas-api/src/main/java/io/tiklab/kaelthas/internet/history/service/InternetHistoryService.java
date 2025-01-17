package io.tiklab.kaelthas.internet.history.service;

import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.internet.history.model.InternetHistoryQuery;

import java.util.List;

public interface InternetHistoryService {

    //上传监控网络信息存入历史数据表当中
    void insertForList(List<InternetHistory> entityList);

    /**
     * 查询网络监控数据库数据库历史
     * @param internetHistoryQuery internetHistoryQuery
     */
    List<List<InternetHistory>> findGraphicsLine(InternetHistoryQuery internetHistoryQuery);


    /**
     * 查询距离当前时间指定分钟的数据
     * @param beforeTime 时间
     */
    List<InternetHistory> findInHistoryByTime(String beforeTime);
}
