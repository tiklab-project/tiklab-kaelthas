package io.tiklab.kaelthas.host.triggerExpression.service;

import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.kaelthas.host.triggerExpression.model.TriggerExpression;

import java.util.List;

/**
 * 触发器表达式(当前没有使用)
 */
public interface TriggerExpressionService {

    /**
     * 根据触发器id查询触发器表达式的list
     */
    @FindList
    List<TriggerExpression> findTriggerExpressionList(String triggerId);

    /**
     * 查询所有的触发器表达式list
     */
    @FindAll
    List<TriggerExpression> findTriggerExpressionAll();


}
