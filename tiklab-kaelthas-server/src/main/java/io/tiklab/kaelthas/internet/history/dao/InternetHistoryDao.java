package io.tiklab.kaelthas.internet.history.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.internet.history.model.InternetHistoryQuery;
import io.tiklab.kaelthas.util.TableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class InternetHistoryDao {

    @Autowired
    private JpaTemplate jpaTemplate;
    public void insertHistoryList(String historySql) {
        jpaTemplate.getJdbcTemplate().execute(historySql);
    }

    /**
     * 通过监控项id和时间段查询历史
     * @param internetHistory 查询数据
     * @param dbTableName 历史表名
     */
    public List<InternetHistory> findInternetHistoryByInMonitorId(InternetHistoryQuery internetHistory, String dbTableName) {

     /*   String sql = " select mkm.name monitorName,mki.expression,mh.* "+
                "from mtc_internet_monitor mkm "+
                "JOIN mtc_internet_item mki "+
                "ON mkm.internet_item_id = mki.id "+
                "LEFT JOIN "+dbTableName+" mh "+
                "ON mh.internet_monitor_id = mkm.id ";*/


        String sql = " select mkm.name monitorName,mki.expression,mh.* "+
                "from "+dbTableName+" mh  "+
                "LEFT JOIN mtc_internet_monitor mkm "+
                "ON mh.internet_monitor_id = mkm.id "+
                "LEFT JOIN mtc_internet_item mki "+
                "ON mkm.internet_item_id = mki.id ";

        if (StringUtils.isNotBlank(internetHistory.getInternetId())) {
            sql = sql.concat(" where mkm.internet_id = '" + internetHistory.getInternetId() + "'");
        }

        if (StringUtils.isNotBlank(internetHistory.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + internetHistory.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(internetHistory.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + internetHistory.getEndTime() + "'");
        }



        if (StringUtils.isNotBlank(internetHistory.getInternetMonitorId())) {
            sql = sql.concat(" and mkm.id = '" + internetHistory.getInternetMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(InternetHistory.class));
    }

    /**
     * 通过监控项id和时间段查询历史
     * @param beforeTime 时间
     */
    public List<InternetHistory> findInHistoryByTime(String beforeTime, String dbTableName) {

        String sql = " SELECT distinct internet_id FROM "+dbTableName+" where 1=1";

        sql = sql.concat(" and gather_time >= '" + beforeTime + "'");

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(InternetHistory.class));
    }

    public List<InternetHistory> findHistoryByCondition(InternetHistory history, String beforeTime, String nowTime) {
        String tableName = TableUtil.getInternetTableName(LocalDate.now(),0);

        String sql = "select * from "+tableName+" where 1=1";

        if (StringUtils.isNotBlank(history.getInternetId())) {
            sql = sql.concat(" and internet_id = '" + history.getInternetId() + "'");
        }

        if (StringUtils.isNotBlank(history.getInternetMonitorId())) {
            sql = sql.concat(" and internet_monitor_id = '" + history.getInternetMonitorId() + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and gather_time >= '" + beforeTime + "'");
        }

        if (StringUtils.isNotBlank(nowTime)) {
            sql = sql.concat(" and gather_time <= '" + nowTime + "'");
        }

        sql = sql.concat(" order by gather_time desc limit 1");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(InternetHistory.class));

    }

    public List<InternetHistory> findInternetToGatherTime(String internetId, String beforeTime,String expression) {
        String tableName = TableUtil.getInternetTableName(LocalDate.now(),0);
        String sql = " SELECT mh.*,mdi.expression "+
                "FROM mtc_internet_monitor mdm "+
                "JOIN mtc_internet_item mdi "+
                "ON mdm.internet_item_id = mdi.id "+
                "LEFT JOIN "+tableName+" mh "+
                " ON mdm.id = mh.internet_monitor_id ";

        if (StringUtils.isNotBlank(internetId)) {
            sql = sql.concat(" where mh.internet_id = '" + internetId + "'");
        }

        if (StringUtils.isNotBlank(expression)) {
            sql = sql.concat(" and mdi.expression = '" + expression + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time > '" + beforeTime + "'");
        }

        sql = sql.concat(" and mdi.report_type = 1 order by mh.gather_time desc limit 2");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(InternetHistory.class));
    }
}
