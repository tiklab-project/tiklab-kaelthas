package io.tiklab.kaelthas.db.history.service;

import com.mchange.lang.LongUtils;
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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
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
        String dbTableName = TableUtil.getDbTableName(LocalDate.now(),0);

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
        String dbTableName = TableUtil.getDbTableName(LocalDate.now(),0);
        List<DbHistory> historyList = dbHistoryDao.findDbHistoryByTime(beforeTime, dbTableName);
        return historyList;
    }


    @Override
    public List<DbHistory> findInHistoryByGatherTime(String dbId, String beforeTime,String expression) {
        return dbHistoryDao.findInHistoryByGatherTime(dbId, beforeTime,expression);
    }


    /**
     * 拼接监控历史数据
     * @param dbHistoryQuery 查询数据
     * @param data 查询的时间段   1:5分钟内、2:60分钟内、 3:720分钟内、4:其他的
     */

    public List<List<DbHistory>> joinMonitorHistoryData(DbHistoryQuery dbHistoryQuery,int data){
        List<List<DbHistory>> resList = new ArrayList<>();

        //客服端传入的开始时间
        String beginTime = dbHistoryQuery.getBeginTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalTime = LocalDateTime.parse(beginTime, formatter);


        //根据id查询图形列表
        List<DbGraphics> graphicsList = dbGraphicsService.findDbGraphicsList(dbHistoryQuery.getDbId());

        //计算两个时间中间的时间点
        //List<String> xTime = ConversionDateUtil.splitTime(dbHistoryQuery.getBeginTime(), dbHistoryQuery.getEndTime(), data);
        for (DbGraphics graphics : graphicsList) {
            //查询出每个图表下的监控项
            List<DbGraphicsMonitor> dbGraphicsMonitorList = dbGraphicsMonitorService.findMonitors(graphics.getId());

            List<DbHistory> list = new LinkedList<>();
            for (DbGraphicsMonitor graphicsMonitor : dbGraphicsMonitorList) {
                List<String> timeList = new ArrayList<>();
                List<String> dataList = new ArrayList<>();

                //通过监控项id查询监控历史记录
                dbHistoryQuery.setMonitorId(graphicsMonitor.getMonitorId());

                //通过监控项id和时间段查询历史
                int endName = TableUtil.getHistoryEndName(data);

                LocalDate localDate = LocalDate.parse(beginTime, formatter);
                String dbTableName = TableUtil.getDbTableName(localDate,endName);

                //根据对应查询时间段，向前添加时间
                addOneTime(dbHistoryQuery,data);

                DbHistory dbHistory = new DbHistory();
                //图形名称
                dbHistory.setGraphicsName(graphicsMonitor.getGraphicsName());
                //监控项名字
                dbHistory.setName(graphicsMonitor.getMonitorName());


                List<DbHistory> dbHistories = dbHistoryDao.findHistoryByDbMonitorId(dbHistoryQuery,dbTableName);

                if (!dbHistories.isEmpty()) {
                    dbHistories = dbHistories.stream().sorted(Comparator.comparing(DbHistory::getGatherTime))
                            .collect(Collectors.toList());


                    //设置告警信息
                    setTriggerService(dbHistories, dbHistory);

                    int number=0;
                    int addNum=0;
                    for (int i=0;i<dbHistories.size();i++){
                        DbHistory history = dbHistories.get(i);
                        String gatherTime = history.getGatherTime();
                        String reportData = history.getReportData();

                        //查询监控数据库的历史时间点 在客户端传入的时间前直接跳过
                        LocalDateTime localDateTime = LocalDateTime.parse(gatherTime, formatter);
                        if (localDateTime.isBefore(beginLocalTime)){
                            number+=1;
                            continue;
                        }

                        addNum+=1;
                        long l=0L;
                        if (number>0||i>0){
                            //当前数据减去前面历史记录的数据
                            String befReportData = dbHistories.get(i-1).getReportData();
                            if (!("null").equals(reportData)&&!("null").equals(befReportData)){
                                l = Long.parseLong(reportData) - Long.parseLong(befReportData);
                            }
                        }

                        if (i==dbHistories.size()-1){
                            //查询的数据最后一个时间不等于客户端传入的结束时间 添加内容为null
                            if (!dbHistoryQuery.getEndTime().equals(gatherTime)){
                                timeList.add(dbHistoryQuery.getEndTime());
                                dataList.add("0");
                            }else {
                                timeList.add(gatherTime);
                                dataList.add(String.valueOf(l));
                            }
                        }else {
                            if (i==0&&number==0){
                                //查询的数据第一个时间不等于客户端传入的开始时间 添加内容为null
                                if (!beginTime.equals(gatherTime)){
                                    timeList.add(beginTime);
                                    dataList.add("0");
                                }else {
                                    timeList.add(gatherTime);
                                    dataList.add(reportData);
                                }
                            }else {
                                // 第一次执行且i!=0的时候 开始时间取beginTime
                               if (addNum==1){
                                   gatherTime=beginTime;
                               }
                                timeList.add(gatherTime);
                                dataList.add(String.valueOf(l));
                            }
                        }

                     /*   if (i==0){
                            //查询的数据第一个时间不等于客户端传入的开始时间 添加内容为null
                            if (!beginTime.equals(gatherTime)){
                                timeList.add(dbHistoryQuery.getBeginTime());
                                dataList.add("0");
                            }else {
                                timeList.add(gatherTime);
                                dataList.add(reportData);
                            }
                        }else if (i==dbHistories.size()-1){
                            //查询的数据最后一个时间不等于客户端传入的结束时间 添加内容为null
                            if (!dbHistoryQuery.getEndTime().equals(gatherTime)){
                                timeList.add(dbHistoryQuery.getEndTime());
                                dataList.add("0");
                            }else {
                                timeList.add(gatherTime);
                                dataList.add(String.valueOf(l));
                            }
                        }else {
                            timeList.add(gatherTime);
                            dataList.add(String.valueOf(l));
                        }*/
                    }
                    //时间段内数据添加时间、数据
                    dbHistory.setData(dataList);
                    dbHistory.setDataTimes(timeList);
                } else {
                    //没有历史记录数据,只需要添加开始时间和结束时间
                    dataList.add("null");
                    dataList.add("null");
                    timeList.add(dbHistoryQuery.getBeginTime());
                    timeList.add(dbHistoryQuery.getEndTime());
                    dbHistory.setData(dataList);
                    dbHistory.setDataTimes(timeList);
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
        }).collect(Collectors.toList());


    }


    /**
     * 开始时间 向前添加时间
     * @param dbHistoryQuery 数据库历史
     * @param data 查询的时间段   1:5分钟内、2:60分钟内、 3:720分钟内、4:其他的
     */
    public String addOneTime(DbHistoryQuery dbHistoryQuery,int data){
        String beginTime = dbHistoryQuery.getBeginTime();
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将字符串转换为 LocalDateTime
        LocalDateTime now = LocalDateTime.parse(beginTime, formatter);
        LocalDateTime localDateTime;
        switch (data){
            case 1,2:
                 localDateTime = now.minusMinutes(1);
                break;
            case 3:
                localDateTime = now.minusMinutes(5);
                break;
            default:
                localDateTime = now.minusMinutes(15);
                break;
        }
        String format = localDateTime.format(formatter);
        dbHistoryQuery.setBeginTime(format);
        return format;
    }



}
