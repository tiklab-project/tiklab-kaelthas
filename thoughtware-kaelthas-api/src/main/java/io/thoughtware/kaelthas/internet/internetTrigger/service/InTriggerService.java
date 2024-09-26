package io.thoughtware.kaelthas.internet.internetTrigger.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.internet.internetTrigger.model.InTrigger;

import java.util.List;

public interface InTriggerService {
    Pagination<InTrigger> findTriggerPage(InTrigger inTrigger);

    String createInTrigger(InTrigger inTrigger);

    void deleteTrigger(String id);

    void updateTrigger(InTrigger inTrigger);

    //根据网络id删除触发器
    void deleteByInId(String internetId);

    //根据网络id查询出网络下的触发器集合
    List<String> findTriggerByInId(String internetId);
}
