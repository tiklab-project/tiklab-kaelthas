package io.thoughtware.kaelthas.host.hostDynamic.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.host.hostDynamic.model.HostDynamic;

public interface HostDynamicService {
    Pagination<HostDynamic> findPage(HostDynamic hostDynamic);

    void createHostDynamic(HostDynamic hostDynamic);
}
