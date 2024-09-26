package io.thoughtware.kaelthas.host.hostGroup.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.host.hostGroup.model.HostGroup;

import java.util.List;

@JoinProvider(model = HostGroup.class)
public interface HostGroupService {


    List<HostGroup> findHostGroupByName();

    @FindOne
    HostGroup findHostGroupById(String id);

    @FindAll
    List<HostGroup> findAllHostGroupList();

    Pagination<HostGroup> findHostGroupPage(HostGroup hostGroup);

    void createHostGroup(HostGroup hostGroup);

    void deleteHostGroup(String id);

    void updateHostGroup(HostGroup hostGroup);

    //查询出所有主机组数量
    Long findHostGroupAllNum();

}
