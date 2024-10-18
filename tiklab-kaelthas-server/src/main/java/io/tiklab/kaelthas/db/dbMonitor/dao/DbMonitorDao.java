package io.tiklab.kaelthas.db.dbMonitor.dao;


import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.db.dbMonitor.entity.DbMonitorEntity;
import io.tiklab.kaelthas.db.dbMonitor.model.DbMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbMonitorDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<DbMonitorEntity> findDbMonitorPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, DbMonitorEntity.class);
    }

    public String createDbMonitor(DbMonitorEntity dbMonitorEntity) {
        return jpaTemplate.save(dbMonitorEntity, String.class);
    }

    public void deleteDbMonitor(String id) {
        jpaTemplate.delete(DbMonitorEntity.class, id);
    }

    public void updateDbMonitor(DbMonitorEntity dbMonitorEntity) {
        jpaTemplate.update(dbMonitorEntity);
    }

    public List<DbMonitorEntity> findAllMonitor(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, DbMonitorEntity.class);
    }

    public DbMonitor findListById(String monitorId) {
        String sql = """
                SELECT mdm.*,mdi.expression\s
                FROM mtc_db_monitor mdm
                LEFT JOIN mtc_db_item mdi
                ON mdm.db_item_id = mdi.id
                """;
        sql = sql.concat("\nwhere mdm.id = '" + monitorId + "'");
        List<DbMonitor> query = jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbMonitor.class));
        return query.get(0);
    }

    public List<DbMonitorEntity> findMonitorNum(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,DbMonitorEntity.class);
    }

    public void deleteByDbId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
