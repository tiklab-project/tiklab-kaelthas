package io.tiklab.kaelthas.internet.triggerMedium.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.kaelthas.util.SqlUtil;
import io.tiklab.kaelthas.internet.triggerMedium.dao.InTriggerMediumDao;
import io.tiklab.kaelthas.internet.triggerMedium.entity.InTriggerMediumEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 触发器和消息渠道的关联表
 */
@Service
public class InTriggerMediumServiceImpl implements InTriggerMediumService{

    @Autowired
    private InTriggerMediumDao inTriggerMediumDao;

    //创建触发器和消息渠道的中间表
    @Override
    public void createTriggerMedium(String string, List<String> mediumType) {
        //将插入的数据拼装成sql
        List<Map<String, Object>> list = mediumType.stream().map(id -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", RandomStringUtils.randomAlphabetic(12));
            map.put("trigger_id", string);
            map.put("medium_id", id);
            return map;
        }).collect(Collectors.toList());
        String sql = SqlUtil.getBatchInsertSql("mtc_internet_trigger_medium", list);
        inTriggerMediumDao.createTriggerMedium(sql);
    }

    //根据触发器id删除关联信息
    @Override
    public void deleteByTrigger(String id) {
        if (StringUtils.isBlank(id)){
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(InTriggerMediumEntity.class)
                .eq("triggerId", id)
                .get();
        inTriggerMediumDao.deleteByTrigger(deleteCondition);
    }

    //根据触发器ids删除关联表信息
    @Override
    public void deleteByTriggerIds(List<String> triggerIds) {
        if (triggerIds.isEmpty()) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(InTriggerMediumEntity.class)
                .in("triggerId", triggerIds.toArray())
                .get();
        inTriggerMediumDao.deleteByTriggerIds(deleteCondition);
    }
}
