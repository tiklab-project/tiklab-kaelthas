package io.tiklab.kaelthas.host.templateMonitor.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.host.templateMonitor.entity.TemplateMonitorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 环境 数据访问
 */

@Repository
public class TemplateMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    //根据模板id集合查询监控项id集合
    public List<TemplateMonitorEntity> findTemplateByIds(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TemplateMonitorEntity.class);
    }

    /**
     * 根据模板id查询监控项id
     */
    public List<TemplateMonitorEntity> findIdsByTemplateId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TemplateMonitorEntity.class);
    }

    public String createTemplateMonitor(TemplateMonitorEntity templateMonitorEntity) {
        return jpaTemplate.save(templateMonitorEntity, String.class);
    }

    public void deleteByTemplateId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void deleteByMonitorId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<TemplateMonitorEntity> findList(List<String> strings) {
        return jpaTemplate.findList(TemplateMonitorEntity.class, strings);
    }

    public void deleteTemplateMonitor(String id) {
        jpaTemplate.delete(TemplateMonitorEntity.class, id);
    }


    public List<TemplateMonitorEntity> findTemplateMonitorByTemplateId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TemplateMonitorEntity.class);
    }

    public TemplateMonitorEntity findOneMonitor(String id) {
        return jpaTemplate.findOne(TemplateMonitorEntity.class, id);
    }

    public void deleteMonitorByIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<TemplateMonitorEntity> findMonitorByItemIds(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TemplateMonitorEntity.class);
    }

    public Integer findMonitorNumber() {
        return jpaTemplate.findAll(TemplateMonitorEntity.class).size();
    }
}