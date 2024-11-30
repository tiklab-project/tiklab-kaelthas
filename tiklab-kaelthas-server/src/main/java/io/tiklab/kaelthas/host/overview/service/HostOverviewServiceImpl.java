package io.tiklab.kaelthas.host.overview.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;
import io.tiklab.kaelthas.host.hostDynamic.service.HostDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 主机的概况页
 */
@Service
public class HostOverviewServiceImpl implements HostOverviewService{

    @Autowired
    private HostDynamicService hostDynamicService;

    @Autowired
    private HostService hostService;

    /**
     * 主机概况的动态信息查询
     */
    @Override
    public Pagination<HostDynamic> findDynamicList(HostDynamic hostDynamic) {
        return hostDynamicService.findPage(hostDynamic);
    }

    /**
     * 根据主机的id进行查询主机下监控项和触发器数量信息
     */
    @Override
    public Host findHostById(String id) {
        return hostService.findHostById(id);
    }
}
