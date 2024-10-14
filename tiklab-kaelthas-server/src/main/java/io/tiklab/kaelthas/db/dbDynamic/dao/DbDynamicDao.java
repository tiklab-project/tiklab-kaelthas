package io.tiklab.kaelthas.db.dbDynamic.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.db.dbDynamic.entity.DbDynamicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DbDynamicDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<DbDynamicEntity> findDynamicPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, DbDynamicEntity.class);
    }

    public void createDbDynamic(DbDynamicEntity entity) {
        jpaTemplate.save(entity,String.class);
    }
}
