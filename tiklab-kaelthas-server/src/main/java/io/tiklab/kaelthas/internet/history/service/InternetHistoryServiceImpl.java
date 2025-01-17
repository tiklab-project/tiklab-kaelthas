package io.tiklab.kaelthas.internet.history.service;

import io.tiklab.kaelthas.internet.graphics.model.InternetGraphics;
import io.tiklab.kaelthas.internet.graphics.service.InternetGraphicsService;
import io.tiklab.kaelthas.internet.graphicsMonitor.model.InGraphicsMonitor;
import io.tiklab.kaelthas.internet.history.dao.InternetHistoryDao;
import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.internet.history.model.InternetHistoryQuery;
import io.tiklab.kaelthas.internet.trigger.model.InTrigger;
import io.tiklab.kaelthas.internet.trigger.service.InTriggerService;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.util.ConversionScriptsUtils;
import io.tiklab.kaelthas.util.SqlUtil;
import io.tiklab.kaelthas.util.TableUtil;
import org.apache.commons.collections.CollectionUtils;
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
public class InternetHistoryServiceImpl implements InternetHistoryService {

    @Autowired
    InternetHistoryDao internetHistoryDao;

    @Autowired
    InternetGraphicsService internetGraphicsService;

    @Autowired
    InTriggerService inTriggerService;

    @Override
    public void insertForList(List<InternetHistory> entityList) {

        //获取网络历史表名
        String internetTableName = TableUtil.getInternetTableName(0);

        List<Map<String, Object>> mapList = getMapList(entityList);
        String historySql = SqlUtil.getBatchInsertSql(internetTableName, mapList);
        internetHistoryDao.insertHistoryList(historySql);

        List<InternetHistory> list1 = new ArrayList<>();
        List<InternetHistory> list5 = new ArrayList<>();
        List<InternetHistory> list15 = new ArrayList<>();

        for (InternetHistory information : entityList) {
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
            String historySql1 = SqlUtil.getBatchInsertSql(internetTableName+"_1", mapList1);
            internetHistoryDao.insertHistoryList(historySql1);
        }

        if (!list5.isEmpty()) {
            List<Map<String, Object>> mapList5 = getMapList(list5);
            String historySql5 = SqlUtil.getBatchInsertSql(internetTableName+"_5", mapList5);
            internetHistoryDao.insertHistoryList(historySql5);
        }

        if (!list15.isEmpty()) {
            List<Map<String, Object>> mapList15 = getMapList(list15);
            String historySql15 = SqlUtil.getBatchInsertSql(internetTableName+"_15", mapList15);
            internetHistoryDao.insertHistoryList(historySql15);
        }
    }

    @Override
    public List<List<InternetHistory>> findGraphicsLine(InternetHistoryQuery internetHistoryQuery) {
        List<List<InternetHistory>> resultList;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime beginLocalTime = LocalDateTime.parse(internetHistoryQuery.getBeginTime(), dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(internetHistoryQuery.getEndTime(), dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();

        if (minutes <= 5) {
            resultList = joinMonitorHistoryData(internetHistoryQuery, 1);
        }else if (minutes <= 60) {
            resultList = joinMonitorHistoryData(internetHistoryQuery, 2);
        }else if (minutes <= 720) {
            resultList = joinMonitorHistoryData(internetHistoryQuery, 3);
        }else {
            resultList = joinMonitorHistoryData(internetHistoryQuery, 4);
        }

        return resultList;
    }

    @Override
    public List<InternetHistory> findInHistoryByTime(String beforeTime) {
        String tableName = TableUtil.getInternetTableName(0);
        List<InternetHistory> inHistoryList = internetHistoryDao.findInHistoryByTime(beforeTime, tableName);
        return inHistoryList;
    }


    /**
     * 拼接监控历史数据
     * @param internetHistoryQuery 查询数据
     * @param data 查询的时间段   1:5分钟内、2:60分钟内、 3:720分钟内、4:其他的
     */
    public List<List<InternetHistory>> joinMonitorHistoryData(InternetHistoryQuery internetHistoryQuery, int data){
        List<List<InternetHistory>> resList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();


        //根据主机的id查询主机当中的图表
        List<InternetGraphics> graphicsList = internetGraphicsService.findGraphicsList(internetHistoryQuery.getInternetId());

        //计算两个时间中间的时间点
        List<String> xTime = ConversionDateUtil.splitTime(internetHistoryQuery.getBeginTime(), internetHistoryQuery.getEndTime(), data);
        //2.根据查询出的图表,分别查询出图表中对应的监控项数据
        for (InternetGraphics graphics : graphicsList) {
            //查询出每个图表下的监控项
            List<InGraphicsMonitor> graphicsMonitors = internetGraphicsService.findGraphicsMonitors(graphics.getId());
            if (graphicsMonitors.isEmpty()) {
                continue;
            }
            //根据监控项的ids查询监控项的数据
            //查询每条监控项的数据集合
            List<InternetHistory> list = new LinkedList<>();
            for (InGraphicsMonitor graphicsMonitor : graphicsMonitors) {

                //通过监控项id查询监控历史记录
                internetHistoryQuery.setInternetMonitorId(graphicsMonitor.getMonitorId());

                //通过监控项id和时间段查询历史
                int endName = TableUtil.getHistoryEndName(data);
                String dbTableName = TableUtil.getInternetTableName(endName);
                List<InternetHistory> internetHistories = internetHistoryDao.findInternetHistoryByInMonitorId(internetHistoryQuery, dbTableName);

                InternetHistory internetHistory = new InternetHistory();
                internetHistory.setGraphicsName(graphicsMonitor.getGraphicsName());

                if (!internetHistories.isEmpty()) {
                    internetHistories = internetHistories.stream().sorted(Comparator.comparing(InternetHistory::getGatherTime))
                            .collect(Collectors.toList());

                    //监控项名字
                    internetHistory.setName(internetHistories.get(0).getMonitorName());
                    //设置告警信息
                    InternetColorLine(internetHistories,internetHistory);

                    //循环添加时间、数据
                    for (int i=0;i<internetHistories.size();i++){
                        String gatherTime = internetHistories.get(i).getGatherTime();
                        String reportData = internetHistories.get(i).getReportData();

                        if (i==0){
                            //查询的数据第一个时间不等于客户端传入的开始时间 添加内容为null
                            if (!internetHistoryQuery.getBeginTime().equals(gatherTime)){
                                timeList.add(internetHistoryQuery.getBeginTime());
                                dataList.add("null");
                            }else {
                                timeList.add(gatherTime);
                                dataList.add(reportData);
                            }
                        }
                        if (i==internetHistories.size()-1){
                            //查询的数据最后一个时间不等于客户端传入的结束时间 添加内容为null
                            if (!internetHistoryQuery.getEndTime().equals(gatherTime)){
                                timeList.add(internetHistoryQuery.getEndTime());
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
                    internetHistory.setData(dataList);
                    internetHistory.setDataTimes(timeList);
                }else {
                    internetHistory.setData(dataList);
                    internetHistory.setDataTimes(xTime);
                }
                if (StringUtils.isNotBlank(internetHistory.getName())) {
                    list.add(internetHistory);
                }
            }
            if (!list.isEmpty()) {
                resList.add(list);
            }
        }
        return resList;
    }

    //部分触发告警的数据变颜色,在前端提示出来
    private void InternetColorLine(List<InternetHistory> internetHistoryList, InternetHistory internetHistory) {
        //根据监控项查询监控项的阈值
        String expression = internetHistoryList.get(0).getExpression();
        if (StringUtils.isBlank(expression)) {
            return;
        }
        //根据监控项表达式查询触发器表达式
        List<InTrigger> triggerList = inTriggerService.findLikeTrigger(internetHistoryList.get(0).getInternetId(), expression);

        if (!triggerList.isEmpty()) {
            String triggerExe = triggerList.get(0).getExpression();
            if (StringUtils.isNotBlank(triggerExe)) {
                //遍历所有合适的触发器,将多条件的触发器进行拆解
                List<Map<String, String>> mapList = new LinkedList<>();
                for (InTrigger trigger : triggerList) {
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

                internetHistory.setMapList(mapList);
                //获取[]中的值
                String value1 = ConversionScriptsUtils.getValue(triggerExe);

                //获取表达式关系,>,>=,<,<=,=,!=
                String substring = triggerExe.substring(triggerExe.indexOf("}}") + 2);
                String substring1 = substring.substring(0, substring.indexOf("["));

                internetHistory.setReportData(value1);
                internetHistory.setExpression(substring1);
                internetHistory.setProblem(triggerList.get(0).getDescribe());
            }
        }
    }

    /**
     * 定义要插入表的字段,在插入数据的时候使用
     */
    private static List<Map<String, Object>> getMapList(List<InternetHistory> entityList) {
        return entityList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            String uuid = RandomStringUtils.randomAlphanumeric(12);
            map.put("id", uuid);
            map.put("internet_id", history.getInternetId());
            map.put("internet_monitor_id", history.getInternetMonitorId());
            map.put("report_data", history.getReportData());
            map.put("gather_time", history.getGatherTime());
            return map;
        }).toList();
    }
}
