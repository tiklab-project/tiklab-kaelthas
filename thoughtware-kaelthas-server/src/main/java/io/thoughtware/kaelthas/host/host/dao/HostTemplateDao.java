package io.thoughtware.kaelthas.host.host.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.host.host.entity.HostTemplateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HostTemplateDao {

    @Autowired
    private JpaTemplate jpaTemplate;


    public List<HostTemplateEntity> findTemplateForHost(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostTemplateEntity.class);
    }

    public String save(HostTemplateEntity hostTemplateEntity) {
        return jpaTemplate.save(hostTemplateEntity, String.class);
    }

    public void deleteByHostId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<HostTemplateEntity> findHostTemplateList(List<String> ids) {
        return jpaTemplate.findList(HostTemplateEntity.class,ids);

    }

    public List<HostTemplateEntity> findHostTemplateAll() {
        return jpaTemplate.findAll(HostTemplateEntity.class);
    }

    public List<HostTemplateEntity> findTemplateByTemplateId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,HostTemplateEntity.class);
    }

    public List<HostTemplateEntity> findHostTemplateListByHostIds(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostTemplateEntity.class);
    }

    public List<HostTemplateEntity> findHostTemplateListByCondition(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,HostTemplateEntity.class);
    }

    public void removeTemplate(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
