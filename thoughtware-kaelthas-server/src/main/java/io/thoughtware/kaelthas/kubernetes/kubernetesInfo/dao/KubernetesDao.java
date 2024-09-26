package io.thoughtware.kaelthas.kubernetes.kubernetesInfo.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.kubernetes.kubernetesInfo.entity.KubernetesEntity;
import io.thoughtware.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KubernetesDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<Kubernetes> findKbInfoPage(Kubernetes kubernetes) {
        String sql = """
                SELECT mki.*,COUNT(ma.id) as alarmNum,MAX(ma.send_message) AS message
                FROM mtc_ku_info mki
                LEFT JOIN mtc_alarm ma
                ON mki.id = ma.host_id AND ma.status = 2
                where 1=1
                """;

        if (kubernetes.getUsability() != null) {
            sql = sql.concat(" and usability = " + kubernetes.getUsability());
        }

        if (StringUtils.isNotBlank(kubernetes.getName())) {
            sql = sql.concat(" and mki.name like '%" + kubernetes.getName() + "%'");
        }

        sql = sql.concat("\n GROUP BY mki.id");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, kubernetes.getPageParam(), new BeanPropertyRowMapper<>(Kubernetes.class));
    }

    public String createKbInfo(KubernetesEntity kubernetesEntity) {
        return jpaTemplate.save(kubernetesEntity, String.class);
    }

    public void updateKbInfo(KubernetesEntity kubernetesEntity) {
        jpaTemplate.update(kubernetesEntity);
    }

    public void deleteKuInfo(String id) {
        jpaTemplate.delete(KubernetesEntity.class, id);
    }

    public Pagination<KubernetesEntity> findKuDropDown(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, KubernetesEntity.class);
    }

    public KubernetesEntity findKuInfoById(String id) {
        return jpaTemplate.findOne(KubernetesEntity.class, id);
    }

    public List<KubernetesEntity> findAll() {
        return jpaTemplate.findAll(KubernetesEntity.class);
    }
}
