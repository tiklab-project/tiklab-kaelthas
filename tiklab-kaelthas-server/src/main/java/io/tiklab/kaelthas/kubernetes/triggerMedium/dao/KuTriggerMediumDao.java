package io.tiklab.kaelthas.kubernetes.triggerMedium.dao;


import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.kaelthas.kubernetes.triggerMedium.entity.KuTriggerMediumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KuTriggerMediumDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void deleteByTriggerIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void createKuTriggerMedium(KuTriggerMediumEntity entity) {
        jpaTemplate.save(entity,String.class);
    }

    public void deleteByTriggerId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
