package io.thoughtware.kaelthas.host.host.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.host.host.entity.HostEntity;
import io.thoughtware.kaelthas.host.host.model.Host;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class HostDao {

    @Resource
    private JpaTemplate jpaTemplate;

    /**
     * 根据条件分页查询
     */
    public Pagination<Host> findPageHost(Host host) {

        String sql = """
                SELECT mtc_host.id, name, ip, usability, MAX(create_time) AS create_time,
                       COUNT(mtc_alarm.id) AS alarmNum, MAX(mtc_alarm.send_message) AS message
                FROM mtc_host
                LEFT JOIN mtc_alarm
                ON mtc_host.id = mtc_alarm.host_id
                AND mtc_alarm.status = 2
                """;

        if (Objects.nonNull(host.getUsability())) {
            sql = sql.concat("  where mtc_host.usability =" + host.getUsability());

            if (StringUtils.isNotBlank(host.getName())) {
                sql = sql.concat(" and mtc_host.name like'%" + host.getName() + "%'");
            }
        } else {
            if (StringUtils.isNotBlank(host.getName())) {
                sql = sql.concat("  where mtc_host.name like'%" + host.getName() + "%'");
            }
        }

        sql = sql.concat("  GROUP BY mtc_host.id, name, ip, usability\n" +
                "ORDER BY create_time DESC");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, host.getPageParam(), new BeanPropertyRowMapper<>(Host.class));
    }

    /**
     * 添加主机
     */
    public String createHost(HostEntity hostEntity) {

        try {
            //插入主机表当中
            return jpaTemplate.save(hostEntity, String.class);

        } catch (DataAccessException e) {
            throw new RuntimeException("添加主机失败");
        }
    }

    public void updateHost(HostEntity hostEntity) {
        try {
            jpaTemplate.update(hostEntity);
        } catch (DataAccessException e) {
            throw new RuntimeException("主机信息更新失败");
        }
    }

    /**
     * 根据id进行查询
     */
    public HostEntity findHostById(String id) {
        return jpaTemplate.findOne(HostEntity.class, id);
    }

    /**
     * 根据id删除主机
     */
    public void deleteHostById(String id) {
        jpaTemplate.delete(HostEntity.class, id);
    }

    public List<HostEntity> findHostByIp(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostEntity.class);
    }

    public Pagination<Host> findHostPageForMonitoring(Host host) {
        String sql = """
                SELECT mtc_host.id, mtc_host.name, mtc_host.ip, mtc_host.usability, MAX(create_time) AS create_time,
                       COUNT(mtc_alarm.id) AS alarmNum, MAX(mtc_alarm.send_message) AS message
                FROM mtc_host
                LEFT JOIN mtc_alarm
                ON mtc_host.id = mtc_alarm.host_id
                AND mtc_alarm.status = 2
                WHERE 1=1
                """;

        if (Objects.nonNull(host.getUsability())) {
            sql = sql.concat("  and mtc_host.usability =" + host.getUsability());

        }

        if (StringUtils.isNotBlank(host.getName())) {
            sql = sql.concat(" and mtc_host.name like'%" + host.getName() + "%'");
        }

        sql = sql.concat("  GROUP BY mtc_host.id, mtc_host.name, mtc_host.ip, mtc_host.usability\n" +
                "ORDER BY create_time DESC");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, host.getPageParam(), new BeanPropertyRowMapper<>(Host.class));
    }

    public Pagination<HostEntity> findRecentHostList(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, HostEntity.class);
    }

    public List<HostEntity> findList(List<String> ids) {
        return jpaTemplate.findList(HostEntity.class, ids);
    }

    public Integer findHostNumber() {
        return jpaTemplate.findAll(HostEntity.class).size();
    }

    public HostEntity findOne(String id) {
        return jpaTemplate.findOne(HostEntity.class, id);
    }

    public List<HostEntity> findAll() {
        return jpaTemplate.findAll(HostEntity.class);
    }

    public void updateHostStatus(String hostId, Integer status) {
        String sql = "update mtc_host set usability = " + status + " where id = '" + hostId + "'";
        jpaTemplate.getJdbcTemplate().execute(sql);
    }

    public Long findHostCount() {
        String sql = """
                SELECT "count"(*) as number
                FROM mtc_host
                """;
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return (Long) map.get("number");
    }

    public Long findHostAbnormal() {
        String sql = """
                SELECT count(*) number
                FROM
                (SELECT DISTINCT(mh.id)
                FROM mtc_host mh
                JOIN mtc_alarm ma
                ON mh.id = ma.host_id
                WHERE usability = 1 and ma.status = 2
                UNION
                SELECT DISTINCT(id)
                FROM mtc_host
                WHERE usability = 2) a
                """;
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());

        return (Long) map.get("number");
    }
}
