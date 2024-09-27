package io.thoughtware.kaelthas.collection.mysql.service;

import io.thoughtware.kaelthas.collection.util.SqlUtil;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.history.service.HistoryService;
import io.thoughtware.kaelthas.db.database.service.DbInfoService;
import io.thoughtware.kaelthas.db.dbMonitor.model.DbMonitor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class MysqlService {

    List<History> histories = new LinkedList<>();

    @Autowired
    private DbInfoService dbInfoService;

    @Autowired
    private HistoryService historyService;

    ScheduledExecutorService service = Executors.newScheduledThreadPool(20);

    @Scheduled(cron = "0/10 * * * * ? ")
    public void changeDbMysql() {
        //查询mysql下的监控项
        List<DbMonitor> dbMonitorList = dbInfoService.findMysqlItemList();

        if (dbMonitorList.isEmpty()) {
            return;
        }

        // 创建并行任务列表
        //List<CompletableFuture<History>> futures = new ArrayList<>();

        List<History> list = new LinkedList<>();
        String dataTimeNow = SqlUtil.getDataTimeNow();
        for (DbMonitor dbMonitor : dbMonitorList) {
            /*CompletableFuture<History> future = CompletableFuture.supplyAsync(() -> getMonitor(dbMonitor), service);
            futures.add(future);*/
            History history = getMonitor(dbMonitor,dataTimeNow);
            list.add(history);
        }

        // 收集所有case分支的结果
        /*List<History> results = futures.stream()
                .map(CompletableFuture::join) // 等待所有任务完成
                .toList();*/

        // 收集处理后的数据
        histories.addAll(list);

        if (histories.size() > 300) {
            List<History> linkedList = new LinkedList<>(histories);
            historyService.insertForList(linkedList);
            histories.clear();
        }

    }

    // 应用关闭时要确保线程池关闭
    @PreDestroy
    public void shutdownThreadPool() {
        service.shutdown();
        try {
            if (!service.awaitTermination(1, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
        }
    }

    @NotNull
    private static History getMonitor(DbMonitor dbMonitor,String dataTimeNow) {

        History history = new History();
        history.setMonitorId(dbMonitor.getId());
        history.setHostId(dbMonitor.getDbId());
        history.setExpression(dbMonitor.getExpression());
        history.setGatherTime(dataTimeNow);

        String mysqlUrl = "jdbc:mysql://" + dbMonitor.getIp() + ":" + dbMonitor.getPort() + "/mysql";
        String mysqlQuery = "SELECT VERSION() as version";

        try (Connection connection = DriverManager.getConnection(mysqlUrl, dbMonitor.getUsername(), dbMonitor.getPassword());
             PreparedStatement statement = connection.prepareStatement(mysqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            String string = null;
            // 处理查询结果
            while (resultSet.next()) {
                // 假设查询结果包含一个名为 "column_name" 的列
                string = resultSet.getString("version");
            }
            Double version = SqlUtil.parseVersionToNumber(string);

            String sql;
            if (version > 7) {
                switch (dbMonitor.getMonitorItemId()) {
                    case "101":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'INSERT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "102":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'DELETE%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "103":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'UPDATE%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "104":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'SELECT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "106":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'ROLLBACK%' THEN COUNT_STAR ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "107":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'COMMIT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "110":
                        sql = """
                                SELECT COUNT(*) AS count
                                FROM information_schema.PROCESSLIST
                                WHERE COMMAND != 'Sleep'
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" AND DB = '" + dbMonitor.getDatName() + "'");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "111":
                        sql = """
                                SELECT COUNT(*) AS count
                                FROM information_schema.PROCESSLIST
                                WHERE COMMAND = 'Sleep'
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" AND DB = '" + dbMonitor.getDatName() + "'");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "114":
                        sql = """
                                SELECT COUNT(*) AS count
                                FROM performance_schema.data_locks
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE OBJECT_SCHEMA = '" + dbMonitor.getDatName() + "'");
                        }

                        getMysqlData(connection, sql, history);
                        break;
                }
            } else {
                switch (dbMonitor.getMonitorItemId()) {
                    case "101":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'INSERT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "102":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'DELETE%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "103":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'UPDATE%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "104":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'SELECT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "106":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'ROLLBACK%' THEN COUNT_STAR ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "107":
                        sql = """
                                SELECT SUM(CASE WHEN DIGEST_TEXT LIKE 'COMMIT%' THEN SUM_ROWS_AFFECTED ELSE 0 END) AS count
                                FROM performance_schema.events_statements_summary_by_digest
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" WHERE SCHEMA_NAME = '" + dbMonitor.getDatName() + "'\n GROUP BY  SCHEMA_NAME");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "110":
                        sql = """
                                SELECT COUNT(*) AS count
                                FROM information_schema.PROCESSLIST
                                WHERE COMMAND != 'Sleep'
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" AND DB = '" + dbMonitor.getDatName() + "'");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "111":
                        sql = """
                                SELECT COUNT(*) AS count
                                FROM information_schema.PROCESSLIST
                                WHERE COMMAND = 'Sleep'
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" AND DB = '" + dbMonitor.getDatName() + "'");
                        }
                        getMysqlData(connection, sql, history);
                        break;
                    case "114":
                        sql = """
                                SELECT COUNT(*) AS count
                                FROM information_schema.INNODB_LOCKS l
                                JOIN information_schema.INNODB_TRX t ON l.lock_trx_id = t.trx_id
                                WHERE t.trx_mysql_thread_id IN (
                                SELECT THREAD_ID
                                FROM performance_schema.threads
                                WHERE type = 'FOREGROUND'
                                """;
                        if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                            sql = sql.concat(" AND processlist_db = '" + dbMonitor.getDatName() + "'");
                        }
                        sql = sql.concat(")");

                        getMysqlData(connection, sql, history);
                        break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return history;
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
}
