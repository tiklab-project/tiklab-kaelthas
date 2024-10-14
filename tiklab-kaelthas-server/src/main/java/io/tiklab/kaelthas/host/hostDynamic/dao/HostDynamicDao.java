package io.tiklab.kaelthas.host.hostDynamic.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.host.hostDynamic.entity.HostDynamicEntity;
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
