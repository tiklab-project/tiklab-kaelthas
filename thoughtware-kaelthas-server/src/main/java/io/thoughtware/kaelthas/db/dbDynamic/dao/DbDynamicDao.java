package io.thoughtware.kaelthas.db.dbDynamic.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.db.dbDynamic.entity.DbDynamicEntity;
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
