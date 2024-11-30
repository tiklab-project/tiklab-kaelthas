package io.tiklab.kaelthas.internet.overview.service;

import java.util.Map;

/**
 * 网络监控概况页
 */
public interface InOverviewService {

    /**
     * 获取网络详情
     */
    Map<String, Object> findInternetOverview(String internetId);
}
