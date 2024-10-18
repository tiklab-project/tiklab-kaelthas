package io.tiklab.kaelthas.host.monitor.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.*;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;

import java.util.List;

@JoinProvider(model = HostMonitor.class)
public interface HostMonitorService {
    Pagination<HostMonitor> findMonitorPage(HostMonitor query);

    Pagination<HostMonitor> findMonitorList(HostMonitor query);

    List<MonitorItem> findMonitorItemByName(String name);

    String createMonitor(HostMonitor monitor);

    void deleteMonitorById(String id);

    void updateMonitor(HostMonitor monitor);

    @FindOne
    HostMonitor findOneMonitor(String monitorId);

    @FindList
    List<HostMonitor> findList(List<String> idList);

    Pagination<HostMonitor> findMonitorByTemplateId(HostMonitor hostMonitor);

    @FindAll
    List<MonitorItem> findMonitorItemAll();

    void deleteByHostId(String hostId);

    List<HostMonitor> findMonitorByHostId(String hostId);

    List<HostMonitor> findAllMonitor(HostMonitor hostMonitor);

    List<HostMonitor> findHostMonitorListByHostIds(List<String> hostIds);

    //根据监控项id查询主机当中的监控项和模板当中的监控项
    List<HostMonitor> findmonitorByMonitorItemIds(List<String> monitorItemIds,String hostId);

    Integer findHostNumber();

    //根据id查询模板下创建的监控项或者是主机下创建的监控项
    List<HostMonitor> findByHostId(String id);

    void deleteCondition(HostMonitor hostMonitor);

    void updateByMonitorId(HostMonitor hostMonitor);

    void updateMonitorByTemplate(HostMonitor hostMonitor);

    void deleteMonitorByItemIds(List<String> stringList);


    //根据条件进行查询
    List<HostMonitor> findCondition(HostMonitor hostMonitor);

    //查询主机下被引用的监控项信息
    List<HostMonitor> findConditionByHost(HostMonitor hostMonitor);

    //查找监控项
    Long findMonitorAllNum();

}
