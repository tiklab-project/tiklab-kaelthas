package io.tiklab.kaelthas.host.trigger.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.host.trigger.entity.TriggerEntity;
import io.tiklab.kaelthas.host.trigger.model.TriggerQuery;
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

    public void deleteByHostId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }


    /**
     * 条件查询
     */
    public List<TriggerEntity> findTriggerList(TriggerQuery triggerQuery) {
        //将符合条件的触发器全部拉进来,进行判断(当前主机下根据当前监控项创建的触发器)
        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerEntity.class)
                .eq("state", triggerQuery.getState())
                .get();
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
