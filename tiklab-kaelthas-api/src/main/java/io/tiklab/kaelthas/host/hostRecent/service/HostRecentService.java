package io.tiklab.kaelthas.host.hostRecent.service;

import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.hostRecent.model.HostRecent;

import java.util.List;

/**
 * 最近使用的主机
 */
@JoinProvider(model = HostRecent.class)
public interface HostRecentService {

    /**
     * 查询出最近使用的主机list
     */
    List<HostRecent> findRecentHostList();

    /**
     * 创建最近主机
     */
    String createHostRecent(HostRecent hostRecent);

    /**
     * 根据id删除最近主机
     */
    void deleteByHostId(String hostId);

    /**
     * 查询出最近使用的四个主机信息
     */
    List<HostRecent> findHostRecentList();

    /**
     * 根据id查询最近使用主机
     */
    @FindOne
    HostRecent findHostRecentOne(String id);

    /**
     * 查询所有最近使用主机
     */
    @FindAll
    List<HostRecent> findAllHostRecent();

    /**
     * 修改最近使用的主机信息
     */
    void updateHostRecent(HostRecent hostRecent);
}
