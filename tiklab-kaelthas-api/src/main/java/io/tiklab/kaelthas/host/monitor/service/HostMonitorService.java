package io.tiklab.kaelthas.host.monitor.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.*;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;

import java.util.List;

/**
 * 主机下的监控项
 */
@JoinProvider(model = HostMonitor.class)
public interface HostMonitorService {

    /**
     * 查询主机下监控项
     */
    Pagination<HostMonitor> findMonitorPage(HostMonitor query);

    /**
     * 查询主机下创建的监控项
     */
    Pagination<HostMonitor> findMonitorList(HostMonitor query);

    /**
     * 根据监控类型查询MonitorItem中的监控项
     */
    List<MonitorItem> findMonitorItemByName(String name);

    /**
     * 创建主机监控项
     */
    String createMonitor(HostMonitor monitor);

    /**
     * 根据监控项id删除主机监控项
     */
    void deleteMonitorById(String id);

    /**
     * 修改监控项
     */
    void updateMonitor(HostMonitor monitor);

    /**
     * 根据监控项id删除监控项信息
     */
    @FindOne
    HostMonitor findOneMonitor(String monitorId);

    /**
     * 根据监控项的ids查询list数据
     */
    @FindList
    List<HostMonitor> findList(List<String> idList);

    /**
     * 根据模板id分页查询监控项
     */
    Pagination<HostMonitor> findMonitorByTemplateId(HostMonitor hostMonitor);

    /**
     * 查询监控项字典项list
     */
    @FindAll
    List<MonitorItem> findMonitorItemAll();

    /**
     * 根据监控项ids删除数据
     */
    void deleteByHostId(String hostId);

    /**
     * 根据主机id查询监控项list
     */
    List<HostMonitor> findMonitorByHostId(String hostId);

    /**
     * 查询出主机下所有的监控项
     */
    List<HostMonitor> findAllMonitor(HostMonitor hostMonitor);

    /**
     * 根据主机ids查询出监控项list
     */
    List<HostMonitor> findHostMonitorListByHostIds(List<String> hostIds);

    //根据监控项id查询主机当中的监控项和模板当中的监控项
    List<HostMonitor> findmonitorByMonitorItemIds(List<String> monitorItemIds,String hostId);

    //根据id查询模板下创建的监控项或者是主机下创建的监控项
    List<HostMonitor> findByHostId(String id);

    /**
     * 根据条件删除监控项
     */
    void deleteCondition(HostMonitor hostMonitor);

    /**
     * 修改主机引用模板的监控项
     */
    void updateByMonitorId(HostMonitor hostMonitor);

    /**
     * 修改模板当中的监控项
     */
    void updateMonitorByTemplate(HostMonitor hostMonitor);

    /**
     * 根据模板的ids删除监控项
     */
    void deleteMonitorByItemIds(List<String> stringList);

    //根据条件进行查询
    List<HostMonitor> findCondition(HostMonitor hostMonitor);

    //查询主机下被引用的监控项信息
    List<HostMonitor> findConditionByHost(HostMonitor hostMonitor);

    //查找监控项
    Long findMonitorAllNum();

}
