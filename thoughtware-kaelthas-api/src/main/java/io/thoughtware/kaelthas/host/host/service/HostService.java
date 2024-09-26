package io.thoughtware.kaelthas.host.host.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.host.host.model.Host;
import io.thoughtware.kaelthas.host.monitor.model.HostMonitor;

import java.util.List;
import java.util.Map;

@JoinProvider(model = Host.class)
public interface HostService {

    Pagination<Host> findPageHost(Host host);

    String createHost(Host host);

    void updateHost(Host host);

    @FindOne
    Host findOneHost(String id);

    //根据主机的id进行查询主机下监控项等信息
    Host findHostById(String id);

    void deleteHostById(String id);

    /**
     * 根据主机ip查询主机下所有监控项
     */
    List<HostMonitor> findMonitorItemListByHostIp(String ip);

    //监测页面查询的主机列表信息
    Pagination<Host> findHostPage(Host host);

    List<HostMonitor> findMonitorForHost(Host host);

    //查询最近主机
    List<Host> findRecentHostList(String hostId);

    @FindList
    List<Host> findList(List<String> ids);

    Map<String, Integer> findHostNumber();

    @FindAll
    List<Host> findAllHost();

    void updateHostByHostId(String hostId);

    void updateHostStatus(String hostId,Integer status);

    //查找可用主机数量和主机总数量
    Map<String, Long> findHostUsage();

}
