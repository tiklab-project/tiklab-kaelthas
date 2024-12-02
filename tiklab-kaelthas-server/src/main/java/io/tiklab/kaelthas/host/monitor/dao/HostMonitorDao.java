package io.tiklab.kaelthas.host.monitor.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.host.monitor.entity.HostMonitorEntity;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.monitorItem.entity.MonitorItemEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HostMonitorDao {

    @Resource
    private JpaTemplate jpaTemplate;

    /**
     * 根据条件查询监控项信息
     */
    public List<HostMonitorEntity> findMonitorCondition(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostMonitorEntity.class);
    }

    public String createMonitor(HostMonitorEntity monitorEntity) {
        try {

            return jpaTemplate.save(monitorEntity, String.class);

        } catch (DataAccessException e) {
            throw new RuntimeException("监控项添加失败");
        }
    }

    public void deleteMonitorById(String id) {
        jpaTemplate.delete(HostMonitorEntity.class, id);
    }

    public void updateMonitor(HostMonitorEntity monitorEntity) {
        jpaTemplate.update(monitorEntity);
    }


    public HostMonitorEntity findOneMonitor(String monitorId) {

        return jpaTemplate.findOne(HostMonitorEntity.class, monitorId);

    }

    public List<HostMonitorEntity> findList(List<String> idList) {
        return jpaTemplate.findList(HostMonitorEntity.class, idList);
    }

    public List<HostMonitorEntity> findMonitorByHostId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostMonitorEntity.class);
    }

    public Pagination<HostMonitorEntity> findListByQuery(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, HostMonitorEntity.class);
    }

    public List<MonitorItemEntity> findMonitorItemAll() {
        return jpaTemplate.findAll(MonitorItemEntity.class);
    }

    public void deleteByHostId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<HostMonitorEntity> findHostMonitorListByHostIds(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostMonitorEntity.class);
    }

    public Pagination<HostMonitorEntity> findMonitorPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, HostMonitorEntity.class);
    }

    public List<HostMonitorEntity> findMonitorList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostMonitorEntity.class);
    }

    public List<HostMonitorEntity> findIdsByTemplateId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostMonitorEntity.class);
    }

    public void deleteCondition(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void updateByMonitorId(HostMonitor hostMonitor) {
        String sql = """
                update mtc_host_monitor
                set
                """;

        sql = sql.concat(" name = '" + hostMonitor.getName() + "',");

        if (StringUtils.isNotBlank(hostMonitor.getMonitorItemId())) {
            sql = sql.concat(" monitor_item_id = '" + hostMonitor.getMonitorItemId() + "',");
        }

        sql = sql.concat(" interval_time = '" + hostMonitor.getIntervalTime() + "',");
        sql = sql.concat(" data_retention_time = '" + hostMonitor.getDataRetentionTime() + "',");
        sql = sql.concat(" monitor_status = " + hostMonitor.getMonitorStatus() + ",");
        sql = sql.concat(" expression = '" + hostMonitor.getExpression() + "'");

        sql = sql.concat(" where template_id = '"+hostMonitor.getId()+"'");

        jpaTemplate.getJdbcTemplate().execute(sql);
    }

    public void updateMonitorByTemplate(HostMonitorEntity monitorEntity) {
        jpaTemplate.update(monitorEntity);
    }

    public void deleteMonitorByItemIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<HostMonitorEntity> findCondition(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,HostMonitorEntity.class);
    }

    public Long findMonitorAllNum() {
        String sql = """
                SELECT count(*) countNum
                FROM mtc_host_monitor
                """;

        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return (Long) map.get("countNum");
    }
}
