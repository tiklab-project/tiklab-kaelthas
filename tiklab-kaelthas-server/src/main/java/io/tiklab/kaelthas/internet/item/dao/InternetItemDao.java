package io.tiklab.kaelthas.internet.item.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.internet.item.entity.InternetItemEntity;
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
