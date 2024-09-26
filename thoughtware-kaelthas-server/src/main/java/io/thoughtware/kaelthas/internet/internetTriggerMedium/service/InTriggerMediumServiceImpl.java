package io.thoughtware.kaelthas.internet.internetTriggerMedium.service;

import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.kaelthas.common.util.SqlUtil;
import io.thoughtware.kaelthas.internet.internetTriggerMedium.dao.InTriggerMediumDao;
import io.thoughtware.kaelthas.internet.internetTriggerMedium.entity.InTriggerMediumEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InTriggerMediumServiceImpl implements InTriggerMediumService{

    @Autowired
    private InTriggerMediumDao inTriggerMediumDao;

    @Override
    public void createTriggerMedium(String string, List<String> mediumType) {
        //将插入的数据拼装成sql
        List<Map<String, Object>> list = mediumType.stream().map(id -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", RandomStringUtils.randomAlphabetic(12));
            map.put("trigger_id", string);
            map.put("medium_id", id);
            return map;
        }).toList();
        String sql = SqlUtil.getBatchInsertSql("mtc_internet_trigger_medium", list);
        inTriggerMediumDao.createTriggerMedium(sql);
    }

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
