package io.tiklab.kaelthas.internet.internetTrigger.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.internetTrigger.model.InTrigger;

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

    List<InTrigger> findLikeTrigger(String hostId, String expression);
}
