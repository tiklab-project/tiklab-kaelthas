package io.thoughtware.kaelthas.db.dbGraphicsMonitor.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.db.dbGraphicsMonitor.entity.DbGraphicsMonitorEntity;
import io.thoughtware.kaelthas.db.dbGraphicsMonitor.model.DbGraphicsMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbGraphicsMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;


    public void deleteByGraphicsId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void createGraphicsMonitor(DbGraphicsMonitorEntity dbGraphicsMonitorEntity) {
        jpaTemplate.save(dbGraphicsMonitorEntity, String.class);
    }

    public List<DbGraphicsMonitorEntity> findMonitorIds(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, DbGraphicsMonitorEntity.class);
    }

    public List<DbGraphicsMonitor> findMonitors(String graphicsId) {
        String sql = """
                SELECT mdg.name as graphicsName,mdgm.*
                FROM mtc_db_graphics mdg
                JOIN mtc_db_graphics_monitor mdgm
                ON mdg.id = mdgm.graphics_id
                """;

        if (StringUtils.isNotBlank(graphicsId)) {
            sql = sql.concat("\nwhere mdg.id = '" + graphicsId + "'");
        }
        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbGraphicsMonitor.class));
    }
}
