package io.tiklab.kaelthas.host.graphics.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.host.graphics.entity.GraphicsEntity;
import io.tiklab.kaelthas.host.graphics.model.Graphics;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GraphicsDao {

    @Resource
    private JpaTemplate jpaTemplate;


    public Pagination<Graphics> findGraphics(Graphics graphics) {
        String sql = """
                SELECT mg.*,count(mgm.id) as monitorSum
                FROM mtc_graphics mg
                LEFT JOIN mtc_graphics_monitor mgm
                ON mg.id = mgm.graphics_id
                where 1=1
                """;

        if (StringUtils.isNotBlank(graphics.getHostId())) {
            sql = sql.concat(" and mg.host_id = '" + graphics.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(graphics.getName())) {
            sql = sql.concat(" and mg.name like '%" + graphics.getName()+"%'");
        }

        sql = sql.concat("\nGROUP BY mg.id");
        return jpaTemplate.getJdbcTemplate().findPage(sql, null, graphics.getPageParam(), new BeanPropertyRowMapper<>(Graphics.class));
    }

    public String createGraphics(GraphicsEntity graphicsEntity) {
        return jpaTemplate.save(graphicsEntity, String.class);
    }

    public void deleteGraphics(String id) {
        jpaTemplate.delete(GraphicsEntity.class, id);
    }

    public void updateGraphics(GraphicsEntity graphicsEntity) {
        jpaTemplate.update(graphicsEntity);
    }

    //根据监控项id删除图表
    public void deleteGraphicsByMonitorId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void deleteGraphicsByHostId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<GraphicsEntity> findGraphicsList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, GraphicsEntity.class);
    }

    public void deleteGraphicsByMonitorIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<GraphicsEntity> findGraphicsByHostId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,GraphicsEntity.class);
    }

    public List<GraphicsEntity> findGraphicsByHisInformation(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,GraphicsEntity.class);
    }

    public Long findGraphicsAllNum() {

        String sql = """
                SELECT count(*) countNum
                FROM mtc_graphics
                """;
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return (Long) map.get("countNum");
    }
}
