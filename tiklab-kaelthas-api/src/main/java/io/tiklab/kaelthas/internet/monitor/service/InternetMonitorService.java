package io.tiklab.kaelthas.internet.monitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.item.model.InternetItem;
import io.tiklab.kaelthas.internet.monitor.model.InternetMonitor;

import java.util.List;

/**
 * 网络监控中的监控项
 */
public interface InternetMonitorService {

    /**
     * 分页查询监控项
     */
    Pagination<InternetMonitor> findMonitorPage(InternetMonitor internetMonitor);

    //创建监控项
    String createMonitor(InternetMonitor internetMonitor);

    //删除监控项
    void deleteMonitor(String id);

    //修改监控项
    void updateMonitor(InternetMonitor internetMonitor);

    //根据监控网络的id查询出监控项的list
    List<InternetMonitor> findMonitorByInId(InternetMonitor internetMonitor);

    //根据监控网络的id删除监控项
    void deleteByInternet(String id);

    /**
     * 根据类型查询对应的item
     */
    List<InternetItem> findItemList(InternetItem internetItem);
}
