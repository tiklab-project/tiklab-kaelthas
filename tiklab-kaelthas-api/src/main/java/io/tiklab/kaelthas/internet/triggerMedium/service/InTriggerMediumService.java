package io.tiklab.kaelthas.internet.triggerMedium.service;

import java.util.List;

/**
 * 触发器和消息渠道的关联表
 */
public interface InTriggerMediumService {

    //创建触发器和消息渠道的中间表
    void createTriggerMedium(String string, List<String> mediumType);

    //根据触发器id删除关联信息
    void deleteByTrigger(String id);

    //根据触发器ids删除
    void deleteByTriggerIds(List<String> triggerIds);
}
