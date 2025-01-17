package io.tiklab.kaelthas.kubernetes.history.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistoryQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KubernetesHistoryDao {



    @Autowired
    private JpaTemplate jpaTemplate;

    public void insertHistoryList(String historySql) {
        jpaTemplate.getJdbcTemplate().execute(historySql);
    }

    /**
     * 通过监控项id和时间段查询历史
     * @param history 查询数据
     * @param dbTableName 历史表名
     */
    public List<KubernetesHistory> findHistoryByKuMonitorId(KubernetesHistoryQuery history, String dbTableName) {
        String sql = " select mkm.name monitorName,mki.expression,mh.* "+
                "from mtc_ku_monitor mkm "+
                "JOIN mtc_ku_item mki "+
                "ON mkm.ku_item_id = mki.id "+
                "LEFT JOIN "+dbTableName+" mh "+
                "ON mh.ku_monitor_id = mkm.id ";

        if (StringUtils.isNotBlank(history.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + history.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + history.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(history.getKuId())) {
            sql = sql.concat(" where mkm.ku_id = '" + history.getKuId() + "'");
        }

        if (StringUtils.isNotBlank(history.getKuMonitorId())) {
            sql = sql.concat(" and mkm.id = '" + history.getKuMonitorId() + "'");
        }

        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(KubernetesHistory.class));
    }

    /**
     * 通过监控项id和时间段查询历史
     * @param beforeTime 时间
     */
    public List<KubernetesHistory> findKuHistoryByTime(String beforeTime, String dbTableName) {

        String sql = " SELECT distinct ku_id FROM "+dbTableName+" where 1=1";

        sql = sql.concat(" and gather_time >= '" + beforeTime + "'");

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(KubernetesHistory.class));
    }

}
