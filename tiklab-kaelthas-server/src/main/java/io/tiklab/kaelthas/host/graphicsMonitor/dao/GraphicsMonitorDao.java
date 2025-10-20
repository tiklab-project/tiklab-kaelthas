package io.tiklab.kaelthas.host.graphicsMonitor.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.host.graphicsMonitor.entity.GraphicsMonitorEntity;
import io.tiklab.kaelthas.host.graphicsMonitor.model.GraphicsMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GraphicsMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void insertByGraphics(GraphicsMonitorEntity relation) {
        jpaTemplate.save(relation, GraphicsMonitorEntity.class);
    }

    public void deleteByGraphicsId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void deleteByMonitorIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void deleteByMonitorId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<GraphicsMonitor> findByGraphics(String graphicsId) {

        String sql = """
                SELECT mt.name as graphicsName,mgm.*,hm.name as monitorName
                FROM mtc_graphics mt
                JOIN mtc_graphics_monitor mgm           
                ON mt.id = mgm.graphics_id
                left join  mtc_host_monitor hm
                ON  mgm.monitor_id = hm.id
                """;

        if (StringUtils.isNotBlank(graphicsId)) {
            sql = sql.concat("\nwhere mt.id = '" + graphicsId + "'");
        }

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(GraphicsMonitor.class));
    }

    public void deleteByGraphicsIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
