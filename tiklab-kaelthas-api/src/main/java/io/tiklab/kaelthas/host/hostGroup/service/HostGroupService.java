package io.tiklab.kaelthas.host.hostGroup.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;

import java.util.List;

/**
 * 主机组
 */
@JoinProvider(model = HostGroup.class)
public interface HostGroupService {

    /**
     * 查询所有主机组信息
     */
    List<HostGroup> findHostGroupByName();

    /**
     * 根据id查询主机组信息
     */
    @FindOne
    HostGroup findHostGroupById(String id);

    /**
     * 查询所有主机组的list信息
     */
    @FindAll
    List<HostGroup> findAllHostGroupList();

    /**
     * 主机组分页查询
     */
    Pagination<HostGroup> findHostGroupPage(HostGroup hostGroup);

    /**
     * 创建主机组
     */
    void createHostGroup(HostGroup hostGroup);

    /**
     * 删除主机组
     */
    void deleteHostGroup(String id);

    /**
     * 修改主机组
     */
    void updateHostGroup(HostGroup hostGroup);

    /**
     * 查询主机组数量
     */
    Long findHostGroupAllNum();

}
