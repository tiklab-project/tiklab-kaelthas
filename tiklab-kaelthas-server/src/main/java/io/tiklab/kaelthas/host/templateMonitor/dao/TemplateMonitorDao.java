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

    /**
     * 根据模板id查询监控项id
     */
    public List<TemplateMonitorEntity> findIdsByTemplateId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TemplateMonitorEntity.class);
    }

    public List<TemplateMonitorEntity> findList(List<String> strings) {
        return jpaTemplate.findList(TemplateMonitorEntity.class, strings);
    }

}