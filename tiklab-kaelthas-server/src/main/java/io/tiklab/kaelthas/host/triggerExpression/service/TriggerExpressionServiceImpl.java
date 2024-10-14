package io.tiklab.kaelthas.host.triggerExpression.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.triggerExpression.dao.TriggerExpressionDao;
import io.tiklab.kaelthas.host.triggerExpression.entity.TriggerExpressionEntity;
import io.tiklab.kaelthas.host.triggerExpression.model.TriggerExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriggerExpressionServiceImpl implements TriggerExpressionService {

    @Autowired
    private TriggerExpressionDao triggerExpressionDao;

    @Override
    public List<TriggerExpression> findTriggerExpressionList(String triggerId) {
        return triggerExpressionDao.findListById(triggerId);
    }

    @Override
    public List<TriggerExpression> findTriggerExpressionAll() {
        List<TriggerExpressionEntity> expressionEntityList = triggerExpressionDao.findTriggerExpressionAll();
        return BeanMapper.mapList(expressionEntityList, TriggerExpression.class);
    }

    @Override
    public void deleteByMonitorId(String monitorId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(TriggerExpressionEntity.class)
                .eq("monitorId", monitorId)
                .get();
        triggerExpressionDao.deleteByMonitorId(deleteCondition);
    }

    @Override
    public void deleteByMonitorIds(String[] monitorIds) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(TriggerExpressionEntity.class)
                .in("monitorId", monitorIds)
                .get();
        triggerExpressionDao.deleteByMonitorIds(deleteCondition);
    }

    @Override
    public void updateTriggerExpression(TriggerExpression triggerExpression) {
        TriggerExpressionEntity triggerExpressionEntity = BeanMapper.map(triggerExpression, TriggerExpressionEntity.class);
        triggerExpressionDao.updateTriggerExpression(triggerExpressionEntity);
    }

    @Override
    public void deleteByTriggerId(String triggerId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(TriggerExpressionEntity.class)
                .eq("triggerId", triggerId)
                .get();
        triggerExpressionDao.deleteByTriggerId(deleteCondition);
    }

    @Override
    public void createTriggerExpression(TriggerExpression entity) {
        TriggerExpressionEntity triggerExpressionEntity = BeanMapper.map(entity, TriggerExpressionEntity.class);
        triggerExpressionDao.createTriggerExpression(triggerExpressionEntity);
    }
}
