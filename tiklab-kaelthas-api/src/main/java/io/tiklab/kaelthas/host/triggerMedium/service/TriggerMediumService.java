package io.tiklab.kaelthas.host.triggerMedium.service;


import java.util.List;

/**
 * 触发器和消息渠道的中间表
 */
public interface TriggerMediumService {
    /**
     * 创建关联表信息
     */
    void createTriggerMedium(String triggerId, List<String> mediumIds);

    /**
     * 根据触发器id删除关联表
     */
    void deleteByTriggerId(String id);

    /**
     * 根据触发器id查询消息渠道的ids
     */
    List<String> findMediumIdByTriggerId(String triggerId);
}
