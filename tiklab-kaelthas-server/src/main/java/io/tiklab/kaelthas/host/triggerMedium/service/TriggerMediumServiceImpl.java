package io.tiklab.kaelthas.host.triggerMedium.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.kaelthas.host.triggerMedium.dao.TriggerMediumDao;
import io.tiklab.kaelthas.host.triggerMedium.entity.TriggerMediumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class TriggerMediumServiceImpl implements TriggerMediumService {

    @Autowired
    private TriggerMediumDao triggerMediumDao;

    @Override
    public void createTriggerMedium(String triggerId, List<String> mediumIds) {
        for (String mediumId : mediumIds) {
            TriggerMediumEntity triggerMediumEntity = new TriggerMediumEntity();
            triggerMediumEntity.setTriggerId(triggerId);
            triggerMediumEntity.setMediumId(mediumId);
            triggerMediumDao.createTriggerMedium(triggerMediumEntity);
        }
    }

    @Override
    public void deleteByTriggerId(String id) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(TriggerMediumEntity.class)
                .eq("triggerId", id)
                .get();
        triggerMediumDao.deleteByTriggerId(deleteCondition);
    }

    @Override
    public List<String> findMediumIdByTriggerId(String triggerId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerMediumEntity.class)
                .eq("triggerId", triggerId)
                .get();
        List<TriggerMediumEntity> triggerMediumEntities = triggerMediumDao.findMediumIdByTriggerId(queryCondition);

        return triggerMediumEntities.stream().map(TriggerMediumEntity::getMediumId).toList();
    }
}
