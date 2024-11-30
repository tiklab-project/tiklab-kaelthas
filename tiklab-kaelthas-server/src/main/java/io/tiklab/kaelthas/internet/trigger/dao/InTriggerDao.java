package io.tiklab.kaelthas.internet.trigger.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.internet.trigger.entity.InTriggerEntity;
import io.tiklab.kaelthas.internet.trigger.model.InTrigger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InTriggerDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<InTrigger> findTriggerPage(InTrigger inTrigger) {
        String sql = """
                SELECT mit.*,string_agg(mitm.medium_id, ',') AS mediumId
                FROM mtc_internet_trigger mit
                LEFT JOIN mtc_internet_trigger_medium mitm
                ON mit.id = mitm.trigger_id
                """;

        if (StringUtils.isNotBlank(inTrigger.getInternetId())) {
            sql = sql.concat(" where mit.internet_id = '" + inTrigger.getInternetId() + "'");
        }

        if (StringUtils.isNotBlank(inTrigger.getName())) {
            sql = sql.concat(" and mit.name like '%" + inTrigger.getName() + "%'");
        }

        sql = sql.concat("\nGROUP BY mit.id");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, inTrigger.getPageParam(), new BeanPropertyRowMapper<>(InTrigger.class));
    }

    public String createInTrigger(InTriggerEntity entity) {
        return jpaTemplate.save(entity,String.class);
    }

    public void deleteTrigger(String id) {
        jpaTemplate.delete(InTriggerEntity.class,id);
    }

    public void updateTrigger(InTriggerEntity entity) {
        jpaTemplate.update(entity);
    }

    public void deleteByInId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<InTriggerEntity> findTriggerByInId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, InTriggerEntity.class);
    }

    public List<InTriggerEntity> findTriggerAll() {
        return jpaTemplate.findAll(InTriggerEntity.class);
    }

    public List<InTriggerEntity> findLikeTrigger(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,InTriggerEntity.class);
    }
}
