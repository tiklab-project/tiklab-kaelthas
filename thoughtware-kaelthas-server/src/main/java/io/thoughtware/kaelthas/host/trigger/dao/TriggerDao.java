package io.thoughtware.kaelthas.host.trigger.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.host.trigger.entity.TriggerEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TriggerDao {

    @Resource
    private JpaTemplate jpaTemplate;

    /**
     * 根据主机id查询触发器列表
     */
    public List<TriggerEntity> findTriggerListById(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TriggerEntity.class);

    }


    public Pagination<TriggerEntity> findTrigger(QueryCondition queryCondition) {

        return jpaTemplate.findPage(queryCondition, TriggerEntity.class);
    }

    public String createTrigger(TriggerEntity triggerEntity) {
        return jpaTemplate.save(triggerEntity, String.class);
    }


    public void deleteById(String id) {
        jpaTemplate.delete(TriggerEntity.class, id);
    }

    public void updateTrigger(TriggerEntity triggerEntity) {
        jpaTemplate.update(triggerEntity);
    }


    public void deleteByMonitor(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void deleteByMonitorIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<TriggerEntity> findTriggerByHostIdAndMonitorId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TriggerEntity.class);
    }

    public List<TriggerEntity> findTriggerAll() {
        return jpaTemplate.findAll(TriggerEntity.class);
    }

    public List<TriggerEntity> findLikeTrigger(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,TriggerEntity.class);
    }

    public TriggerEntity findTriggerOne(String triggerId) {
        return jpaTemplate.findOne(TriggerEntity.class,triggerId);
    }

    public List<TriggerEntity> findTriggerList(List<String> ids) {
        return jpaTemplate.findList(TriggerEntity.class,ids);
    }

    public Long findTriggerAllNum() {
        String sql = """
                SELECT count(*) countNum
                FROM mtc_trigger
                """;
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return (Long) map.get("countNum");
    }
}
