package io.tiklab.kaelthas.internet.trigger.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.trigger.model.InTrigger;

import java.util.List;

/**
 * 网络监控中的触发器
 */
public interface InTriggerService {

    //分页查询触发器
    Pagination<InTrigger> findTriggerPage(InTrigger inTrigger);

    //创建触发器
    String createInTrigger(InTrigger inTrigger);

    //根据id删除触发器
    void deleteTrigger(String id);

    //修改触发器
    void updateTrigger(InTrigger inTrigger);

    //根据网络id删除触发器
    void deleteByInId(String internetId);

    //根据网络id查询出网络下的触发器list
    List<String> findTriggerByInId(String internetId);

    //根据监控项表达式模糊查询触发器表达式
    List<InTrigger> findLikeTrigger(String hostId, String expression);
}
