package io.thoughtware.kaelthas.host.triggerExpression.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.host.triggerExpression.entity.TriggerExpressionEntity;
import io.thoughtware.kaelthas.host.triggerExpression.model.TriggerExpression;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TriggerExpressionDao {

    @Resource
    private JpaTemplate jpaTemplate;

    /**
     * 根据触发器id删除触发器表达式当中的数据
     */
    public void deleteByTriggerId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 根据监控项id删除触发器表达式当中的数据
     */
    public void deleteByMonitorId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void deleteByMonitorIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<TriggerExpression> findListById(String triggerId) {

        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerExpression.class)
                .eq("triggerId", triggerId)
                .get();

        return jpaTemplate.findList(queryCondition, TriggerExpression.class);
    }

    public List<TriggerExpressionEntity> findTriggerExpressionAll() {
        return jpaTemplate.findAll(TriggerExpressionEntity.class);
    }

    public void updateTriggerExpression(TriggerExpressionEntity triggerExpressionEntity) {
        jpaTemplate.update(triggerExpressionEntity);
    }

    public void createTriggerExpression(TriggerExpressionEntity triggerExpressionEntity) {
        jpaTemplate.save(triggerExpressionEntity, TriggerExpressionEntity.class);
    }
}
