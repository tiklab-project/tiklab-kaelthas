package io.tiklab.kaelthas.kubernetes.history.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistoryQuery;
import io.tiklab.kaelthas.util.TableUtil;
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


    public List<KubernetesHistory> findKuOverviewTotal(List<String> list, String kuId,String beforeTime,String nowDate) {
        String tableName = TableUtil.getK8sTableName(0);

        String sql = " SELECT mh.*,mki.name,mki.report_type as reportType "+
                "FROM mtc_ku_item mki "+
                "LEFT JOIN "+tableName+" mh "+
                "ON mki.id = mh.ku_monitor_id ";

        if (StringUtils.isNotBlank(kuId)) {
            sql = sql.concat(" where mh.ku_id = '" + kuId + "'");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append("'").append(list.get(i).concat("'"));
            if (i != list.size() - 1) {
                stringBuilder.append(",");
            }
        }

        sql = sql.concat(" and mh.gather_time BETWEEN '" + beforeTime + "' and '"+nowDate+"'");

        sql = sql.concat(" and mh.ku_monitor_id in (" + stringBuilder + ")");

        sql = sql.concat(" ORDER BY mh.gather_time DESC LIMIT 22");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(KubernetesHistory.class));
    }


    public List<KubernetesHistory> findKuHistoryByKuId(String kuId, String beforeTime) {
        String tableName = TableUtil.getK8sTableName(0);
        String sql = "SELECT mh.*,mdi.expression "+
                "FROM mtc_ku_monitor mdm "+
                "JOIN mtc_ku_item mdi "+
                "ON mdm.ku_item_id = mdi.id "+
                "LEFT JOIN "+tableName+" mh "+
                "ON mdm.id = mh.ku_monitor_id ";

        if (StringUtils.isNotBlank(kuId)) {
            sql = sql.concat(" where mh.ku_id = '" + kuId + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time > '" + beforeTime + "'");
        }

        sql = sql.concat(" and mdi.report_type != 2 order by mh.gather_time desc limit 28");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(KubernetesHistory.class));
    }
}
