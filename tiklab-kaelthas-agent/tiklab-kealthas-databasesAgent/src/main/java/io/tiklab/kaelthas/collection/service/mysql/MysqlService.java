package io.tiklab.kaelthas.collection.service.mysql;

import io.tiklab.kaelthas.collection.util.SqlUtil;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import io.tiklab.kaelthas.db.dbMonitor.model.DbMonitor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MysqlService {

    List<History> histories = new LinkedList<>();

    @Autowired
    private DbInfoService dbInfoService;

    @Autowired
    private HistoryService historyService;

    @Scheduled(cron = "0/30 * * * * ? ")
    public void changeDbMysql() {
        String dataTimeNow = SqlUtil.getDataTimeNow();

        //查询mysql下的监控项
        List<DbMonitor> dbMonitorList = dbInfoService.findMysqlItemList();

        if (dbMonitorList.isEmpty()) {
            return;
        }

        Collection<List<DbMonitor>> values = dbMonitorList.stream().collect(Collectors.groupingBy(DbMonitor::getDbId)).values();

        for (List<DbMonitor> value : values) {
            //每个数据库连接需要查询版本,拼接不同的SQL语句
            List<String> sqlList = new LinkedList<>();
            String sql = "";
            String mysqlUrl = "jdbc:mysql://" + value.get(0).getIp() + ":" + value.get(0).getPort() + "/mysql";
            String mysqlQuery = "SELECT VERSION() as version";

            try (Connection connection = DriverManager.getConnection(mysqlUrl, value.get(0).getUsername(), value.get(0).getPassword());
                 PreparedStatement statement = connection.prepareStatement(mysqlQuery);
                 ResultSet resultSet = statement.executeQuery()) {

                String string = null;
                // 处理查询结果
                while (resultSet.next()) {
                    // 假设查询结果包含一个名为 "column_name" 的列
                    string = resultSet.getString("version");
                }
                Double version = SqlUtil.parseVersionToNumber(string);
                for (int i = 0; i < value.size(); i++) {
                    String sqlString = getSQLString(value.get(i), version);
                    sqlList.add(sqlString);
                }

                for (int i = 0; i < sqlList.size(); i++) {
                    if (i != sqlList.size() - 1) {
                        sql = sql.concat(sqlList.get(i)).concat("\nunion all \n");
                    } else {
                        sql = sql.concat(sqlList.get(i));
                    }
                }

                List<History> historyList = getMysqlString(connection, value.get(0), sql,dataTimeNow);
                histories.addAll(historyList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (histories.size() > 30) {
            List<History> linkedList = new LinkedList<>(histories);
            historyService.insertForList(linkedList);
            histories.clear();
        }

    }

    @NotNull
    private static String getSQLString(DbMonitor dbMonitor, Double version) {
        String sql = "";

        if (version > 7) {
            switch (dbMonitor.getMonitorItemId()) {
                case "101":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'INSERT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "102":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'DELETE%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "103":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'UPDATE%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "104":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'SELECT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "106":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'ROLLBACK%' THEN COUNT_STAR ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "107":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'COMMIT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "110":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, COUNT(*) AS count " +
                            "FROM information_schema.PROCESSLIST " +
                            "WHERE COMMAND != 'Sleep' ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" AND DB = '" + dbMonitor.getDatName() + "'");
                    }
                    break;
                case "111":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, COUNT(*) AS count " +
                            "FROM information_schema.PROCESSLIST " +
                            "WHERE COMMAND = 'Sleep' ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" AND DB = '" + dbMonitor.getDatName() + "'");
                    }
                    break;
                case "114":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, COUNT(*) AS count " +
                            "FROM performance_schema.data_locks ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE OBJECT_SCHEMA = '" + dbMonitor.getDatName() + "'");
                    }

                    break;
            }
        } else {
            switch (dbMonitor.getMonitorItemId()) {
                case "101":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'INSERT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "102":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'DELETE%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "103":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'UPDATE%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "104":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'SELECT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "106":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'ROLLBACK%' THEN COUNT_STAR ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "107":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, SUM(CASE WHEN DIGEST_TEXT LIKE 'COMMIT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count " +
                            "FROM performance_schema.events_statements_summary_by_digest ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                    }
                    break;
                case "110":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, COUNT(*) AS count " +
                            "FROM information_schema.PROCESSLIST " +
                            "WHERE COMMAND != 'Sleep' ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" AND DB = '" + dbMonitor.getDatName() + "'");
                    }
                    break;
                case "111":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, COUNT(*) AS count " +
                            "FROM information_schema.PROCESSLIST " +
                            "WHERE COMMAND = 'Sleep' ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" AND DB = '" + dbMonitor.getDatName() + "'");
                    }
                    break;
                case "114":
                    sql = " SELECT '" + dbMonitor.getId() + "' as monitorId, COUNT(*) AS count FROM information_schema.INNODB_LOCKS l JOIN information_schema.INNODB_TRX t ON l.lock_trx_id = t.trx_id " +
                            "WHERE t.trx_mysql_thread_id IN ( " +
                            "SELECT THREAD_ID " +
                            "FROM performance_schema.threads WHERE type = 'FOREGROUND'";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" AND processlist_db = '" + dbMonitor.getDatName() + "'");
                    }
                    sql = sql.concat(")");
                    break;
            }
        }

        return sql;
    }

    private static void getMysqlData(Connection connection, String sql, History history) {
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            // 处理查询结果
            while (resultSet.next()) {
                // 假设查询结果包含一个名为 "column_name" 的列
                String string = resultSet.getString("count");
                history.setReportData(string);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<History> getMysqlString(Connection connection, DbMonitor dbMonitor, String sql,String dataTimeNow) {
        List<History> list = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            // 处理查询结果
            while (resultSet.next()) {
                History history = new History();
                history.setHostId(dbMonitor.getDbId());
                history.setGatherTime(dataTimeNow);
                history.setMonitorId(resultSet.getString("monitorId"));
                String string = resultSet.getString("count");
                history.setReportData(string);
                list.add(history);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
