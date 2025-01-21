package io.tiklab.kaelthas.db.agent.service;


import io.tiklab.kaelthas.db.agent.dao.DbSqlDao;
import io.tiklab.kaelthas.db.agent.model.DbMonitorVo;
import io.tiklab.kaelthas.db.agent.utils.AgentSqlUtil;
import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.db.history.service.DbHistoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时拉取配置进行数据上报
 */
@Service
public class PgsqlService {

    @Autowired
    private DbSqlDao dbSqlDao;

    @Autowired
    DbHistoryService dbHistoryService;

    public static final List<DbHistory> histories = new LinkedList<>();

    public static final Map<String,Long> pgSqlStoreTime = new HashMap();

    //定时拉取pgsql的数据,执行SQL进行数据上报
   // @Scheduled(cron = "0 0/1 * * * ? ")
    public void changeDbAim() {
        String dataTimeNow = AgentSqlUtil.date(9);

        List<DbMonitorVo> DbMonitorVoList = dbSqlDao.findSqlItemList("PostgreSQL");

        if (DbMonitorVoList.isEmpty()) {
            return;
        }

        if (ObjectUtils.isEmpty(pgSqlStoreTime.get("time"))){
            pgSqlStoreTime.put("time",System.currentTimeMillis());
        }

        Collection<List<DbMonitorVo>> values = DbMonitorVoList.stream().collect(Collectors.groupingBy(DbMonitorVo::getDbId)).values();

        for (List<DbMonitorVo> value : values) {
            //将每个数据库来进行一个SQL拼接
            String sql = "";
            DbHistory dbHistory = new DbHistory();
            dbHistory.setDbId(value.get(0).getDbId());
            dbHistory.setGatherTime(dataTimeNow);
            List<String> sqlList = new LinkedList<>();

            for (DbMonitorVo DbMonitorVo : value) {
                dbHistory.setDbMonitorId(DbMonitorVo.getId());
                String pgSQLString = getPgSQLString(DbMonitorVo);
                sqlList.add(pgSQLString);
            }

            for (int i = 0; i < sqlList.size(); i++) {
                if (i != sqlList.size() - 1) {
                    sql = sql.concat(sqlList.get(i)).concat("\nunion all \n");
                } else {
                    sql = sql.concat(sqlList.get(i));
                }
            }

            List<DbHistory> DbHistoryVoList = getReportNum(dataTimeNow, value.get(0), sql);
            if (DbHistoryVoList != null) {
                histories.addAll(DbHistoryVoList);
            }
        }

        Long aLong = pgSqlStoreTime.get("time");
        long time = System.currentTimeMillis() - aLong;
        //定时上报数据 存储时间大于30条或者时间超过1分钟
        if (histories.size() > 30||time>=60000) {
            List<DbHistory> DbHistoryVoList = new LinkedList<>(histories);
            dbHistoryService.insertForList(DbHistoryVoList);
            histories.clear();
            pgSqlStoreTime.put("time",System.currentTimeMillis());
        }
    }

    //根据不同的监控项生成指定的SQL
    private String getPgSQLString(DbMonitorVo DbMonitorVo) {
        String sql = null;
        switch (DbMonitorVo.getMonitorItemId()) {
            case "1":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(tup_inserted) as count FROM pg_stat_database ";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "2":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(tup_deleted) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "3":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(tup_updated) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "4":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(tup_fetched) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "5":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(tup_returned) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "6":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(xact_rollback) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "7":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(xact_commit) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "8":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(conflicts) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "9":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId," +
                        "CASE WHEN (blks_hit + blks_read) = 0 THEN 0 " +
                        "ELSE ROUND((blks_hit::numeric / (blks_hit + blks_read)::numeric) * 100, 2) " +
                        "END AS count " +
                        "FROM pg_stat_database " +
                        "WHERE blks_hit + blks_read > 0";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" and datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "10":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, COUNT(*) as count FROM pg_stat_activity WHERE state = 'active'";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "11":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, COUNT(*)as count FROM pg_stat_activity WHERE state = 'idle'";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "13":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(temp_files) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
            case "14":
                sql = "SELECT '" + DbMonitorVo.getId() + "' as monitorId, sum(deadlocks) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(DbMonitorVo.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + DbMonitorVo.getDatName() + "'");
                }
                break;
        }

        return sql;
    }

    //将拼接的SQL进行执行,查询出批量的数据
    private List<DbHistory> getReportNum(String dataTimeNow, DbMonitorVo DbMonitorVo, String sql) {

        List<DbHistory> list = new LinkedList<>();

        String dbUrl = "jdbc:postgresql://" + DbMonitorVo.getIp() + ":" + DbMonitorVo.getPort() + "/postgres";

        try (Connection connection = DriverManager.getConnection(dbUrl, DbMonitorVo.getUsername(), DbMonitorVo.getPassword());
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            // 处理查询结果
            while (resultSet.next()) {
                DbHistory dbHistory = new DbHistory();
                dbHistory.setDbId(DbMonitorVo.getDbId());
                dbHistory.setGatherTime(dataTimeNow);
                // 假设查询结果包含一个名为 "column_name" 的列
                dbHistory.setDbMonitorId(resultSet.getString("monitorId"));
                dbHistory.setReportData(resultSet.getString("count"));
                list.add(dbHistory);
            }
        } catch (SQLException e) {
            return null;
        }
        return list;
    }
}
