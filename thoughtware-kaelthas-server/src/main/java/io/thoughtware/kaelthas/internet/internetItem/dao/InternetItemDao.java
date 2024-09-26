package io.thoughtware.kaelthas.internet.internetItem.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.internet.internetItem.entity.InternetItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InternetItemDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public List<InternetItemEntity> findItemList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, InternetItemEntity.class);
    }
}
