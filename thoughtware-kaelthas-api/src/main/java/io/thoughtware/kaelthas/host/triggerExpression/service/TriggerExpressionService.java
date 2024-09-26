package io.thoughtware.kaelthas.host.triggerExpression.service;

import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.kaelthas.host.triggerExpression.model.TriggerExpression;

import java.util.List;

public interface TriggerExpressionService {

    @FindList
    List<TriggerExpression> findTriggerExpressionList(String triggerId);

    @FindAll
    List<TriggerExpression> findTriggerExpressionAll();

    void deleteByMonitorId(String monitorId);

    void deleteByMonitorIds(String[] monitorIds);

    void updateTriggerExpression(TriggerExpression triggerExpression);

    void deleteByTriggerId(String triggerId);

    void createTriggerExpression(TriggerExpression entity);
}
