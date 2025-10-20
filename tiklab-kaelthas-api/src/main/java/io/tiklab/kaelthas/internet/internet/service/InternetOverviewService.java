package io.tiklab.kaelthas.internet.internet.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.internet.model.InternetOverview;
import io.tiklab.kaelthas.internet.internet.model.InternetOverviewQuery;

import java.util.List;
import java.util.Map;

/**
 * 网络监控概览信息存储表
 */
public interface InternetOverviewService {


    //创建网络监控概览
    String createInternetOverview(InternetOverview internetOverview);

    //更新网络监控概览
    void updateInternetOverview(InternetOverview internetOverview);


    //根据监控网络的id查询监控网络的信息
    Map<String, Object> findInternetByInId(String internetId);


    //根据监控网络的id查询监控网络的信息
    List<InternetOverview> findInternetList(InternetOverviewQuery internetOverviewQuery);

}
