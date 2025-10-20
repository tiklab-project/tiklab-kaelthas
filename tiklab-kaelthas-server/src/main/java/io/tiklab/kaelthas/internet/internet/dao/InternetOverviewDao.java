package io.tiklab.kaelthas.internet.internet.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.internet.internet.entity.InternetOverviewEntity;
import io.tiklab.kaelthas.internet.internet.model.InternetOverview;
import io.tiklab.kaelthas.internet.internet.model.InternetOverviewQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InternetOverviewDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public List<InternetOverviewEntity> findLibraryList(InternetOverviewQuery internetOverviewQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InternetOverviewEntity.class)
                .eq("internetId", internetOverviewQuery.getInternetId())
                .eq("type",internetOverviewQuery.getType())
                .get();

        return jpaTemplate.findList(queryCondition,InternetOverviewEntity.class);
    }

    public void updateInternetOverview(InternetOverviewEntity internetOverviewEntity) {
        jpaTemplate.update(internetOverviewEntity);
    }

    public String createInternetOverview(InternetOverviewEntity overviewEntity) {
        return jpaTemplate.save(overviewEntity,String.class);
    }
}
