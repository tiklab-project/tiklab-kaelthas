package io.tiklab.kaelthas.host.hostDynamic.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;

public interface HostDynamicService {
    Pagination<HostDynamic> findPage(HostDynamic hostDynamic);

    void createHostDynamic(HostDynamic hostDynamic);
}
