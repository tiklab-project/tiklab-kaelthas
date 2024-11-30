package io.tiklab.kaelthas.internet.graphicsMonitor.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.internet.graphicsMonitor.entity.InGraphicsMonitorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InGraphicsMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void createGraphicsMonitorList(String sql) {
        jpaTemplate.getJdbcTemplate().execute(sql);
    }

    public void deleteByGraphics(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<InGraphicsMonitorEntity> findMonitorIds(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, InGraphicsMonitorEntity.class);
    }

    public void deleteByGraphicsIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
