package io.tiklab.kaelthas.history.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.history.model.History;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class HistoryMultiDao {

    @Autowired
    private JpaTemplate jpaTemplate;


    public void insertOneForList(String historySql1) {
        jpaTemplate.getJdbcTemplate().execute(historySql1);
    }

    public void insertFiveForList(String historySql5) {
        jpaTemplate.getJdbcTemplate().execute(historySql5);
    }

    public void insertFifteenForList(String historySql15) {
        jpaTemplate.getJdbcTemplate().execute(historySql15);
    }

    public List<History> findHistoryByOneTime(History history) {

        String sql = """
                select mhm.name monitorName,mhm.expression,mh.*
                from mtc_host_monitor mhm
                left join mtc_history_one_minute mh
                ON mh.monitor_id = mhm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mhm.host_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mhm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findHistoryByFiveTime(History history) {
        String sql = """
                select mhm.name monitorName,mhm.expression,mh.*
                from mtc_host_monitor mhm
                left join mtc_history_five_minute mh
                ON mh.monitor_id = mhm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mhm.host_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mhm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findHistoryByFifteenTime(History history) {
        String sql = """
                SELECT mhm.name AS monitorName, mhm.expression, mh.*
                FROM mtc_host_monitor mhm
                LEFT JOIN mtc_history_fifteen_minute mh
                ON mh.monitor_id = mhm.id
                """;
        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mhm.host_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mhm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findDbHistoryByOneTime(History history) {
        String sql = """
                select mdm.name monitorName,mdi.expression,mh.*
                from mtc_db_monitor mdm
                JOIN mtc_db_item mdi
                ON mdm.db_item_id = mdi.id
                LEFT JOIN mtc_history_one_minute mh
                ON mh.monitor_id = mdm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mdm.db_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mdm.id = '" + history.getMonitorId() + "'");
        }


        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findDbHistoryByFiveTime(History history) {
        String sql = """
                select mdm.name monitorName,mdi.expression,mh.*
                from mtc_db_monitor mdm
                JOIN mtc_db_item mdi
                ON mdm.db_item_id = mdi.id
                LEFT JOIN mtc_history_five_minute mh
                ON mh.monitor_id = mdm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mdm.db_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mdm.id = '" + history.getMonitorId() + "'");
        }


        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findDbHistoryByFifteenTime(History history) {
        String sql = """
                select mdm.name monitorName,mdi.expression,mh.*
                from mtc_db_monitor mdm
                JOIN mtc_db_item mdi
                ON mdm.db_item_id = mdi.id
                LEFT JOIN mtc_history_fifteen_minute mh
                ON mh.monitor_id = mdm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mdm.db_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mdm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findKuHistoryByOneTime(History history) {
        String sql = """
                select mkm.name monitorName,mki.expression,mh.*
                from mtc_ku_monitor mkm
                JOIN mtc_ku_item mki
                ON mkm.ku_item_id = mki.id
                LEFT JOIN mtc_history_one_minute mh
                ON mh.monitor_id = mkm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mkm.ku_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mkm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findKuHistoryByFiveTime(History history) {
        String sql = """
                select mkm.name monitorName,mki.expression,mh.*
                from mtc_ku_monitor mkm
                JOIN mtc_ku_item mki
                ON mkm.ku_item_id = mki.id
                LEFT JOIN mtc_history_five_minute mh
                ON mh.monitor_id = mkm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mkm.ku_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mkm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }


    public List<History> findKuHistoryByFifteenTime(History history) {
        String sql = """
                select mkm.name monitorName,mki.expression,mh.*
                from mtc_ku_monitor mkm
                JOIN mtc_ku_item mki
                ON mkm.ku_item_id = mki.id
                LEFT JOIN mtc_history_fifteen_minute mh
                ON mh.monitor_id = mkm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mkm.ku_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mkm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findInHistoryByFifteenTime(History history) {
        String sql = """
                select mkm.name monitorName,mki.expression,mh.*
                from mtc_internet_monitor mkm
                JOIN mtc_internet_item mki
                ON mkm.internet_item_id = mki.id
                LEFT JOIN mtc_history_fifteen_minute mh
                ON mh.monitor_id = mkm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mkm.internet_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mkm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findInHistoryByFiveTime(History history) {
        String sql = """
                select mkm.name monitorName,mki.expression,mh.*
                from mtc_internet_monitor mkm
                JOIN mtc_internet_item mki
                ON mkm.internet_item_id = mki.id
                LEFT JOIN mtc_history_five_minute mh
                ON mh.monitor_id = mkm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mkm.internet_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mkm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findInHistoryByOneTime(History history) {
        String sql = """
                select mkm.name monitorName,mki.expression,mh.*
                from mtc_internet_monitor mkm
                JOIN mtc_internet_item mki
                ON mkm.internet_item_id = mki.id
                LEFT JOIN mtc_history_one_minute mh
                ON mh.monitor_id = mkm.id
                """;

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where mkm.internet_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and mkm.id = '" + history.getMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }
}
