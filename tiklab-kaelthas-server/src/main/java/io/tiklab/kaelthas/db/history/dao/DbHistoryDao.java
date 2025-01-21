package io.tiklab.kaelthas.db.history.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.db.history.model.DbHistoryQuery;
import io.tiklab.kaelthas.util.TableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbHistoryDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void insertHistoryList(String historySql) {
        jpaTemplate.getJdbcTemplate().execute(historySql);
    }

    /**
     * 通过监控项id和时间段查询历史
     * @param dbHistoryQuery 查询数据
     * @param dbTableName 历史表名
     */
    public List<DbHistory> findHistoryByDbMonitorId(DbHistoryQuery dbHistoryQuery,String dbTableName) {

        String sql=" select mdm.name monitorName,mdi.expression,mh.* " +
                "from mtc_db_monitor mdm " +
                "JOIN mtc_db_item mdi " +
                "ON mdm.db_item_id = mdi.id " +
                "LEFT JOIN "+dbTableName+" mh " +
                "ON mh.db_monitor_id = mdm.id";

        if (StringUtils.isNotBlank(dbHistoryQuery.getBeginTime())) {
            sql = sql.concat(" and mh.gather_time >= '" + dbHistoryQuery.getBeginTime() + "'");
        }

        if (StringUtils.isNotBlank(dbHistoryQuery.getEndTime())) {
            sql = sql.concat(" and mh.gather_time <= '" + dbHistoryQuery.getEndTime() + "'");
        }

        if (StringUtils.isNotBlank(dbHistoryQuery.getDbId())) {
            sql = sql.concat(" where mdm.db_id = '" + dbHistoryQuery.getDbId() + "'");
        }

        if (StringUtils.isNotBlank(dbHistoryQuery.getMonitorId())) {
            sql = sql.concat(" and mdm.id = '" + dbHistoryQuery.getMonitorId() + "'");
        }


        sql = sql.concat(" order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbHistory.class));
    }


    /**
     * 通过监控项id和时间段查询历史
     * @param beforeTime 时间
     */
    public List<DbHistory> findDbHistoryByTime(String beforeTime,String dbTableName) {

        String sql = " SELECT distinct db_id FROM "+dbTableName+" where 1=1";

        sql = sql.concat(" and gather_time >= '" + beforeTime + "'");

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(DbHistory.class));
    }


    public List<DbHistory> findInformationToGatherTime(String hostId, String beforeDateTime) {
        String dbTableName = TableUtil.getDbTableName(0);

        String sql = " SELECT mh.*,mki.report_type as reportType "+
                "FROM "+dbTableName+" mh "+
                "JOIN mtc_ku_monitor mkm "+
                "ON mh.db_monitor_id = mkm.id "+
                "JOIN mtc_ku_item mki "+
                " ON mkm.ku_item_id = mki.id ";

        if (StringUtils.isNotBlank(hostId)) {
            sql = sql.concat(" where mh.db_id = '" + hostId + "'");
        }

        if (StringUtils.isNotBlank(beforeDateTime)) {
            sql = sql.concat(" and mh.gather_time >= '" + beforeDateTime + "'");
        }

        sql = sql.concat(" and mki.report_type != 2 order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbHistory.class));
    }

    public List<DbHistory> findHistoryByGatherTime(String hostId, String beforeTime) {
        String dbTableName = TableUtil.getDbTableName(0);
        String sql = " SELECT mh.*,mdi.data_type as reportType,mdi.expression "+
                "FROM "+dbTableName+" mh "+
                "JOIN mtc_db_monitor mdm "+
                "ON mh.db_monitor_id = mdm.id "+
                "JOIN mtc_db_item mdi "+
                "ON mdm.db_item_id = mdi.id ";

        if (StringUtils.isNotBlank(hostId)) {
            sql = sql.concat(" where mh.db_id = '" + hostId + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time >= '" + beforeTime + "'");
        }

        sql = sql.concat(" and mdi.data_type != 2 order by mh.gather_time desc");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbHistory.class));
    }


    public List<DbHistory> findDbHistoryByDbId(String hostId, String beforeTime) {
        String dbTableName = TableUtil.getDbTableName(0);
        String sql = " SELECT mh.*,mdi.expression "+
                "FROM mtc_db_monitor mdm "+
                "JOIN mtc_db_item mdi "+
                "ON mdm.db_item_id = mdi.id "+
                "LEFT JOIN "+dbTableName+" mh "+
                "ON mdm.id = mh.db_monitor_id ";

        if (StringUtils.isNotBlank(hostId)) {
            sql = sql.concat(" where mh.db_id = '" + hostId + "'");
        }

        if (StringUtils.isNotBlank(beforeTime)) {
            sql = sql.concat(" and mh.gather_time > '" + beforeTime + "'");
        }
        sql = sql.concat(" limit 28");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbHistory.class));
    }
}
