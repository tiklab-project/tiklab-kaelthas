package io.tiklab.kaelthas.kubernetes.item.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.kubernetes.item.entity.KubernetesItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KubernetesItemDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public List<KubernetesItemEntity> findAll() {
        return jpaTemplate.findAll(KubernetesItemEntity.class);
    }

    public List<KubernetesItemEntity> findList(List<String> ids) {
        return jpaTemplate.findList(KubernetesItemEntity.class,ids);
    }

    public KubernetesItemEntity findOne(String id) {
        return jpaTemplate.findOne(KubernetesItemEntity.class,id);
    }

    public List<KubernetesItemEntity> findItemList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, KubernetesItemEntity.class);
    }
}
