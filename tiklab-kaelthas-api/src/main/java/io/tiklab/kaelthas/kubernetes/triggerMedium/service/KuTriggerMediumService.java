package io.tiklab.kaelthas.kubernetes.triggerMedium.service;

import java.util.List;

/**
 * 触发器和消息渠道中间表
 */
public interface KuTriggerMediumService {

    /**
     * 根据触发器ids删除中间表
     */
    void deleteByTriggerIds(List<String> stringList);

    /**
     * 创建中间表
     */
    void createKuTriggerMedium(String triggerId, List<String> mediumType);

    /**
     * 根据触发器id删除中间表
     */
    void deleteByTriggerId(String triggerId);
}
