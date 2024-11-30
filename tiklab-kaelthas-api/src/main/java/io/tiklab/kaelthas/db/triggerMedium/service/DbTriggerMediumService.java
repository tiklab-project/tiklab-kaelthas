package io.tiklab.kaelthas.db.triggerMedium.service;

import io.tiklab.kaelthas.db.trigger.model.DbTrigger;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.db.triggerMedium.model.DbTriggerMedium;

/**
 * 触发器和消息渠道的关联业务(关联表)
 */
@JoinProvider(model = DbTriggerMedium.class)
public interface DbTriggerMediumService {

    /**
     * 创建触发器和消息渠道的关联信息
     */
    void createTriggerMedium(DbTrigger dbTrigger);

    /**
     * 根据触发器的id删除信息
     */
    void deleteByTriggerId(String id);

    /**
     * 根据监控数据库的id删除关联表
     */
    void deleteByDbId(String dbId);
}
