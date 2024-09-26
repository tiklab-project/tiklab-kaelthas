package io.thoughtware.kaelthas.host.hostDynamic.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.host.hostDynamic.entity.HostDynamicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HostDynamicDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public Pagination<HostDynamicEntity> findPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, HostDynamicEntity.class);
    }

    public void createHostDynamic(HostDynamicEntity hostDynamicEntity) {
        jpaTemplate.save(hostDynamicEntity, HostDynamicEntity.class);
    }
}
