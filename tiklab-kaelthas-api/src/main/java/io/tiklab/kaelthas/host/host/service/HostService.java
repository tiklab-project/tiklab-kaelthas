package io.tiklab.kaelthas.host.host.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;

import java.util.List;
import java.util.Map;

@JoinProvider(model = Host.class)
public interface HostService {

    /**
     * 创建监控主机
     */
    String createHost(Host host);

    /**
     * 修改主机
     */
    void updateHost(Host host);

    /**
     * findOne方法,根据id查询单个主机信息
     */
    @FindOne
    Host findOneHost(String id);

    //根据主机的id进行查询主机下监控项等信息
    Host findHostById(String id);

    /**
     * 根据id删除主机
     */
    void deleteHostById(String id);

    /**
     * 根据主机ip查询主机下所有监控项
     */
    List<HostMonitor> findMonitorItemListByHostIp(String ip);

    //监测页面查询的主机列表信息
    Pagination<Host> findHostPage(Host host);

    /**
     * 根据ids查询主机的列表信息
     */
    @FindList
    List<Host> findList(List<String> ids);

    /**
     * 查询所有的主机信息
     */
    @FindAll
    List<Host> findAllHost();

    /**
     * 查找可用主机数量和主机总数量
     */
    Map<String, Long> findHostUsage();

    /**
     * 查询所有主机组信息
     */
    List<HostGroup> findAllHostGroupList();

}
