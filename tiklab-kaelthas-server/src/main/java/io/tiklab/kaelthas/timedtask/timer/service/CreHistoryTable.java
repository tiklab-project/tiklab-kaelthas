package io.tiklab.kaelthas.timedtask.timer.service;

import io.tiklab.kaelthas.timedtask.timer.dao.TimerDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时每年创建1-12月份的历史表
 */
@Component
public class CreHistoryTable {
    @Autowired
    TimerDao timerDao;


    public void createTable() {

        List<String> dbNameList = timerDao.findDbName();
        // 获取当前年、月
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();

        for (int i=1;i<=12;i++){

            String month= String.valueOf(i);
            if (i<10){
                month = "0" + i;
            }

            //创建监控数据库历史数据表
            createHistoryDb(dbNameList,"db",year,month);

            //创建监控主机历史表
            createHistoryDb(dbNameList,"host",year,month);

            //创建监控K8s历史表
            createHistoryDb(dbNameList,"ku",year,month);

            //创建监控网络历史表
            createHistoryDb(dbNameList,"internet",year,month);
        }
    }


    /**
     * 动态创建监控数据库历史表
     * @param dbNameList 数据库所有表名
     * @param year 当前年
     * @param month 当前月
     */
    public void createHistoryDb(List<String> dbNameList,String tableType,int year,String month){
        //创建监控数据库历史表
        String historyDbName="mtc_"+tableType+"_history_"+year+month;
        List<String> collect = dbNameList.stream().filter(a -> historyDbName.equals(a)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)){
            createHistory(tableType,historyDbName);
        }

        //创建监控数据库历史表 （每分钟）
        String historyDbName01="mtc_"+tableType+"_history_"+year+month+"_1";
        List<String> collect01 = dbNameList.stream().filter(a -> historyDbName01.equals(a)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect01)){
            createHistory(tableType,historyDbName01);
        }

        //创建监控数据库历史表 （5分钟）
        String historyDbName05="mtc_"+tableType+"_history_"+year+month+"_5";
        List<String> collect05 = dbNameList.stream().filter(a -> historyDbName05.equals(a)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect05)){
            createHistory(tableType,historyDbName05);
        }

        //创建监控数据库历史表 （15分钟）
        String historyDbName15="mtc_"+tableType+"_history_"+year+month+"_15";
        List<String> collect15 = dbNameList.stream().filter(a -> historyDbName15.equals(a)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect15)){
            createHistory(tableType,historyDbName15);
        }
    }

    /**
     * 动态创建监控网络历史表
     * @param tableName 数据库所有表名
     * @param  tableType  类型
     */
    public void createHistory(String tableType,String tableName){
        switch (tableType){
            case "db":
                timerDao.createDataBaseDb(tableName);
                break;
            case "host":
                timerDao.createHostDb(tableName);
                break;
            case "ku":
                timerDao.createK8sDb(tableName);
                break;
            case "internet":
                timerDao.createInternetDb(tableName);
                break;

        }
    }
}
