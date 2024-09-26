package io.thoughtware.kaelthas.host.triggerMedium.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.host.triggerMedium.entity.TriggerMediumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TriggerMediumDao {

    @Autowired
    private JpaTemplate jpaTemplate;


    public void createTriggerMedium(TriggerMediumEntity triggerMediumEntity) {
        jpaTemplate.save(triggerMediumEntity,TriggerMediumEntity.class);
    }

    public void deleteByTriggerId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<TriggerMediumEntity> findMediumIdByTriggerId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TriggerMediumEntity.class);
    }
}
