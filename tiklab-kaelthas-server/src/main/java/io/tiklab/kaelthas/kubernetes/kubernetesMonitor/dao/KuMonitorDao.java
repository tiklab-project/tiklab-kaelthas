package io.tiklab.kaelthas.kubernetes.kubernetesMonitor.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.entity.KubernetesMonitorEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.model.KubernetesMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KuMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<KubernetesMonitorEntity> findKuMonitorPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, KubernetesMonitorEntity.class);
    }

    public String createKuMonitor(KubernetesMonitorEntity entity) {
        return jpaTemplate.save(entity,String.class);
    }

    public void deleteKuMonitor(String id) {
        jpaTemplate.delete(KubernetesMonitorEntity.class,id);
    }

    public void updateKuMonitor(KubernetesMonitorEntity entity) {
        jpaTemplate.update(entity);
    }

    public List<KubernetesMonitor> findAllKuMonitor() {
        String sql = """
                SELECT mkm.*,mk.ip,mk.port,mk.api_token
                FROM mtc_ku_monitor mkm
                LEFT JOIN mtc_ku_info mk
                ON mkm.ku_id = mk.id
                LEFT JOIN mtc_ku_item mki
                ON mkm.ku_item_id = mki.id
                WHERE mki.report_type = 1
                """;
        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(KubernetesMonitor.class));
    }

    public List<KubernetesMonitor> findKuAndMonitor() {
        String sql = """
                SELECT mkm.*,mk.ip,mk.port,mk.api_token
                FROM mtc_ku_monitor mkm
                LEFT JOIN mtc_ku_info mk
                ON mkm.ku_id = mk.id
                """;

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(KubernetesMonitor.class));
    }

    public void deleteByKuId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
