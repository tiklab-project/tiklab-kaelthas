package io.tiklab.kaelthas.db.customize.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.db.customize.entity.CustomizeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomizeDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<CustomizeEntity> findCustomizePage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, CustomizeEntity.class);
    }

    public String createCustomize(CustomizeEntity customizeEntity) {
        return jpaTemplate.save(customizeEntity,String.class);
    }

    public void updateCustomize(CustomizeEntity customizeEntity) {
        jpaTemplate.update(customizeEntity);
    }

    public void deleteCustomize(String id) {
        jpaTemplate.delete(CustomizeEntity.class,id);
    }
}
