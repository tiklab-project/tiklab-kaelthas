package io.tiklab.kaelthas.host.hostGroup.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;

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
