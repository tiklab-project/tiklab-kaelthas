package io.tiklab.kaelthas.db.dbTrigger.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.db.dbTrigger.entity.DbTriggerEntity;
import io.tiklab.kaelthas.db.dbTrigger.model.DbTrigger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbTriggerDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<DbTrigger> findDbTriggerPage(DbTrigger dbTrigger) {
        String sql = """
                SELECT mdt.*,string_agg(mdtm.medium_id, ',') AS mediumId
                FROM mtc_db_trigger mdt
                LEFT JOIN mtc_db_trigger_medium mdtm
                ON mdt.id = mdtm.trigger_id
                """;
        sql = sql.concat("\nwhere mdt.db_id = '" + dbTrigger.getDbId() + "'");

        if (StringUtils.isNotBlank(dbTrigger.getName())) {
            sql = sql.concat(" and mdt.name like '%" + dbTrigger.getName() + "%'");
        }
        sql = sql.concat("\nGROUP BY mdt.id");

        return jpaTemplate.getJdbcTemplate().findPage(sql,null,dbTrigger.getPageParam(),new BeanPropertyRowMapper<>(DbTrigger.class));
    }

    public String createDbTrigger(DbTriggerEntity entity) {
        return jpaTemplate.save(entity, String.class);
    }

    public void deleteDbTrigger(String id) {
        jpaTemplate.delete(DbTriggerEntity.class,id);
    }

    public void updateDbTrigger(DbTriggerEntity entity) {
        jpaTemplate.update(entity);
    }

    public List<DbTriggerEntity> findListByDbId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, DbTriggerEntity.class);
    }

    public List<DbTriggerEntity> findLikeTrigger(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,DbTriggerEntity.class);
    }

    public List<DbTriggerEntity> findAllTrigger() {
        return jpaTemplate.findAll(DbTriggerEntity.class);
    }

    public void deleteByDbId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
