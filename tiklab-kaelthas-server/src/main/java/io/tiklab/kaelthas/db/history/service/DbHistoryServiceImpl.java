package io.tiklab.kaelthas.db.history.service;

import io.tiklab.kaelthas.db.graphics.model.DbGraphics;
import io.tiklab.kaelthas.db.graphics.service.DbGraphicsService;
import io.tiklab.kaelthas.db.graphicsMonitor.model.DbGraphicsMonitor;
import io.tiklab.kaelthas.db.graphicsMonitor.service.DbGraphicsMonitorService;
import io.tiklab.kaelthas.db.history.dao.DbHistoryDao;
import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.db.history.model.DbHistoryQuery;
import io.tiklab.kaelthas.db.trigger.model.DbTrigger;
import io.tiklab.kaelthas.db.trigger.service.DbTriggerService;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.util.ConversionScriptsUtils;
import io.tiklab.kaelthas.util.SqlUtil;
import io.tiklab.kaelthas.util.TableUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DbHistoryServiceImpl implements DbHistoryService {

    @Autowired
    DbHistoryDao dbHistoryDao;

    @Autowired
    private DbGraphicsService dbGraphicsService;

    @Autowired
    private DbGraphicsMonitorService dbGraphicsMonitorService;

    @Autowired
    private DbTriggerService dbTriggerService;
    @Override
    public void insertForList(List<DbHistory> entityList) {

        //获取数据库表名
        String dbTableName = TableUtil.getDbTableName(0);

        List<Map<String, Object>> mapList = getMapList(entityList);
        //拼接添加历史的数据的sql
        String historySql = SqlUtil.getBatchInsertSql(dbTableName, mapList);
        dbHistoryDao.insertHistoryList(historySql);


        List<DbHistory> list1 = new ArrayList<>();
        List<DbHistory> list5 = new ArrayList<>();
        List<DbHistory> list15 = new ArrayList<>();

        for (DbHistory information : entityList) {
            String gatherTime = information.getGatherTime();
            //判断是否能插入到一分钟的表当中
            boolean divisible = ConversionDateUtil.isDivisible(gatherTime, 1);
            if (divisible) {
                list1.add(information);
            }

            //添加到5分钟
            boolean divisible1 = ConversionDateUtil.isDivisible(gatherTime, 5);
            if (divisible1) {
                list5.add(information);
            }

            //添加到15分钟
            boolean divisible2 = ConversionDateUtil.isDivisible(gatherTime, 15);
            if (divisible2) {
                list15.add(information);
            }
        }


        //添加数据到数据库中
        if (!list1.isEmpty()) {
            List<Map<String, Object>> mapList1 = getMapList(list1);
            String historySql1 = SqlUtil.getBatchInsertSql(dbTableName+"_1", mapList1);
            dbHistoryDao.insertHistoryList(historySql1);
        }

        if (!list5.isEmpty()) {
            List<Map<String, Object>> mapList5 = getMapList(list5);
            String historySql5 = SqlUtil.getBatchInsertSql(dbTableName+"_5", mapList5);
            dbHistoryDao.insertHistoryList(historySql5);
        }

        if (!list15.isEmpty()) {
            List<Map<String, Object>> mapList15 = getMapList(list15);
            String historySql15 = SqlUtil.getBatchInsertSql(dbTableName+"_15", mapList15);
            dbHistoryDao.insertHistoryList(historySql15);
        }
    }

    @Override
    public List<List<DbHistory>> findGraphicsLine(DbHistoryQuery dbHistoryQuery) {
        List<List<DbHistory>> resultList;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalTime = LocalDateTime.parse(dbHistoryQuery.getBeginTime(), dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(dbHistoryQuery.getEndTime(), dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();

        if (minutes <= 5) {
            resultList = joinMonitorHistoryData(dbHistoryQuery, 1);
        }else if (minutes <= 60) {
            resultList = joinMonitorHistoryData(dbHistoryQuery, 2);
        }else if (minutes <= 720) {
            resultList = joinMonitorHistoryData(dbHistoryQuery, 3);
        }else {
            resultList = joinMonitorHistoryData(dbHistoryQuery, 4);
        }
        return resultList;
    }

    @Override
    public List<DbHistory> findDbHistoryByTime(String beforeTime) {
        String dbTableName = TableUtil.getDbTableName(0);
        List<DbHistory> historyList = dbHistoryDao.findDbHistoryByTime(beforeTime, dbTableName);
        return historyList;
    }

    @Override
    public List<DbHistory> findInformationToGatherTime(String dbId, String beforeDateTime) {
        List<DbHistory> dbHistoryList = dbHistoryDao.findInformationToGatherTime(dbId, beforeDateTime);
        return dbHistoryList;
    }

    @Override
    public List<DbHistory> findHistoryByGatherTime(String dbId, String beforeTime) {
        return dbHistoryDao.findHistoryByGatherTime(dbId, beforeTime);
    }

    /**
     * 根据监控数据库的id和时间查询时间之后的存储数据
     */
    @Override
    public List<DbHistory> findDbHistoryByDbId(String dbId, String beforeTime) {
        return dbHistoryDao.findDbHistoryByDbId(dbId, beforeTime);
    }


    /**
     * 拼接监控历史数据
     * @param dbHistoryQuery 查询数据
     * @param data 查询的时间段   1:5分钟内、2:60分钟内、 3:720分钟内、4:其他的
     */

    public List<List<DbHistory>> joinMonitorHistoryData(DbHistoryQuery dbHistoryQuery,int data){
        List<List<DbHistory>> resList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();

        //根据id查询图形列表
        List<DbGraphics> graphicsList = dbGraphicsService.findDbGraphicsList(dbHistoryQuery.getDbId());

        //计算两个时间中间的时间点
        List<String> xTime = ConversionDateUtil.splitTime(dbHistoryQuery.getBeginTime(), dbHistoryQuery.getEndTime(), data);
        for (DbGraphics graphics : graphicsList) {
            //查询出每个图表下的监控项
            List<DbGraphicsMonitor> dbGraphicsMonitorList = dbGraphicsMonitorService.findMonitors(graphics.getId());

            List<DbHistory> list = new LinkedList<>();
            for (DbGraphicsMonitor graphicsMonitor : dbGraphicsMonitorList) {

                //通过监控项id查询监控历史记录
                dbHistoryQuery.setMonitorId(graphicsMonitor.getMonitorId());

                //通过监控项id和时间段查询历史
                int endName = TableUtil.getHistoryEndName(data);
                String dbTableName = TableUtil.getDbTableName(endName);
                List<DbHistory> dbHistories = dbHistoryDao.findHistoryByDbMonitorId(dbHistoryQuery,dbTableName);

                DbHistory dbHistory = new DbHistory();
                dbHistory.setGraphicsName(graphicsMonitor.getGraphicsName());
                if (!dbHistories.isEmpty()) {
                    dbHistories = dbHistories.stream().sorted(Comparator.comparing(DbHistory::getGatherTime))
                            .collect(Collectors.toList());

                    //监控项名字
                    dbHistory.setName(dbHistories.get(0).getMonitorName());

                    //设置告警信息
                    setTriggerService(dbHistories, dbHistory);

                    for (int i=0;i<dbHistories.size();i++){
                        String gatherTime = dbHistories.get(i).getGatherTime();
                        String reportData = dbHistories.get(i).getReportData();

                        if (i==0){
                            //查询的数据第一个时间不等于客户端传入的开始时间 添加内容为null
                            if (!dbHistoryQuery.getBeginTime().equals(gatherTime)){
                                timeList.add(dbHistoryQuery.getBeginTime());
                                dataList.add("null");
                            }else {
                                timeList.add(gatherTime);
                                dataList.add(reportData);
                            }
                        }
                        if (i==dbHistories.size()-1){
                            //查询的数据最后一个时间不等于客户端传入的结束时间 添加内容为null
                            if (!dbHistoryQuery.getEndTime().equals(gatherTime)){
                                timeList.add(dbHistoryQuery.getEndTime());
                                dataList.add("null");
                            }else {
                                timeList.add(gatherTime);
                                dataList.add(reportData);
                            }
                        }else {
                            timeList.add(gatherTime);
                            dataList.add(reportData);
                        }
                    }
                    //时间段内数据添加时间、数据
                    dbHistory.setData(dataList);
                    dbHistory.setDataTimes(timeList);
                } else {
                    dbHistory.setData(dataList);
                    dbHistory.setDataTimes(xTime);
                }
                list.add(dbHistory);
            }
            if (!list.isEmpty()) {
                resList.add(list);
            }
        }
        return resList;
    }


    //部分触发告警的数据变颜色,在前端提示出来
    private void setTriggerService(List<DbHistory> histories, DbHistory dbHistory) {
        //根据监控项查询监控项的阈值
        String expression = histories.get(0).getExpression();
        if (StringUtils.isBlank(expression)) {
            return;
        }
        //根据表达式查询触发器表达式
        List<DbTrigger> triggerList = dbTriggerService.findLikeTrigger(histories.get(0).getDbId(), expression);

        if (!triggerList.isEmpty()) {
            String triggerExe = triggerList.get(0).getExpression();
            if (StringUtils.isNotBlank(triggerExe)) {
                //遍历所有合适的触发器,将多条件的触发器进行拆解
                List<Map<String, String>> mapList = new LinkedList<>();
                for (DbTrigger trigger : triggerList) {
                    //触发器中有&&和||的匹配符,需要根据这两类进行分隔
                    if (StringUtils.isNotBlank(trigger.getExpression())) {
                        String[] conditions = trigger.getExpression().split("(&&|\\|\\|)");
                        for (String condition : conditions) {
                            if (condition.contains(expression)) {
                                Map<String, String> map = new HashMap<>();
                                if (StringUtils.isBlank(condition)) {
                                    // 添加空值检查
                                    return;
                                }

                                Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}");
                                Matcher matcher = pattern.matcher(condition);

                                while (matcher.find()) {
                                    map.put("expression", matcher.group(1));
                                }

                                Pattern pattern2 = Pattern.compile("\\[(.+?)]");
                                Matcher matcher2 = pattern2.matcher(condition);

                                while (matcher2.find()) {
                                    map.put("value", matcher2.group(1));
                                }

                                String substring = condition.substring(condition.indexOf("}}") + 2);
                                String substring1 = substring.substring(0, substring.indexOf("[")).trim();
                                map.put("operator", substring1);
                                map.put("problem", trigger.getDescribe());
                                mapList.add(map);
                            }
                        }
                    }
                }

                dbHistory.setMapList(mapList);
                //获取[]中的值
                String value1 = ConversionScriptsUtils.getValue(triggerExe);

                //获取表达式关系,>,>=,<,<=,=,!=
                String substring = triggerExe.substring(triggerExe.indexOf("}}") + 2);
                String substring1 = substring.substring(0, substring.indexOf("["));

                dbHistory.setReportData(value1);
                dbHistory.setExpression(substring1);
                dbHistory.setProblem(triggerList.get(0).getDescribe());
            }
        }
    }


    /**
     * 定义要插入表的字段,在插入数据的时候使用
     */
    private static List<Map<String, Object>> getMapList(List<DbHistory> entityList) {
        return entityList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            String uuid = RandomStringUtils.randomAlphanumeric(12);
            map.put("id", uuid);
            map.put("db_id", history.getDbId());
            map.put("db_monitor_id", history.getDbMonitorId());
            map.put("report_data", history.getReportData());
            map.put("gather_time", history.getGatherTime());
            return map;
        }).toList();
    }



}
