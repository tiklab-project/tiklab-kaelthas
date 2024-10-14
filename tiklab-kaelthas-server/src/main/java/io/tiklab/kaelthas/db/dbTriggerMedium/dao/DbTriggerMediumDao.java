package io.tiklab.kaelthas.db.dbTriggerMedium.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.kaelthas.db.dbTriggerMedium.entity.DbTriggerMediumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DbTriggerMediumDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void createTriggerMedium(DbTriggerMediumEntity entity) {
        jpaTemplate.save(entity,String.class);
    }

    public void deleteByTriggerId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
