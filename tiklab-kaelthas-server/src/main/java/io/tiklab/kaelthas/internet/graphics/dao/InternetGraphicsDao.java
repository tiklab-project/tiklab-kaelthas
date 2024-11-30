package io.tiklab.kaelthas.internet.graphics.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.internet.graphics.entity.InternetGraphicsEntity;
import io.tiklab.kaelthas.internet.graphics.model.InternetGraphics;
import io.tiklab.kaelthas.internet.graphicsMonitor.model.InGraphicsMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InternetGraphicsDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<InternetGraphics> findGraphicsPage(InternetGraphics internetGraphics) {
        String sql = """
                SELECT mig.*,count(migm.id) monitorNum
                FROM mtc_internet_graphics mig
                LEFT JOIN mtc_internet_graphics_monitor migm
                ON mig.id = migm.graphics_id
                """;

        if (StringUtils.isNotBlank(internetGraphics.getInternetId())) {
            sql = sql.concat(" where mig.internet_id = '" + internetGraphics.getInternetId() + "'");
        }

        if (StringUtils.isNotBlank(internetGraphics.getName())) {
            sql = sql.concat(" and mig.name like '" + internetGraphics.getName() + "'");
        }

        sql = sql.concat("  GROUP BY mig.id");

        return jpaTemplate.getJdbcTemplate().findPage(sql,null,internetGraphics.getParamPage(),new BeanPropertyRowMapper<>(InternetGraphics.class));
    }

    public String createGraphics(InternetGraphicsEntity graphics) {
        return jpaTemplate.save(graphics, String.class);
    }

    public void deleteGraphics(String id) {
        jpaTemplate.delete(InternetGraphicsEntity.class, id);
    }

    public void updateGraphics(InternetGraphicsEntity graphics) {
        jpaTemplate.update(graphics);
    }

    public List<InternetGraphicsEntity> findGraphicsList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, InternetGraphicsEntity.class);
    }

    public void deleteGraphicsByInId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<InGraphicsMonitor> findGraphicsMonitors(String id) {
        String sql = """
                SELECT mt.name as graphicsName,mgm.*
                FROM mtc_internet_graphics mt
                JOIN mtc_internet_graphics_monitor mgm
                ON mt.id = mgm.graphics_id
                """;

        if (StringUtils.isNotBlank(id)) {
            sql = sql.concat("\nwhere mt.id = '" + id + "'");
        }

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(InGraphicsMonitor.class));
    }
}
