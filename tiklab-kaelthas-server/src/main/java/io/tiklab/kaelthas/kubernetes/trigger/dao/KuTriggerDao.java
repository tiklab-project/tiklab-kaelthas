package io.tiklab.kaelthas.kubernetes.trigger.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.kubernetes.trigger.entity.KuTriggerEntity;
import io.tiklab.kaelthas.kubernetes.trigger.model.KuTrigger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KuTriggerDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<KuTrigger> findKuTriggerPage(KuTrigger kuTrigger) {
        String sql = """
                SELECT mkt.*,string_agg(mktm.medium_id, ',') AS mediumId
                FROM mtc_ku_trigger mkt
                LEFT JOIN mtc_ku_trigger_medium mktm
                ON mkt.id = mktm.trigger_id
                """;

        if (StringUtils.isNotBlank(kuTrigger.getKuId())) {
            sql = sql.concat(" where mkt.ku_id = '" + kuTrigger.getKuId() + "'");
        }

        if (StringUtils.isNotBlank(kuTrigger.getName())) {
            sql = sql.concat(" and mkt.name like '%" + kuTrigger.getName() + "%'");
        }
        sql = sql.concat("\nGROUP BY mkt.id");


        return jpaTemplate.getJdbcTemplate().findPage(sql, null, kuTrigger.getPageParam(), new BeanPropertyRowMapper<>(KuTrigger.class));
    }

    public String createKuTrigger(KuTriggerEntity entity) {
        return jpaTemplate.save(entity, String.class);
    }

    public void deleteKuTrigger(String id) {
        jpaTemplate.delete(KuTriggerEntity.class, id);
    }

    public void updateKuTrigger(KuTriggerEntity entity) {
        jpaTemplate.update(entity);
    }

    public List<KuTriggerEntity> findKuTriggerByKuId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, KuTriggerEntity.class);
    }

    public void deleteKuTriggerByKuId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<KuTriggerEntity> findKuTriggerByKuIdAndState(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, KuTriggerEntity.class);
    }

    public List<KuTriggerEntity> findAllTrigger() {
        return jpaTemplate.findAll(KuTriggerEntity.class);
    }
}
