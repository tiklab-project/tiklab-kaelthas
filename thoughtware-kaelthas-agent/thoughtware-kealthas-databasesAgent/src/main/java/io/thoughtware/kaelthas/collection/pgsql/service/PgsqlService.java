package io.thoughtware.kaelthas.collection.pgsql.service;


import io.thoughtware.kaelthas.collection.util.SqlUtil;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.history.service.HistoryService;
import io.thoughtware.kaelthas.db.database.service.DbInfoService;
import io.thoughtware.kaelthas.db.dbMonitor.model.DbMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class PgsqlService {

    public static final List<History> histories = new LinkedList<>();

    @Autowired
    private DbInfoService dbInfoService;

    @Autowired
    private HistoryService historyService;

    ScheduledExecutorService service = Executors.newScheduledThreadPool(20);

    @Scheduled(cron = "0/10 * * * * ? ")
    public void changeDbAim() {
        List<DbMonitor> dbMonitorList = dbInfoService.findUsableDbInfoList();

        if (dbMonitorList.isEmpty()) {
            return;
        }

        List<History> list = new LinkedList<>();
        //List<CompletableFuture<History>> futures = new LinkedList<>();
        String dataTimeNow = SqlUtil.getDataTimeNow();

        for (DbMonitor dbMonitor : dbMonitorList) {

            /*CompletableFuture<History> future = CompletableFuture.supplyAsync(() -> getPgList(dbMonitor), service);
            futures.add(future);*/
            History pgList = getPgList(dbMonitor, dataTimeNow);
            list.add(pgList);
        }

        /*List<History> result = futures.stream()
                .map(CompletableFuture::join)
                .toList();*/

        histories.addAll(list);

        if (histories.size() > 300) {
            List<History> historyList = new LinkedList<>(histories);
            historyService.insertForList(historyList);
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

    private History getPgList(DbMonitor dbMonitor, String dataTimeNow) {
        History history = new History();
        history.setMonitorId(dbMonitor.getId());
        history.setHostId(dbMonitor.getDbId());
        history.setGatherTime(dataTimeNow);

        String sql;
        switch (dbMonitor.getMonitorItemId()) {
            case "1":
                try {
                    sql = "SELECT tup_inserted as count FROM pg_stat_database ";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "2":
                try {
                    sql = "SELECT tup_deleted as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "3":
                try {
                    sql = "SELECT tup_updated as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "4":
                try {
                    sql = "SELECT tup_fetched as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "5":
                try {
                    sql = "SELECT tup_returned as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "6":
                try {
                    sql = "SELECT xact_rollback as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "7":
                try {
                    sql = "SELECT xact_commit as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "8":
                try {
                    sql = "SELECT conflicts as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "9":
                try {
                    sql = """
                            SELECT
                            CASE
                            WHEN (blks_hit + blks_read) = 0 THEN 0
                            ELSE ROUND((blks_hit::numeric / (blks_hit + blks_read)::numeric) * 100, 2)
                            END AS count
                            FROM pg_stat_database
                            WHERE blks_hit + blks_read > 0
                            """;
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" and datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "10":
                try {
                    sql = "SELECT COUNT(*) as count FROM pg_stat_activity WHERE state = 'active'";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "11":
                try {
                    sql = "SELECT COUNT(*)as count FROM pg_stat_activity WHERE state = 'idle'";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "13":
                try {
                    sql = "SELECT temp_files as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
            case "14":
                try {
                    sql = "SELECT deadlocks as count FROM pg_stat_database";
                    if (StringUtils.isNotBlank(dbMonitor.getDatName())) {
                        sql = sql.concat(" WHERE datname = '" + dbMonitor.getDatName() + "'");
                    }
                    getReportNum(history, dbMonitor, sql);
                } catch (Exception e) {
                    break;
                }
                break;
        }

        return history;
    }


    private void getReportNum(History history, DbMonitor dbMonitor, String sql) {

        String dbUrl = "jdbc:postgresql://" + dbMonitor.getIp() + ":" + dbMonitor.getPort() + "/postgres";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbMonitor.getUsername(), dbMonitor.getPassword());
             PreparedStatement statement = connection.prepareStatement(sql);
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
