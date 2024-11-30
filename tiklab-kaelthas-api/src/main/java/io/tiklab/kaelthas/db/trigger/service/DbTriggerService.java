package io.tiklab.kaelthas.db.trigger.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.db.trigger.model.DbTrigger;

import java.util.List;

/**
 * 监控项中的触发器
 */
@JoinProvider(model = DbTrigger.class)
public interface DbTriggerService {

    /**
     * 触发器的分页查询
     */
    Pagination<DbTrigger> findDbTriggerPage(DbTrigger dbTrigger);

    /**
     * 触发器的创建
     */
    String createDbTrigger(DbTrigger dbTrigger);

    /**
     * 根据触发器id删除触发器信息和关联表信息
     */
    void deleteDbTrigger(String id);

    /**
     * 修改触发器
     */
    void updateDbTrigger(DbTrigger dbTrigger);

    /**
     * 查询出可用的触发器list
     */
    List<DbTrigger> findListByDbId(String hostId);

    /**
     * 根据表达式模糊查询触发器list
     */
    List<DbTrigger> findLikeTrigger(String hostId, String expression);

    /**
     * 根据监控数据库的id删除触发器
     */
    void deleteByDbId(String id);
}
