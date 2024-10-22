package io.tiklab.kaelthas.collection.service.pgsql;


import io.tiklab.kaelthas.collection.util.SqlUtil;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import io.tiklab.kaelthas.db.dbMonitor.model.DbMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PgsqlService {

    public static final List<History> histories = new LinkedList<>();

    @Autowired
    private DbInfoService dbInfoService;

    @Autowired
    private HistoryService historyService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void changeDbAim() {
        String dataTimeNow = SqlUtil.date(9);

        List<DbMonitor> dbMonitorList = dbInfoService.findUsableDbInfoList();

        if (dbMonitorList.isEmpty()) {
            return;
        }

        Collection<List<DbMonitor>> values = dbMonitorList.stream().collect(Collectors.groupingBy(DbMonitor::getDbId)).values();

        for (List<DbMonitor> value : values) {
            //将每个数据库来进行一个SQL拼接
            String sql = "";
            History history = new History();
            history.setHostId(value.get(0).getDbId());
            history.setGatherTime(dataTimeNow);
            List<String> sqlList = new LinkedList<>();

            for (DbMonitor dbMonitor : value) {
                history.setMonitorId(dbMonitor.getId());
                String pgSQLString = getPgSQLString(dbMonitor);
                sqlList.add(pgSQLString);
            }

            for (int i = 0; i < sqlList.size(); i++) {
                if (i != sqlList.size() - 1) {
                    sql = sql.concat(sqlList.get(i)).concat("\nunion all \n");
                } else {
                    sql = sql.concat(sqlList.get(i));
                }
            }

            List<History> historyList = getReportNum(dataTimeNow, value.get(0), sql);
            histories.addAll(historyList);
        }

        if (histories.size() > 30) {
            List<History> historyList = new LinkedList<>(histories);
            historyService.insertForList(historyList);
            histories.clear();
        }
    }

    private String getPgSQLString(DbMonitor dbMonitor) {
        String sql = null;
        switch (dbMonitor.getMonitorItemId()) {
            case "1":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(tup_inserted) as count FROM pg_stat_database ";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "2":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(tup_deleted) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "3":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(tup_updated) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "4":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(tup_fetched) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "5":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(tup_returned) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "6":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(xact_rollback) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "7":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(xact_commit) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "8":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(conflicts) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "9":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId," +
                        "CASE WHEN (blks_hit + blks_read) = 0 THEN 0 " +
                        "ELSE ROUND((blks_hit::numeric / (blks_hit + blks_read)::numeric) * 100, 2) " +
                        "END AS count " +
                        "FROM pg_stat_database " +
                        "WHERE blks_hit + blks_read > 0";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" and datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "10":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, COUNT(*) as count FROM pg_stat_activity WHERE state = 'active'";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "11":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, COUNT(*)as count FROM pg_stat_activity WHERE state = 'idle'";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "13":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(temp_files) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
            case "14":
                sql = "SELECT '" + dbMonitor.getId() + "' as monitorId, sum(deadlocks) as count FROM pg_stat_database";
                if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                    sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                }
                break;
        }

        return sql;
    }

    private List<History> getReportNum(String dataTimeNow, DbMonitor dbMonitor, String sql) {

        List<History> list = new LinkedList<>();

        String dbUrl = "jdbc:postgresql://" + dbMonitor.getIp() + ":" + dbMonitor.getPort() + "/postgres";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbMonitor.getUsername(), dbMonitor.getPassword());
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            // 处理查询结果
            while (resultSet.next()) {
                History history = new History();
                history.setHostId(dbMonitor.getDbId());
                history.setGatherTime(dataTimeNow);
                // 假设查询结果包含一个名为 "column_name" 的列
                history.setMonitorId(resultSet.getString("monitorId"));
                history.setReportData(resultSet.getString("count"));
                list.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
