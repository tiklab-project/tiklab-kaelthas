package io.tiklab.kaelthas.host.history.service;

import io.tiklab.kaelthas.host.history.model.HostHistory;
import io.tiklab.kaelthas.host.history.model.HostHistoryQuery;

import java.util.List;

public interface HostHistoryService {

    //上传监控主机信息存入历史数据表当中
    void insertForList(List<HostHistory> entityList);

    /**
     * 查询监控主机历史
     * @param hostHistoryQuery hostHistoryQuery
     */
    List<List<HostHistory>> findInformationByGraphics(HostHistoryQuery hostHistoryQuery);

    /**
     * 查询距离当前时间指定分钟的数据
     * @param beforeTime 时间
     */
    List<HostHistory> findHostHistoryByTime(String beforeTime);

    /**
     * 根据主机监控的id和指定的时间后查询存储数据
     */
    List<HostHistory> findByHostTrigger(String hostId, String beforeTime);
}
