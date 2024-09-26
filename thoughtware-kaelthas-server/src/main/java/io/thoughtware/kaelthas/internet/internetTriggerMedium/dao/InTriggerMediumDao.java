package io.thoughtware.kaelthas.internet.internetTriggerMedium.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InTriggerMediumDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void createTriggerMedium(String sql) {
        jpaTemplate.getJdbcTemplate().execute(sql);
    }

    public void deleteByTrigger(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void deleteByTriggerIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
