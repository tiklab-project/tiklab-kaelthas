package io.tiklab.kaelthas.host.history.service;

import io.tiklab.kaelthas.host.graphics.model.Graphics;
import io.tiklab.kaelthas.host.graphics.service.GraphicsService;
import io.tiklab.kaelthas.host.graphicsMonitor.model.GraphicsMonitor;
import io.tiklab.kaelthas.host.graphicsMonitor.service.GraphicsMonitorService;
import io.tiklab.kaelthas.host.history.dao.HostHistoryDao;
import io.tiklab.kaelthas.host.history.model.HostHistory;
import io.tiklab.kaelthas.host.history.model.HostHistoryQuery;
import io.tiklab.kaelthas.host.trigger.model.Trigger;
import io.tiklab.kaelthas.host.trigger.service.TriggerService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class HostHistoryServiceImpl implements HostHistoryService {

    @Autowired
    HostHistoryDao hostHistoryDao;

    @Autowired
    GraphicsService graphicsService;

    @Autowired
    GraphicsMonitorService graphicsMonitorService;

    @Autowired
    TriggerService triggerService;

    @Override
    public void insertForList(List<HostHistory> entityList) {
        //获取数据库表名
        String dbTableName = TableUtil.getHostTableName(LocalDate.now(),0);

        List<Map<String, Object>> mapList = getMapList(entityList);
        String historySql = SqlUtil.getBatchInsertSql(dbTableName, mapList);
        hostHistoryDao.insertHistoryList(historySql);

        List<HostHistory> list1 = new ArrayList<>();
        List<HostHistory> list5 = new ArrayList<>();
        List<HostHistory> list15 = new ArrayList<>();

        for (HostHistory information : entityList) {
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
            hostHistoryDao.insertHistoryList(historySql1);
        }

        if (!list5.isEmpty()) {
            List<Map<String, Object>> mapList5 = getMapList(list5);
            String historySql5 = SqlUtil.getBatchInsertSql(dbTableName+"_5", mapList5);
            hostHistoryDao.insertHistoryList(historySql5);
        }

        if (!list15.isEmpty()) {
            List<Map<String, Object>> mapList15 = getMapList(list15);
            String historySql15 = SqlUtil.getBatchInsertSql(dbTableName+"_15", mapList15);
            hostHistoryDao.insertHistoryList(historySql15);
        }

    }

    @Override
    public List<List<HostHistory>> findInformationByGraphics(HostHistoryQuery hostHistoryQuery) {
        List<List<HostHistory>> resultList;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalTime = LocalDateTime.parse(hostHistoryQuery.getBeginTime(), dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(hostHistoryQuery.getEndTime(), dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();

        if (minutes <= 5) {
            resultList = joinMonitorHistoryData(hostHistoryQuery, 1);
        }else if (minutes <= 60) {
            resultList = joinMonitorHistoryData(hostHistoryQuery, 2);
        }else if (minutes <= 720) {
            resultList = joinMonitorHistoryData(hostHistoryQuery, 3);
        }else {
            resultList = joinMonitorHistoryData(hostHistoryQuery, 4);
        }

        return resultList;
    }

    @Override
    public List<HostHistory> findHostHistoryByTime(String beforeTime) {
        String dbTableName = TableUtil.getHostTableName(LocalDate.now(),0);
        List<HostHistory> hostHistoryList = hostHistoryDao.findHostHistoryByTime(beforeTime, dbTableName);
        return hostHistoryList;
    }

    /**
     * 根据主机监控的id和指定的时间后查询存储数据
     */
    @Override
    public List<HostHistory> findByHostTrigger(String hostId, String beforeTime,String expression) {
        return hostHistoryDao.findByHostTrigger(hostId, beforeTime,expression);
    }


    /**
     * 拼接监控历史数据
     * @param hostHistoryQuery 查询数据
     * @param data 查询的时间段   1:5分钟内、2:60分钟内、 3:720分钟内、4:其他的
     */

    public List<List<HostHistory>> joinMonitorHistoryData(HostHistoryQuery hostHistoryQuery, int data){

        List<List<HostHistory>> resList = new ArrayList<>();

        //根据主机的id查询主机当中的图表
        List<Graphics> graphicsList = graphicsService.findInformationByGraphics(hostHistoryQuery.getHostId());
       // List<String> xTime = ConversionDateUtil.splitTime(hostHistoryQuery.getBeginTime(), hostHistoryQuery.getEndTime(), data);
        //2.根据查询出的图表,分别查询出图表中对应的监控项数据
        for (Graphics graphics : graphicsList) {

            //查询出每个图表下的监控项
            List<GraphicsMonitor> byGraphics = graphicsMonitorService.findByGraphics(graphics.getId());
            //根据监控项的ids查询监控项的数据
            //查询每条监控项的数据集合
            List<HostHistory> list = new LinkedList<>();
            for (GraphicsMonitor graphicsMonitor : byGraphics) {
                List<String> dataList = new ArrayList<>();
                List<String> timeList = new ArrayList<>();

                //通过监控项id查询监控历史记录
                hostHistoryQuery.setHostMonitorId(graphicsMonitor.getMonitorId());

                //通过监控项id和时间段查询历史
                int endName = TableUtil.getHistoryEndName(data);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDate localDate = LocalDate.parse(hostHistoryQuery.getBeginTime(), formatter);
                String dbTableName = TableUtil.getHostTableName(localDate,endName);
                List<HostHistory> hostHistoryList = hostHistoryDao.findHistoryByMonitorId(hostHistoryQuery, dbTableName);

                HostHistory hostHistory = new HostHistory();
                //图形名称
                hostHistory.setGraphicsName(graphicsMonitor.getGraphicsName());
                //监控项名字
                hostHistory.setName(graphicsMonitor.getMonitorName());
                if (!hostHistoryList.isEmpty()) {

                    // 根据 GatherTime 去重
                     hostHistoryList = hostHistoryList.stream()
                            .collect(Collectors.toMap(
                                    HostHistory::getGatherTime,  // 使用 GatherTime 作为键
                                    HostHistory -> HostHistory,  // 使用整个 Person 对象作为值
                                    (existing, replacement) -> existing  // 保留第一个出现的对象
                            ))
                            .values()
                            .stream().collect(Collectors.toList());

                    hostHistoryList = hostHistoryList.stream().sorted(Comparator.comparing(HostHistory::getGatherTime))
                            .collect(Collectors.toList());


                    //设置告警信息
                    setThreshold(hostHistoryList,hostHistory);

                    for (int i=0;i<hostHistoryList.size();i++){
                        String gatherTime = hostHistoryList.get(i).getGatherTime();
                        String reportData = hostHistoryList.get(i).getReportData();

                        if (i==0){
                            //查询的数据第一个时间不等于客户端传入的开始时间 添加内容为null
                            if (!hostHistoryQuery.getBeginTime().equals(gatherTime)){
                                timeList.add(hostHistoryQuery.getBeginTime());
                                dataList.add("null");
                            }else {
                                timeList.add(gatherTime);
                                dataList.add(reportData);
                            }
                        }else if (i==hostHistoryList.size()-1){
                            //查询的数据最后一个时间不等于客户端传入的结束时间 添加内容为null
                            if (!hostHistoryQuery.getEndTime().equals(gatherTime)){
                                timeList.add(hostHistoryQuery.getEndTime());
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
                    hostHistory.setData(dataList);
                    hostHistory.setDataTimes(timeList);
                }else {
                    //没有历史记录数据,只需要添加开始时间和结束时间
                    dataList.add("null");
                    dataList.add("null");
                    timeList.add(hostHistoryQuery.getBeginTime());
                    timeList.add(hostHistoryQuery.getEndTime());
                    hostHistory.setData(dataList);
                    hostHistory.setDataTimes(timeList);
                }

                if (StringUtils.isNotBlank(hostHistory.getName())) {
                    list.add(hostHistory);
                }
            }
            if (!list.isEmpty()) {
                resList.add(list);
            }
        }
        return resList;
    }


    //部分触发告警的数据变颜色,在前端提示出来
    private void setThreshold(List<HostHistory> hostHistoryList, HostHistory hostHistory) {
        //根据监控项查询监控项的阈值
        String expression = hostHistoryList.get(0).getExpression();
        if (StringUtils.isBlank(expression)) {
            return;
        }
        //根据表达式查询触发器表达式
        List<Trigger> triggerList = triggerService.findLikeTrigger(hostHistoryList.get(0).getHostId(), expression);

        if (!triggerList.isEmpty()) {
            String triggerExe = triggerList.get(0).getExpression();
            if (StringUtils.isNotBlank(triggerExe)) {
                //遍历所有合适的触发器,将多条件的触发器进行拆解
                List<Map<String, String>> mapList = new LinkedList<>();
                for (Trigger trigger : triggerList) {
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

                hostHistory.setMapList(mapList);
                //获取[]中的值
                String value1 = ConversionScriptsUtils.getValue(triggerExe);

                //获取表达式关系,>,>=,<,<=,=,!=
                String substring = triggerExe.substring(triggerExe.indexOf("}}") + 2);
                String substring1 = substring.substring(0, substring.indexOf("["));

                hostHistory.setReportData(value1);
                hostHistory.setExpression(substring1);
                hostHistory.setProblem(triggerList.get(0).getDescribe());
            }
        }
    }


    /**
     * 定义要插入表的字段,在插入数据的时候使用
     */
    private static List<Map<String, Object>> getMapList(List<HostHistory> entityList) {
        return entityList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            String uuid = RandomStringUtils.randomAlphanumeric(12);
            map.put("id", uuid);
            map.put("host_id", history.getHostId());
            map.put("host_monitor_id", history.getMonitorId());
            map.put("report_data", history.getReportData());
            map.put("gather_time", history.getGatherTime());
            return map;
        }).collect(Collectors.toList());
    }


}
