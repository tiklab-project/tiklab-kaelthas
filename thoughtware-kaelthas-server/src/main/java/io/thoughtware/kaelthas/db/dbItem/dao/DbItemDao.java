package io.thoughtware.kaelthas.db.dbItem.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.db.dbItem.entity.DbItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbItemDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public List<DbItemEntity> findAll() {
        return jpaTemplate.findAll(DbItemEntity.class);
    }

    public DbItemEntity findOne(String id) {
        return jpaTemplate.findOne(DbItemEntity.class,id);
    }

    public List<DbItemEntity> findList(List<Integer> idList) {
        return jpaTemplate.findList(DbItemEntity.class,idList);
    }

    public List<DbItemEntity> findItemListByType(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, DbItemEntity.class);
    }
}
