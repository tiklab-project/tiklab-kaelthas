package io.thoughtware.kaelthas.history.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.db.database.model.DbInfo;

import java.util.List;
import java.util.Map;

public interface HistoryService {

    //根据条件查询监控数据
    Pagination<History> findInformationPage(History history);

    //根据主机id查询出主机下配置的图表有多少,根据图表查询对应的数据返回
    List<List<History>> findInformationByGraphics(History history);

    History findInformationByLine(History history);

    //上传的数据存储到历史数据表当中
    void insertForList(List<History> entityList);

    List<History> findInformationToGatherTime(String hostId, String beforeDateTime);

    //根据条件删除历史数据
    void deleteByCondition(History history);

    void findHistoryByCondition(History history);

    //数据库监控查询
    List<List<History>> findGraphicsLine(History history);

    //k8s监控
    List<List<History>> findKuGraphicsLine(History history);

    Map<String,Object> findKuOverviewTotal(String kuId);

    List<History> findHistoryByGatherTime(String hostId, String beforeTime);

    List<History> findDbHistoryByHostId(String hostId,String beforeTime);

    List<History> findKuHistoryByHostId(String kuId, String beforeTime);

    List<List<History>> findInGraphicsLine(History history);

    Map<String, Object> findInternetOverview(String internetId);

    List<History> findInHistoryByHostId(String internetId, String beforeTime);

    List<History> findInternetToGatherTime(String internetId, String beforeTime);

    List<History> findHistoryByHostId(String id,String beforeTime);
}
