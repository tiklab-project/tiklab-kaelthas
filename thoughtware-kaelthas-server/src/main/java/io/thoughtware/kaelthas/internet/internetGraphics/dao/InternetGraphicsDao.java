package io.thoughtware.kaelthas.internet.internetGraphics.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.internet.internetGraphics.entity.InternetGraphicsEntity;
import io.thoughtware.kaelthas.internet.internetGraphics.model.InternetGraphics;
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
}
