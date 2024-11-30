package io.tiklab.kaelthas.host.overview.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;

/**
 * 主机的概况页
 */
public interface HostOverviewService {

    /**
     * 主机概况的动态信息查询
     */
    Pagination<HostDynamic> findDynamicList(HostDynamic hostDynamic);

    /**
     * 根据主机的id进行查询主机下监控项和触发器数量信息
     */
    Host findHostById(String id);
}
