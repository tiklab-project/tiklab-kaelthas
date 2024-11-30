package io.tiklab.kaelthas.kubernetes.monitor.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.kubernetes.monitor.entity.KuMonitorEntity;
import io.tiklab.kaelthas.kubernetes.monitor.model.KuMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KuMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<KuMonitorEntity> findKuMonitorPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, KuMonitorEntity.class);
    }

    public String createKuMonitor(KuMonitorEntity entity) {
        return jpaTemplate.save(entity, String.class);
    }

    public void deleteKuMonitor(String id) {
        jpaTemplate.delete(KuMonitorEntity.class, id);
    }

    public void updateKuMonitor(KuMonitorEntity entity) {
        jpaTemplate.update(entity);
    }

    public List<KuMonitor> findAllKuMonitor(String kuId) {
        String sql = """
                SELECT mkm.*,mk.ip,mk.port,mk.api_token
                FROM mtc_ku_monitor mkm
                LEFT JOIN mtc_ku_info mk
                ON mkm.ku_id = mk.id
                LEFT JOIN mtc_ku_item mki
                ON mkm.ku_item_id = mki.id
                WHERE mki.report_type = 1
                """;

        if (StringUtils.isNotBlank(kuId)) {
            sql = sql.concat(" and mk.id = '" + kuId + "'");
        }

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(KuMonitor.class));
    }

    public List<KuMonitor> findKuAndMonitor() {
        String sql = """
                SELECT mkm.*,mk.ip,mk.port,mk.api_token
                FROM mtc_ku_monitor mkm
                LEFT JOIN mtc_ku_info mk
                ON mkm.ku_id = mk.id
                """;

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(KuMonitor.class));
    }

    public void deleteByKuId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
