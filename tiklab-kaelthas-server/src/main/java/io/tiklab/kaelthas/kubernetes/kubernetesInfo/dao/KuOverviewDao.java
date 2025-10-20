package io.tiklab.kaelthas.kubernetes.kubernetesInfo.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.entity.KuOverviewEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.KuOverviewQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KuOverviewDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public void execKuOverviewSql(String historySql) {
        jpaTemplate.getJdbcTemplate().execute(historySql);
    }
    public List<KuOverviewEntity> findKuOverviewList(KuOverviewQuery kuOverviewQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(KuOverviewEntity.class)
                .eq("kuId", kuOverviewQuery.getKuId())
                .eq("type",kuOverviewQuery.getType())
                .get();

        return jpaTemplate.findList(queryCondition,KuOverviewEntity.class);
    }

    public void updateKuOverview(KuOverviewEntity kuOverviewEntity) {
        jpaTemplate.update(kuOverviewEntity);
    }

    public String createKuOverview(KuOverviewEntity overviewEntity) {
        return jpaTemplate.save(overviewEntity,String.class);
    }
}
