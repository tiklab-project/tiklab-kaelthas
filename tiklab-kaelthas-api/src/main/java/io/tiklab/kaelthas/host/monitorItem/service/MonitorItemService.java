package io.tiklab.kaelthas.host.monitorItem.service;

import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;

import java.util.List;

@JoinProvider(model = MonitorItem.class)
public interface MonitorItemService {

    /**
     * 查询单个监控项字典数据
     */
    @FindOne
    MonitorItem findOneMonitorItem(String itemId);

    /**
     * 根据ids查询字典表list
     */
    @FindList
    List<MonitorItem> findMonitorItemList(List<String> idList);

    /**
     * 根据字类型查询字典项list
     */
    List<MonitorItem> findMonitorItemByName(String name);

    /**
     * 根据类型查询字典项ids
     */
    List<String> findMonitorByCategories(String dataCategories);
}
