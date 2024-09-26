package io.thoughtware.kaelthas.internet.internet.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.internet.internet.entity.InternetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InternetDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<InternetEntity> findInternetPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, InternetEntity.class);
    }

    public String createInternet(InternetEntity internetEntity) {
        return jpaTemplate.save(internetEntity,String.class);
    }

    public void deleteInternet(String id) {
        jpaTemplate.delete(InternetEntity.class,id);
    }

    public void updateInternet(InternetEntity internetEntity) {
        jpaTemplate.update(internetEntity);
    }

    public InternetEntity findInternetById(String id) {
        return jpaTemplate.findOne(InternetEntity.class,id);
    }
}
