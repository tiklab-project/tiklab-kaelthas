package io.thoughtware.kaelthas.kubernetes.kubernetesTriggerMedium.dao;


import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.kaelthas.kubernetes.kubernetesTriggerMedium.entity.KuTriggerMediumEntity;
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
