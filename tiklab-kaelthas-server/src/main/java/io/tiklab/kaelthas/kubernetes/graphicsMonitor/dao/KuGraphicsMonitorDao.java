package io.tiklab.kaelthas.kubernetes.graphicsMonitor.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.kubernetes.graphicsMonitor.entity.KuGraphicsMonitorEntity;
import io.tiklab.kaelthas.kubernetes.graphicsMonitor.model.KuGraphicsMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KuGraphicsMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void save(KuGraphicsMonitorEntity entity) {
        jpaTemplate.save(entity,String.class);
    }

    public void deleteByKuCondition(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<KuGraphicsMonitorEntity> findMonitorIds(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,KuGraphicsMonitorEntity.class);
    }

    public List<KuGraphicsMonitor> findGraphicsMonitors(String graphicsId) {

        String sql = """
                SELECT mt.name as graphicsName,mgm.*
                FROM mtc_ku_graphics mt
                JOIN mtc_ku_graphics_monitor mgm
                ON mt.id = mgm.graphics_id
                """;

        if (StringUtils.isNotBlank(graphicsId)) {
            sql = sql.concat("\nwhere mt.id = '" + graphicsId + "'");
        }

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(KuGraphicsMonitor.class));
    }

    public void deleteByKuGraphicsIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
