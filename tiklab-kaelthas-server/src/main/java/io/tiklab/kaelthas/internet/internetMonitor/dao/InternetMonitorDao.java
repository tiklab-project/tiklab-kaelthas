package io.tiklab.kaelthas.internet.internetMonitor.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.internet.internetMonitor.entity.InternetMonitorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InternetMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<InternetMonitorEntity> findMonitorPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, InternetMonitorEntity.class);
    }

    public String createMonitor(InternetMonitorEntity entity) {
        return jpaTemplate.save(entity, String.class);
    }

    public void deleteMonitor(String id) {
        jpaTemplate.delete(InternetMonitorEntity.class,id);
    }

    public void updateMonitor(InternetMonitorEntity entity) {
        jpaTemplate.update(entity);
    }

    public List<InternetMonitorEntity> findMonitorByInId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, InternetMonitorEntity.class);
    }

    public void deleteGraphicsByInId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
