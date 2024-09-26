package io.thoughtware.kaelthas.host.hostRecent.service;

import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.host.hostRecent.model.HostRecent;

import java.util.List;

@JoinProvider(model = HostRecent.class)
public interface HostRecentService {

    //查询主机列表
    List<HostRecent> findRecentHostList();

    String createHostRecent(HostRecent hostRecent);

    void deleteByHostId(String hostId);

    List<HostRecent> findHostRecentList();

    @FindOne
    HostRecent findHostRecentOne(String id);

    @FindAll
    List<HostRecent> findAllHostRecent();

    void updateHostRecent(HostRecent hostRecent);
}
