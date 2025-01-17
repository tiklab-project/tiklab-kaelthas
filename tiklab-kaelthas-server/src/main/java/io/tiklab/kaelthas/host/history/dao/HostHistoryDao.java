package io.tiklab.kaelthas.host.history.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.host.history.model.HostHistory;
import io.tiklab.kaelthas.host.history.model.HostHistoryQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HostHistoryDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void insertHistoryList(String historySql) {
        jpaTemplate.getJdbcTemplate().execute(historySql);
    }

    /**
     * 通过监控项id和时间段查询历史
     * @param hostHistoryQuery 查询数据
     * @param dbTableName 历史表名
     */
    public List<HostHistory> findHistoryByMonitorId(HostHistoryQuery hostHistoryQuery, String dbTableName) {

        String sql = " select mhm.name monitorName,mhm.expression,mh.* "+
                "from "+dbTableName+" mh "+
                "RIGHT JOIN mtc_host_monitor mhm "+
                "ON mh.host_monitor_id = mhm.id ";

        if (StringUtils.isNotBlank(hostHistoryQuery.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + hostHistoryQuery.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(hostHistoryQuery.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + hostHistoryQuery.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(hostHistoryQuery.getHostId())) {
            sql = sql.concat(" where mhm.host_id = '" + hostHistoryQuery.getHostId() + "'");
        }

        if (StringUtils.isNotBlank(hostHistoryQuery.getHostMonitorId())) {
            sql = sql.concat(" and mhm.id = '" + hostHistoryQuery.getHostMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(HostHistory.class));
    }

    /**
     * 通过监控项id和时间段查询历史
     * @param beforeTime 时间
     */
    public List<HostHistory> findHostHistoryByTime(String beforeTime, String dbTableName) {

        String sql = " SELECT distinct host_id FROM "+dbTableName+" where 1=1";

        sql = sql.concat(" and gather_time >= '" + beforeTime + "'");

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(HostHistory.class));
    }
}
