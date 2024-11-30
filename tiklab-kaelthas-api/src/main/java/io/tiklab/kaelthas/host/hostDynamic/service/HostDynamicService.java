package io.tiklab.kaelthas.host.hostDynamic.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;

/**
 * 主机下的动态
 */
public interface HostDynamicService {

    /**
     * 主机下的动态信息,分页查询
     */
    Pagination<HostDynamic> findPage(HostDynamic hostDynamic);

    /**
     * 创建主机下的动态信息
     */
    void createHostDynamic(HostDynamic hostDynamic);
}
