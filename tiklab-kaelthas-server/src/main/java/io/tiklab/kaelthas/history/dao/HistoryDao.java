package io.tiklab.kaelthas.history.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.kaelthas.history.model.History;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HistoryDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<History> findInformationPage(History history) {

        String sql = "SELECT hi.*  FROM mtc_history_fifteen_minute hi where hi.host_id = '" + history.getHostId() + "'";

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and hi.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and hi.gather_time <= '" + history.getEndTime() + "'");
        }

        sql = sql.concat("\n order by hi.gather_time desc");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, history.getPageParam(), new BeanPropertyRowMapper<>(History.class));
    }


    public List<History> findInformationByLine(History history) {

        String sql = "select * from mtc_history_fifteen_minute \n";

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where host_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and monitor_id = '" + history.getMonitorId() + "'");
        }

        if (!Objects.isNull(history.getSource())) {
            sql = sql.concat(" and source = " + history.getSource());
        }

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and gather_time <= '" + history.getEndTime() + "'");
        }

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findInformationToGatherTime(String hostId, String beforeDateTime) {
        String sql = """
                SELECT mh.*,mki.report_type as reportType
                FROM mtc_history mh
                JOIN mtc_ku_monitor mkm
                ON mh.monitor_id = mkm.id
                JOIN mtc_ku_item mki
                ON mkm.ku_item_id = mki.id
                """;

        if (StringUtils.isNotBlank(hostId)) {
            sql = sql.concat(" where mh.host_id = '" + hostId + "'");
        }

        if (StringUtils.isNotBlank(beforeDateTime)) {
            sql = sql.concat(" and mh.gather_time >= '" + beforeDateTime + "'");
        }

        sql = sql.concat(" and mki.report_type != 2 order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public void deleteByCondition(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }


    public List<History> findHistoryByCondition(History history, String beforeTime, String nowTime) {

        String sql = "select * from mtc_history where 1=1";

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" and host_id = '" + history.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(history.getMonitorId())) {
            sql = sql.concat(" and monitor_id = '" + history.getMonitorId() + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and gather_time >= '" + beforeTime + "'");
        }

        if (StringUtils.isNotBlank(nowTime)) {
            sql = sql.concat(" and gather_time <= '" + nowTime + "'");
        }

        sql = sql.concat(" order by gather_time desc limit 1");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));

    }

    public List<History> findHistoryByMonitorId(History history) {

        String sql = """
                select mhm.name monitorName,mhm.expression,mh.*
                from mtc_history mh
                RIGHT JOIN mtc_host_monitor mhm
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

    public void insertHistoryList(String historySql) {
        jpaTemplate.getJdbcTemplate().execute(historySql);
    }

    public List<History> findHistoryByDbMonitorId(History history) {
        String sql = """
                select mdm.name monitorName,mdi.expression,mh.*
                from mtc_db_monitor mdm
                JOIN mtc_db_item mdi
                ON mdm.db_item_id = mdi.id
                LEFT JOIN mtc_history mh
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

    public List<History> findHistoryByKuMonitorId(History history) {
        String sql = """
                select mkm.name monitorName,mki.expression,mh.*
                from mtc_ku_monitor mkm
                JOIN mtc_ku_item mki
                ON mkm.ku_item_id = mki.id
                LEFT JOIN mtc_history mh
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

    public List<History> findKuOverviewTotal(List<String> list, String kuId,String beforeTime,String nowDate) {

        String sql = """
                SELECT mh.*,mki.name,mki.report_type as reportType
                FROM mtc_ku_item mki
                LEFT JOIN mtc_history mh
                ON mki.id = mh.monitor_id
                """;

        if (StringUtils.isNotBlank(kuId)) {
            sql = sql.concat(" where mh.host_id = '" + kuId + "'");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append("'").append(list.get(i).concat("'"));
            if (i != list.size() - 1) {
                stringBuilder.append(",");
            }
        }

        sql = sql.concat(" and mh.gather_time BETWEEN '" + beforeTime + "' and '"+nowDate+"'");

        sql = sql.concat(" and mh.monitor_id in (" + stringBuilder + ")");

        sql = sql.concat(" ORDER BY mh.gather_time DESC LIMIT 22");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findHistoryByGatherTime(String hostId, String beforeTime) {
        String sql = """
                SELECT mh.*,mdi.data_type as reportType,mdi.expression
                FROM mtc_history mh
                JOIN mtc_db_monitor mdm
                ON mh.monitor_id = mdm.id
                JOIN mtc_db_item mdi
                ON mdm.db_item_id = mdi.id
                """;

        if (StringUtils.isNotBlank(hostId)) {
            sql = sql.concat(" where mh.host_id = '" + hostId + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time >= '" + beforeTime + "'");
        }

        sql = sql.concat(" and mdi.data_type != 2 order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findDbHistoryByHostId(String hostId, String beforeTime) {
        String sql = """
                SELECT mh.*,mdi.expression
                FROM mtc_db_monitor mdm
                JOIN mtc_db_item mdi
                ON mdm.db_item_id = mdi.id
                LEFT JOIN mtc_history mh
                ON mdm.id = mh.monitor_id
                """;

        if (StringUtils.isNotBlank(hostId)) {
            sql = sql.concat(" where mh.host_id = '" + hostId + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time > '" + beforeTime + "'");
        }
        sql = sql.concat(" limit 28");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findKuHistoryByHostId(String kuId, String beforeTime) {
        String sql = """
                SELECT mh.*,mdi.expression
                FROM mtc_ku_monitor mdm
                JOIN mtc_ku_item mdi
                ON mdm.ku_item_id = mdi.id
                LEFT JOIN mtc_history mh
                ON mdm.id = mh.monitor_id
                """;

        if (StringUtils.isNotBlank(kuId)) {
            sql = sql.concat(" where mh.host_id = '" + kuId + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time > '" + beforeTime + "'");
        }

        sql = sql.concat(" and mdi.report_type != 2 order by mh.gather_time desc limit 28");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findHistoryByInMonitorId(History history) {
        String sql = """
                select mkm.name monitorName,mki.expression,mh.*
                from mtc_internet_monitor mkm
                JOIN mtc_internet_item mki
                ON mkm.internet_item_id = mki.id
                LEFT JOIN mtc_history mh
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

    public List<History> findInternetToGatherTime(String internetId, String beforeTime) {
        String sql = """
                SELECT mh.*,mdi.expression
                FROM mtc_internet_monitor mdm
                JOIN mtc_internet_item mdi
                ON mdm.internet_item_id = mdi.id
                LEFT JOIN mtc_history mh
                ON mdm.id = mh.monitor_id
                """;

        if (StringUtils.isNotBlank(internetId)) {
            sql = sql.concat(" where mh.host_id = '" + internetId + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time > '" + beforeTime + "'");
        }

        sql = sql.concat(" and mdi.report_type = 1 order by mh.gather_time desc limit 2");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }


    public List<History> findHistoryByHostIds(String beforeTime) {

        String sql = """
                SELECT distinct host_id
                FROM mtc_history where 1=1
                """;

        sql = sql.concat(" and gather_time >= '" + beforeTime + "'");

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(History.class));
    }

    public List<History> findByHostTrigger(String hostId, String beforeTime) {

        String sql = """
                SELECT mh.*,mdi.data_type as reportType,mdm.expression
                FROM mtc_history mh
                JOIN mtc_host_monitor mdm
                ON mh.monitor_id = mdm.id
                JOIN mtc_item mdi
                ON mdm.monitor_item_id = mdi.id
                """;

        if (StringUtils.isNotBlank(hostId)) {
            sql = sql.concat(" where mh.host_id = '" + hostId + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time >= '" + beforeTime + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(History.class));
    }
}
