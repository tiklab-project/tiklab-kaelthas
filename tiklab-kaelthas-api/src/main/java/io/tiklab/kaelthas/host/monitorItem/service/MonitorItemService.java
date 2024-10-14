package io.tiklab.kaelthas.host.monitorItem.service;

import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;

import java.util.List;

@JoinProvider(model = MonitorItem.class)
public interface MonitorItemService {

    @FindOne
    MonitorItem findOneMonitorItem(String itemId);

    @FindList
    List<MonitorItem> findMonitorItemList(List<String> idList);

    List<MonitorItem> findMonitorItemByName(String name);

    List<String> findMonitorByCategories(String dataCategories);
}
