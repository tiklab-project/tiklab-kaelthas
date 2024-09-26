package io.thoughtware.kaelthas.db.database.service;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.db.dbDynamic.model.DbDynamic;
import io.thoughtware.kaelthas.db.dbDynamic.service.DbDynamicService;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.db.database.dao.DbInfoDao;
import io.thoughtware.kaelthas.db.database.entity.DbInfoEntity;
import io.thoughtware.kaelthas.db.database.model.DbInfo;
import io.thoughtware.kaelthas.db.dbMonitor.model.DbMonitor;
import io.thoughtware.kaelthas.db.dbMonitor.service.DbMonitorService;
import io.thoughtware.kaelthas.db.dbTrigger.model.DbTrigger;
import io.thoughtware.kaelthas.db.dbTrigger.service.DbTriggerService;
import io.thoughtware.kaelthas.alarm.model.Alarm;
import io.thoughtware.kaelthas.alarm.service.AlarmService;
import io.thoughtware.kaelthas.common.util.ConversionDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Service
public class DbInfoInfoServiceImpl implements DbInfoService {

    @Autowired
    private DbInfoDao dbInfoDao;

    @Autowired
    private DbMonitorService dbMonitorService;

    @Autowired
    private DbTriggerService dbTriggerService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private DbDynamicService dbDynamicService;

    @Override
    public Pagination<DbInfo> findDbInfoPage(DbInfo dbInfo) {
        return dbInfoDao.findDbInfoPage(dbInfo);
    }

    @Override
    public String createDbInfo(DbInfo dbInfo) {
        try {
            String date = ConversionDateUtil.date(9);
            DbInfoEntity dbInfoEntity = BeanMapper.map(dbInfo, DbInfoEntity.class);
            dbInfoEntity.setCreateTime(date);
            dbInfoEntity.setColor((int) Math.round(Math.random() * 5));
            DbDynamic dbDynamic = new DbDynamic();

            String dbId = dbInfoDao.createDbInfo(dbInfoEntity);

            dbDynamic.setDbId(dbId);
            dbDynamic.setName("创建主机---" + dbInfo.getName());
            dbDynamicService.createDbDynamic(dbDynamic);
            return dbId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDbInfo(DbInfo dbInfo) {
        DbInfoEntity dbInfoEntity = BeanMapper.map(dbInfo, DbInfoEntity.class);
        String date = ConversionDateUtil.date(9);
        dbInfoEntity.setUpdateTime(date);
        dbInfoDao.updateDbInfo(dbInfoEntity);
    }

    @Override
    public void deleteDbInfo(String id) {
        dbInfoDao.deleteDbInfo(id);
    }

    @Override
    public DbInfo findDbInfoById(String id) {
        DbInfoEntity dbInfoById = dbInfoDao.findDbInfoById(id);
        //查询出数据库下监控项,触发器和告警的数量
        List<DbMonitor> dbMonitorList = dbMonitorService.findMonitorNum(id);

        List<DbTrigger> triggerList = dbTriggerService.findListByDbId(id);

        Alarm alarm = new Alarm();
        alarm.setHostId(id);
        List<Alarm> alarmList = alarmService.findAlarmList(alarm);

        DbInfo dbInfo = BeanMapper.map(dbInfoById, DbInfo.class);
        dbInfo.setMonitorNum(dbMonitorList.size());
        dbInfo.setTriggerNum(triggerList.size());
        dbInfo.setAlarmNum(alarmList.size());

        return dbInfo;
    }

    @Override
    public Result<?> testSql(DbInfo dbInfo) {
        String url = "";
        String user = dbInfo.getUsername();
        String password = dbInfo.getPassword();

        Connection connection = null;
        String driverName = null;

        try {
            // 加载JDBC驱动（可选，依赖于驱动实现）
            driverName = switch (dbInfo.getDbType()) {
                case "PostgreSQL" -> {
                    url = "jdbc:postgresql://" + dbInfo.getIp() + ":" + dbInfo.getDbPort() + "/postgres";
                    yield "org.postgresql.Driver";
                }
                case "MYSQL" -> {
                    url = "jdbc:mysql://" + dbInfo.getIp() + ":" + dbInfo.getDbPort() + "/mysql";
                    yield "com.mysql.cj.jdbc.Driver";
                }
                default -> driverName;
            };

            Class.forName(driverName);

            // 尝试建立连接
            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("连接成功！");
                return Result.ok();
            } else {
                System.out.println("连接失败！");
                return Result.error(505, "连接失败");
            }
        } catch (SQLException e) {
            System.out.println("连接失败，SQLException: " + e.getMessage());
            return Result.error(505, "连接失败，SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("找不到JDBC驱动类: " + e.getMessage());
            return Result.error(505, "找不到JDBC驱动类: " + e.getMessage());
        } finally {
            // 关闭连接
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<DbMonitor> findUsableDbInfoList() {
        //通过ip查找数据库,并且查找数据库的监控项
        return dbInfoDao.findUsableDbInfoList();
    }

    @Override
    public List<DbInfo> findDropDown() {
        return dbInfoDao.findDropDown();
    }

    @Override
    public List<DbInfo> findAll() {
        List<DbInfoEntity> dbInfoEntityList = dbInfoDao.findAll();
        return BeanMapper.mapList(dbInfoEntityList, DbInfo.class);
    }

    @Override
    public List<DbMonitor> findMysqlItemList() {
        return dbInfoDao.findMysqlItemList();
    }
}
