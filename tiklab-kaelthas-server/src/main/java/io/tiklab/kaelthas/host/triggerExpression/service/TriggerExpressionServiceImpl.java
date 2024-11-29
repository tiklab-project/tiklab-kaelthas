package io.tiklab.kaelthas.host.triggerExpression.service;

import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.triggerExpression.dao.TriggerExpressionDao;
import io.tiklab.kaelthas.host.triggerExpression.entity.TriggerExpressionEntity;
import io.tiklab.kaelthas.host.triggerExpression.model.TriggerExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 触发器表达式(当前没有使用)
 */
@Service
public class TriggerExpressionServiceImpl implements TriggerExpressionService {

    @Autowired
    private TriggerExpressionDao triggerExpressionDao;

    //根据触发器id查询触发器表达式的list
    @Override
    public List<TriggerExpression> findTriggerExpressionList(String triggerId) {
        return triggerExpressionDao.findListById(triggerId);
    }

    //查询所有的触发器表达式list
    @Override
    public List<TriggerExpression> findTriggerExpressionAll() {
        List<TriggerExpressionEntity> expressionEntityList = triggerExpressionDao.findTriggerExpressionAll();
        return BeanMapper.mapList(expressionEntityList, TriggerExpression.class);
    }

}
