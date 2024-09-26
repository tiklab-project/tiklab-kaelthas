package io.thoughtware.kaelthas.history.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.common.util.ConversionDateUtil;
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


    public List<History> findInformationByMonitorId(History history) {
        String sql = "select * from mtc_history \n";

        if (StringUtils.isNotBlank(history.getHostId())) {
            sql = sql.concat(" where host_id = '" + history.getHostId() + "'");
        }

        sql = sql.concat(" and report_type in('1','3','4')");

        if (StringUtils.isNotBlank(history.getDataCategories())) {
            sql = sql.concat(" and data_categories = '" + history.getDataCategories() + "'");
        }

        if (history.getMonitorIdList() != null && !history.getMonitorIdList().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < history.getMonitorIdList().size(); i++) {
                stringBuilder.append("'").append(history.getMonitorIdList().get(i)).append("'");
                if (i != history.getMonitorIdList().size() - 1) {
                    stringBuilder.append(",");
                }
            }
            sql = sql.concat(" and monitor_id in (" + stringBuilder + ")");
        } else {
            if (StringUtils.isNotBlank(history.getDataCate())) {
                sql = sql.concat(" and data_categories = '" + history.getDataCate() + "'");
            }
        }

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and gather_time <= '" + history.getEndTime() + "'");
        }

        sql = sql.concat(" order by gather_time desc");

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


    public List<History> findHistoryByCondition(String hostId) {
        String beforeTime = ConversionDateUtil.findLocalDateTime(2, 5, null);
        String nowTime = ConversionDateUtil.findLocalDateTime(2, 0, null);

        String sql = "select * from mtc_history";

        sql = sql.concat(" where host_id = '" + hostId + "'");

        sql = sql.concat(" and gather_time >= '" + beforeTime + "'");

        sql = sql.concat(" and gather_time <= '" + nowTime + "'");

        sql = sql.concat(" limit 1");

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

    public List<History> findKuOverviewTotal(List<String> list, String kuId) {

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
        sql = sql.concat(" and mh.monitor_id in (" + stringBuilder + ")");

        sql = sql.concat(" ORDER BY mh.gather_time DESC LIMIT 21");

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
}
