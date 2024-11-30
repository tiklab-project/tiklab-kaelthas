package io.tiklab.kaelthas.internet.item.service;

import io.tiklab.kaelthas.internet.item.model.InternetItem;

import java.util.List;

/**
 * 网络监控项的字典项
 */
public interface InternetItemService {

    /**
     * 根据网络设备的类型查询网络的监控项字典项
     */
    List<InternetItem> findItemList(InternetItem internetItem);
}
