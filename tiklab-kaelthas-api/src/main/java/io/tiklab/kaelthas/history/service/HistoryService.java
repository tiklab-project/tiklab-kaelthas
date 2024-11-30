package io.tiklab.kaelthas.history.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.history.model.History;

import java.util.List;
import java.util.Map;

/**
 * 数据存储表的业务处理(上报数据存储的表)
 */
public interface HistoryService {

    //根据条件查询监控数据
    Pagination<History> findInformationPage(History history);

    //根据主机id查询出主机下配置的图表有多少,根据图表查询对应的数据返回
    List<List<History>> findInformationByGraphics(History history);

    /**
     * 根据条件查询单条数据,之前有这个业务,现在没有使用
     */
    History findInformationByLine(History history);

    //上传的数据存储到历史数据表当中
    void insertForList(List<History> entityList);

    /**
     * 根据主机id和监控项id查询上报的数据
     */
    List<History> findInformationToGatherTime(String hostId, String beforeDateTime);

    //根据条件删除历史数据
    void deleteByCondition(History history);

    //数据库监控查询
    List<List<History>> findGraphicsLine(History history);

    //k8s监控
    List<List<History>> findKuGraphicsLine(History history);

    /**
     * k8s概况页展示的信息数据
     */
    Map<String,Object> findKuOverviewTotal(String kuId);

    /**
     * 查询数据库监控的存储信息,beforeTime时间之后的
     */
    List<History> findHistoryByGatherTime(String dbId, String beforeTime);

    /**
     * 根据监控数据库的id和时间查询时间之后的存储数据
     */
    List<History> findDbHistoryByHostId(String dbId,String beforeTime);

    /**
     * 根据k8s监控的id和时间查询时间之后的存储数据
     */
    List<History> findKuHistoryByHostId(String kuId, String beforeTime);

    /**
     * 网络监控中监控模块的图形展示
     */
    List<List<History>> findInGraphicsLine(History history);

    /**
     * 查询出网络的详情页
     */
    Map<String, Object> findInternetOverview(String internetId);

    /**
     * 根据网络监控的id和指定的时间后查询存储数据
     */
    List<History> findInternetToGatherTime(String internetId, String beforeTime);

    /**
     * 查询距离当前时间指定分钟的数据
     */
    List<History> findHistoryByHostIds(String beforeTime);

    /**
     * 根据主机监控的id和指定的时间后查询存储数据
     */
    List<History> findByHostTrigger(String hostId, String beforeTime);
}
