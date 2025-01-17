package io.tiklab.kaelthas.internet.history.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.internet.history.model.InternetHistoryQuery;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

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

        String sql = " select mkm.name monitorName,mki.expression,mh.* "+
                "from mtc_internet_monitor mkm "+
                "JOIN mtc_internet_item mki "+
                "ON mkm.internet_item_id = mki.id "+
                "LEFT JOIN "+dbTableName+" mh "+
                "ON mh.internet_monitor_id = mkm.id ";

        if (StringUtils.isNotBlank(internetHistory.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + internetHistory.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(internetHistory.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + internetHistory.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(internetHistory.getInternetId())) {
            sql = sql.concat(" where mkm.internet_id = '" + internetHistory.getInternetId() + "'");
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

}
