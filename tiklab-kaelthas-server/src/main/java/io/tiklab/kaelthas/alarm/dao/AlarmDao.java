package io.tiklab.kaelthas.alarm.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.alarm.entity.AlarmEntity;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.model.AlarmQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AlarmDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    /**
     * 查找
     * @param id
     */
    public AlarmEntity findAlarm(String id){
        return jpaTemplate.findOne(AlarmEntity.class,id);
    }


    /**
     * 条件查询
     * @param alarmQuery
     * @return List <RepositoryEntity>
     */
    public List<AlarmEntity> findAlarmList(AlarmQuery alarmQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(AlarmEntity.class)
                .eq("hostId", alarmQuery.getHostId())
                .eq("machineType", alarmQuery.getMachineType())
                .orders(alarmQuery.getOrderParams())
                .get();
        return  jpaTemplate.findList(queryCondition,AlarmEntity.class);
    }

    public Pagination<AlarmEntity> findAlarmPage(Alarm alarm) {
        QueryCondition queryCondition = QueryBuilders.createQuery(AlarmEntity.class)
                .like("name",alarm.getName())
                .like("ip",alarm.getIp())
                .eq("status",alarm.getStatus())
                .eq("hostId",alarm.getHostId())
                .eq("severityLevel",alarm.getSeverityLevel())
                .eq("machineType",alarm.getMachineType())
                .pagination(alarm.getPageParam())
                .get();

       return jpaTemplate.findPage(queryCondition,AlarmEntity.class);
      }

    public String createAlarm(AlarmEntity alarmEntity) {
        return jpaTemplate.save(alarmEntity, String.class);
    }

    public List<AlarmEntity> findListByHisId(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, AlarmEntity.class);
    }

    public void updateAlarm(AlarmEntity alarmEntity1) {
        jpaTemplate.update(alarmEntity1);
    }

    public void deleteByTriggerId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<AlarmEntity> findAlarmList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, AlarmEntity.class);
    }

    public void updateAlarmState(Alarm alarm) {
        String sql = " update mtc_alarm set status = "
                + alarm.getStatus()
                + " , resolution_time = '" + alarm.getResolutionTime() + "'"
                + " , duration = '" + alarm.getDuration() + "'"
                + " where id = '" + alarm.getId() + "'";
        jpaTemplate.getJdbcTemplate().execute(sql);
    }

    public List<Alarm> findAlarmTypeNum(String hostId) {
        String sql = """
                SELECT mt.severity_level,count(ma.id) alarmNum
                FROM mtc_alarm ma
                JOIN mtc_trigger mt
                ON ma.trigger_id = mt.id
                WHERE ma.status = 2
                """;

        if (StringUtils.isNotBlank(hostId)) {
            sql = sql.concat(" and host_id = '" + hostId + "'");
        }

        sql = sql.concat(" GROUP BY mt.severity_level");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Alarm.class));
    }

    public Long findAlarmAllNum(String beforeTenTime) {
        String sql = """
                SELECT count(*) countNum
                FROM mtc_alarm
                """;

        sql = sql.concat(" where alert_time >'" + beforeTenTime + "'");

        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());

        return (Long) map.get("countNum");
    }

    public Long findAlarmTimeNum(String beforeTenTime) {
        String sql = """
                SELECT count(*) countNum
                FROM mtc_alarm
                WHERE status = 2
                """;

        sql = sql.concat(" and alert_time > '" + beforeTenTime + "'");
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return (Long) map.get("countNum");
    }

    public List<Map<String, Object>> findTypeDistribution(String beforeTime) {
        String sql = """
                SELECT mh.ip,count(ma.id),sum(case when ma.status = 1 then 1 else 0 end) settleSum,sum(case when ma.status = 2 then 1 else 0 end) nosettleSum
                FROM mtc_host mh
                LEFT JOIN mtc_alarm ma
                ON mh.id = ma.host_id
                """;
        sql = sql.concat(" WHERE ma.alert_time > '" + beforeTime + "'\n" +
                "                GROUP BY mh.ip\n" +
                "                ORDER BY count(ma.id) desc\n" +
                "                LIMIT 10");
        List<Map<String, Object>> maps = jpaTemplate.getJdbcTemplate().queryForList(sql);

        return maps;
    }

    public List<AlarmEntity> findAlarmNumByCondition(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,AlarmEntity.class);
    }
}
