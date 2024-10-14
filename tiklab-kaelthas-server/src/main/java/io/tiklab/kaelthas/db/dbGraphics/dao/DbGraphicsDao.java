package io.tiklab.kaelthas.db.dbGraphics.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.db.dbGraphics.entity.DbGraphicsEntity;
import io.tiklab.kaelthas.db.dbGraphics.model.DbGraphics;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbGraphicsDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<DbGraphics> findGraphicsPage(DbGraphics dbGraphics) {
        String sql = """
                SELECT mdg.*,count(mdgm.id) as monitorSum
                FROM mtc_db_graphics mdg
                LEFT JOIN mtc_db_graphics_monitor mdgm
                ON mdg.id = mdgm.graphics_id
                where 1=1
                """;

        if (StringUtils.isNotBlank(dbGraphics.getDbId())) {
            sql = sql.concat(" and db_id = '" + dbGraphics.getDbId() + "'");
        }

        if (StringUtils.isNotBlank(dbGraphics.getName())) {
            sql = sql.concat(" and name like '%" + dbGraphics.getName()+"%'");
        }

        sql = sql.concat("\nGROUP BY mdg.id");
        return jpaTemplate.getJdbcTemplate().findPage(sql, null, dbGraphics.getPageParam(), new BeanPropertyRowMapper<>(DbGraphics.class));
    }

    public String createGraphics(DbGraphicsEntity graphicsEntity) {
        return jpaTemplate.save(graphicsEntity,String.class);
    }

    public void deleteGraphics(String id) {
        jpaTemplate.delete(DbGraphicsEntity.class,id);
    }

    public List<DbGraphicsEntity> findDbGraphicsList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, DbGraphicsEntity.class);
    }

    public void updateGraphics(DbGraphicsEntity entity) {
        jpaTemplate.update(entity);
    }
}
