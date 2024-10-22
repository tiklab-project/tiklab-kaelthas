package io.tiklab.kaelthas.history.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.history.dao.HistoryDao;
import io.tiklab.kaelthas.history.dao.HistoryMultiDao;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import io.tiklab.kaelthas.db.dbGraphics.model.DbGraphics;
import io.tiklab.kaelthas.db.dbGraphics.service.DbGraphicsService;
import io.tiklab.kaelthas.db.dbGraphicsMonitor.model.DbGraphicsMonitor;
import io.tiklab.kaelthas.db.dbGraphicsMonitor.service.DbGraphicsMonitorService;
import io.tiklab.kaelthas.common.javascripts.ConversionScriptsUtils;
import io.tiklab.kaelthas.db.dbTrigger.model.DbTrigger;
import io.tiklab.kaelthas.db.dbTrigger.service.DbTriggerService;
import io.tiklab.kaelthas.host.graphics.model.Graphics;
import io.tiklab.kaelthas.host.graphics.service.GraphicsService;
import io.tiklab.kaelthas.host.graphicsMonitor.model.GraphicsMonitor;
import io.tiklab.kaelthas.host.graphicsMonitor.service.GraphicsMonitorService;
import io.tiklab.kaelthas.history.entity.HistoryEntity;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.common.util.SqlUtil;
import io.tiklab.kaelthas.host.trigger.model.Trigger;
import io.tiklab.kaelthas.host.trigger.service.TriggerService;
import io.tiklab.kaelthas.common.util.ConversionDateUtil;
import io.tiklab.kaelthas.internet.internetGraphics.model.InternetGraphics;
import io.tiklab.kaelthas.internet.internetGraphics.service.InternetGraphicsService;
import io.tiklab.kaelthas.internet.internetGraphicsMonitor.model.InGraphicsMonitor;
import io.tiklab.kaelthas.internet.internetTrigger.model.InTrigger;
import io.tiklab.kaelthas.internet.internetTrigger.service.InTriggerService;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.service.KuGraphicsService;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.model.KuGraphicsMonitor;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.service.KuGraphicsMonitorService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryDao historyDao;

    @Autowired
    HistoryMultiDao historyMultiDao;

    @Autowired
    HostService hostService;

    @Autowired
    TriggerService triggerService;

    @Autowired
    GraphicsMonitorService graphicsMonitorService;

    @Autowired
    GraphicsService graphicsService;

    @Autowired
    private DbGraphicsService dbGraphicsService;

    @Autowired
    private DbGraphicsMonitorService dbGraphicsMonitorService;

    @Autowired
    private DbTriggerService dbTriggerService;

    @Autowired
    private DbInfoService dbInfoService;

    @Autowired
    private KuGraphicsService kuGraphicsService;

    @Autowired
    private KuGraphicsMonitorService kuGraphicsMonitorService;

    @Autowired
    private InternetGraphicsService internetGraphicsService;

    @Autowired
    private InTriggerService inTriggerService;

    @Override
    public Pagination<History> findInformationPage(History history) {
        Pagination<History> pagination = historyDao.findInformationPage(history);
        return pagination;
    }

    @Override
    public List<List<History>> findInformationByGraphics(History history) {
        //1.根据主机的id查询主机当中的图表
        List<Graphics> graphicsList = graphicsService.findInformationByGraphics(history.getHostId());

        List<List<History>> resList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime beginLocalTime = LocalDateTime.parse(history.getBeginTime(), dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(history.getEndTime(), dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();

        if (minutes <= 5) {

            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 1);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (Graphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<GraphicsMonitor> byGraphics = graphicsMonitorService.findByGraphics(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (GraphicsMonitor graphicsMonitor : byGraphics) {
                    List<History> reportList = new LinkedList<>();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    History history1 = new History();
                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    List<History> historyList = historyDao.findHistoryByMonitorId(history);
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        setThreshold(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }

                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else if (minutes <= 60) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 2);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (Graphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<GraphicsMonitor> byGraphics = graphicsMonitorService.findByGraphics(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (GraphicsMonitor graphicsMonitor : byGraphics) {
                    List<History> reportList = new LinkedList<>();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    History history1 = new History();
                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    List<History> historyList = historyMultiDao.findHistoryByOneTime(history);
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        setThreshold(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }

                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);

                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else if (minutes <= 720) {

            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 3);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (Graphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<GraphicsMonitor> byGraphics = graphicsMonitorService.findByGraphics(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (GraphicsMonitor graphicsMonitor : byGraphics) {
                    List<History> reportList = new LinkedList<>();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    History history1 = new History();
                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    List<History> historyList = historyMultiDao.findHistoryByFiveTime(history);
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        setThreshold(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }

                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else {

            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 4);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (Graphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<GraphicsMonitor> byGraphics = graphicsMonitorService.findByGraphics(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (GraphicsMonitor graphicsMonitor : byGraphics) {
                    List<History> reportList = new LinkedList<>();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    History history1 = new History();
                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    List<History> historyList = historyMultiDao.findHistoryByFifteenTime(history);
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        setThreshold(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }


                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        }
        return resList;
    }

    private void setThreshold(List<History> histories, History information) {

        //根据监控项查询监控项的阈值
        String expression = histories.get(0).getExpression();
        if (StringUtils.isBlank(expression)) {
            return;
        }
        //根据表达式查询触发器表达式
        List<Trigger> triggerList = triggerService.findLikeTrigger(histories.get(0).getHostId(), expression);

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

                information.setMapList(mapList);
                //获取[]中的值
                String value1 = ConversionScriptsUtils.getValue(triggerExe);

                //获取表达式关系,>,>=,<,<=,=,!=
                String substring = triggerExe.substring(triggerExe.indexOf("}}") + 2);
                String substring1 = substring.substring(0, substring.indexOf("["));

                information.setReportData(value1);
                information.setExpression(substring1);
                information.setProblem(triggerList.get(0).getDescribe());
            }
        }
    }

    private static String getEndTime(History history, List<History> historyList) {
        String endTime = history.getEndTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<String> stringList = historyList.stream().map(History::getGatherTime).distinct().toList();

        for (int i = stringList.size() - 1; i >= 0; i--) {
            try {
                Date parse = dateFormat.parse(history.getEndTime());
                Date parse1 = dateFormat.parse(stringList.get(i));
                if (parse1.getTime() < parse.getTime()) {
                    endTime = stringList.get(i);
                    break;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return endTime;
    }

    @Override
    public History findInformationByLine(History history) {

        List<History> informationByLine = historyDao.findInformationByLine(history);

        History history1 = new History();

        if (!informationByLine.isEmpty()) {
            List<String> stringList = informationByLine.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();

            List<String> times = informationByLine.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getGatherTime).toList();

            history1.setName(informationByLine.get(0).getMonitorName());
            history1.setData(stringList);
            history1.setDataTimes(times);
        }

        return history1;
    }

    @Override
    public void insertForList(List<History> entityList) {
        List<Map<String, Object>> mapList = getMapList(entityList);
        String historySql = SqlUtil.getBatchInsertSql("mtc_history", mapList);

        historyDao.insertHistoryList(historySql);
        List<History> list1 = new ArrayList<>();
        List<History> list5 = new ArrayList<>();
        List<History> list15 = new ArrayList<>();

        for (History information : entityList) {
            String gatherTime = information.getGatherTime();

            //判断是否能插入到一分钟的表当中
            boolean divisible = ConversionDateUtil.isDivisible(gatherTime, 1);
            if (divisible) {
                list1.add(information);
            }

            boolean divisible1 = ConversionDateUtil.isDivisible(gatherTime, 5);
            if (divisible1) {
                list5.add(information);
            }

            boolean divisible2 = ConversionDateUtil.isDivisible(gatherTime, 15);
            if (divisible2) {
                list15.add(information);
            }
        }

        if (!list1.isEmpty()) {
            List<Map<String, Object>> mapList1 = getMapList(list1);
            String historySql1 = SqlUtil.getBatchInsertSql("mtc_history_one_minute", mapList1);
            historyMultiDao.insertOneForList(historySql1);
        }

        if (!list5.isEmpty()) {
            List<Map<String, Object>> mapList5 = getMapList(list5);
            String historySql5 = SqlUtil.getBatchInsertSql("mtc_history_five_minute", mapList5);
            historyMultiDao.insertFiveForList(historySql5);
        }

        if (!list15.isEmpty()) {
            List<Map<String, Object>> mapList15 = getMapList(list15);
            String historySql15 = SqlUtil.getBatchInsertSql("mtc_history_fifteen_minute", mapList15);
            historyMultiDao.insertFifteenForList(historySql15);
        }


    }

    private static List<Map<String, Object>> getMapList(List<History> entityList) {
        return entityList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            String uuid = RandomStringUtils.randomAlphanumeric(12);
            map.put("id", uuid);
            map.put("host_id", history.getHostId());
            map.put("monitor_id", history.getMonitorId());
            map.put("report_data", history.getReportData());
            map.put("gather_time", history.getGatherTime());
            return map;
        }).toList();
    }

    /**
     * 根据主机id和监控项id查询上报的数据
     */
    @Override
    public List<History> findInformationToGatherTime(String hostId, String beforeDateTime) {
        return historyDao.findInformationToGatherTime(hostId, beforeDateTime);
    }

    @Override
    public void deleteByCondition(History history) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(HistoryEntity.class)
                .eq("monitorId", history.getMonitorId())
                .get();
        historyDao.deleteByCondition(deleteCondition);
    }

    /**
     * 查询历史数据表当中有没有此主机的数据
     */
    @Override
    public void findHistoryByCondition(History history) {

        String hostId = history.getHostId();
        //查询最近五分钟有没有这个主机的数据,如果没有的话就说明这个主机没有数据上报,修改状态为不可用
        String beforeTime = ConversionDateUtil.findLocalDateTime(2, 5, null);
        String nowTime = ConversionDateUtil.findLocalDateTime(2, 0, null);
        List<History> histories = historyDao.findHistoryByCondition(history, beforeTime, nowTime);

        if (histories.isEmpty()) {
            //修改主机状态为2
            hostService.updateHostStatus(hostId, 2);
        } else {
            hostService.updateHostStatus(hostId, 1);
        }
    }

    @Override
    public List<List<History>> findGraphicsLine(History history) {
        //根据id查询图形列表
        List<DbGraphics> graphicsList = dbGraphicsService.findDbGraphicsList(history.getHostId());

        List<List<History>> resList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalTime = LocalDateTime.parse(history.getBeginTime(), dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(history.getEndTime(), dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();

        if (minutes <= 5) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 1);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (DbGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<DbGraphicsMonitor> dbGraphicsMonitorList = dbGraphicsMonitorService.findMonitors(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (DbGraphicsMonitor graphicsMonitor : dbGraphicsMonitorList) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyDao.findHistoryByDbMonitorId(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        setTriggerService(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }

                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else if (minutes <= 60) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 2);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (DbGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<DbGraphicsMonitor> dbGraphicsMonitorList = dbGraphicsMonitorService.findMonitors(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (DbGraphicsMonitor graphicsMonitor : dbGraphicsMonitorList) {
                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findDbHistoryByOneTime(history);
                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());

                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        setTriggerService(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }

                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else if (minutes <= 720) {

            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 3);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (DbGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<DbGraphicsMonitor> dbGraphicsMonitorList = dbGraphicsMonitorService.findMonitors(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (DbGraphicsMonitor graphicsMonitor : dbGraphicsMonitorList) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findDbHistoryByFiveTime(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        setTriggerService(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    }

                    List<String> stringList2 = reportList
                            .stream().sorted(Comparator.comparing(History::getGatherTime))
                            .map(History::getReportData).toList();

                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else {

            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 4);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (DbGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<DbGraphicsMonitor> dbGraphicsMonitorList = dbGraphicsMonitorService.findMonitors(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (DbGraphicsMonitor graphicsMonitor : dbGraphicsMonitorList) {
                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();

                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findDbHistoryByFifteenTime(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());

                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        setTriggerService(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }

                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        }
        return resList;
    }

    @Override
    public List<List<History>> findKuGraphicsLine(History history) {
        //根据id查询图形列表
        List<KuGraphics> graphicsList = kuGraphicsService.findKuGraphicsList(history.getHostId());

        List<List<History>> resList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalTime = LocalDateTime.parse(history.getBeginTime(), dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(history.getEndTime(), dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();

        if (minutes <= 5) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 1);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (KuGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<KuGraphicsMonitor> graphicsMonitors = kuGraphicsMonitorService.findGraphicsMonitors(graphics.getId());
                if (graphicsMonitors.isEmpty()) {
                    continue;
                }
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (KuGraphicsMonitor graphicsMonitor : graphicsMonitors) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyDao.findHistoryByKuMonitorId(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
//                        setTriggerService(historyList, history1);
                    }

                    for (String string : xTime) {
                        History history3 = new History();
                        history3.setGatherTime(string);
                        history3.setReportData("null");
                        for (History history2 : historyList) {
                            if (string.equals(history2.getGatherTime())) {
                                history3.setReportData(history2.getReportData());
                            }
                        }
                        reportList.add(history3);
                    }
                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else if (minutes <= 60) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 2);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (KuGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<KuGraphicsMonitor> graphicsMonitors = kuGraphicsMonitorService.findGraphicsMonitors(graphics.getId());
                if (graphicsMonitors.isEmpty()) {
                    continue;
                }
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (KuGraphicsMonitor graphicsMonitor : graphicsMonitors) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findKuHistoryByOneTime(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
//                        setTriggerService(historyList, history1);
                    }

                    for (String string : xTime) {
                        History history3 = new History();
                        history3.setGatherTime(string);
                        history3.setReportData("null");
                        for (History history2 : historyList) {
                            if (string.equals(history2.getGatherTime())) {
                                history3.setReportData(history2.getReportData());
                            }
                        }
                        reportList.add(history3);
                    }
                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else if (minutes <= 720) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 3);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (KuGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<KuGraphicsMonitor> graphicsMonitors = kuGraphicsMonitorService.findGraphicsMonitors(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();

                for (KuGraphicsMonitor graphicsMonitor : graphicsMonitors) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findKuHistoryByFiveTime(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());

                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
//                        setTriggerService(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }

                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else {

            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 4);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (KuGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<KuGraphicsMonitor> graphicsMonitorList = kuGraphicsMonitorService.findGraphicsMonitors(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();

                for (KuGraphicsMonitor kuGraphicsMonitor : graphicsMonitorList) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(kuGraphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findKuHistoryByFifteenTime(history);

                    history1.setGraphicsName(kuGraphicsMonitor.getGraphicsName());
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
//                        setTriggerService(historyList, history1);
                    }

                    for (String string : xTime) {
                        History history3 = new History();
                        history3.setGatherTime(string);
                        history3.setReportData("null");
                        for (History history2 : historyList) {
                            if (string.equals(history2.getGatherTime())) {
                                history3.setReportData(history2.getReportData());
                            }
                        }
                        reportList.add(history3);
                    }
                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        }
        return resList;
    }

    //部分触发告警的数据变颜色,在前端提示出来
    private void setTriggerService(List<History> histories, History information) {
        //根据监控项查询监控项的阈值
        String expression = histories.get(0).getExpression();
        if (StringUtils.isBlank(expression)) {
            return;
        }
        //根据表达式查询触发器表达式
        List<DbTrigger> triggerList = dbTriggerService.findLikeTrigger(histories.get(0).getHostId(), expression);

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

                information.setMapList(mapList);
                //获取[]中的值
                String value1 = ConversionScriptsUtils.getValue(triggerExe);

                //获取表达式关系,>,>=,<,<=,=,!=
                String substring = triggerExe.substring(triggerExe.indexOf("}}") + 2);
                String substring1 = substring.substring(0, substring.indexOf("["));

                information.setReportData(value1);
                information.setExpression(substring1);
                information.setProblem(triggerList.get(0).getDescribe());
            }
        }
    }

    //部分触发告警的数据变颜色,在前端提示出来
    private void InternetColorLine(List<History> histories, History information) {
        //根据监控项查询监控项的阈值
        String expression = histories.get(0).getExpression();
        if (StringUtils.isBlank(expression)) {
            return;
        }
        //根据表达式查询触发器表达式
        List<InTrigger> triggerList = inTriggerService.findLikeTrigger(histories.get(0).getHostId(), expression);

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

                information.setMapList(mapList);
                //获取[]中的值
                String value1 = ConversionScriptsUtils.getValue(triggerExe);

                //获取表达式关系,>,>=,<,<=,=,!=
                String substring = triggerExe.substring(triggerExe.indexOf("}}") + 2);
                String substring1 = substring.substring(0, substring.indexOf("["));

                information.setReportData(value1);
                information.setExpression(substring1);
                information.setProblem(triggerList.get(0).getDescribe());
            }
        }
    }

    @Override
    public Map<String, Object> findKuOverviewTotal(String kuId) {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 22; i++) {
            list.add(String.valueOf(i));
        }
        List<History> entityList = historyDao.findKuOverviewTotal(list, kuId);
        List<History> entities = entityList.stream().filter(h -> h.getReportType() == 1).collect(Collectors.groupingBy(
                        History::getMonitorId,
                        Collectors.maxBy(Comparator.comparing(History::getGatherTime))
                ))
                .values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        map.put("mapTotal", entities);

        List<Object> objectList = new LinkedList<>();

        List<History> collect = entityList.stream().filter(h -> h.getReportType() == 2).toList();
        for (History history : collect) {
            Map<String, Object> objectMap = new HashMap<>();
            List<List<String>> listList = new LinkedList<>();
            Set<String> stringSet = new HashSet<>();

            if (StringUtils.isNotBlank(history.getReportData()) && !"null".equals(history.getReportData())) {
                List<Map<String, String>> mapList = JSON.parseObject(history.getReportData(), List.class);
                if (mapList.isEmpty()) {
                    continue;
                }
                for (Map<String, String> stringMap : mapList) {
                    List<String> list1 = new LinkedList<>();
                    for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                        stringSet.add(entry.getKey());
                        list1.add(entry.getValue());
                    }
                    listList.add(list1);
                }
                listList.add(stringSet.stream().toList());
                Collections.reverse(listList);
            }

            objectMap.put("name", history.getName());
            objectMap.put("data", listList);

            objectList.add(objectMap);
        }
        map.put("mapStatus", objectList);

        return map;
    }

    @Override
    public List<History> findHistoryByGatherTime(String hostId, String beforeTime) {
        return historyDao.findHistoryByGatherTime(hostId, beforeTime);
    }

    @Override
    public List<History> findDbHistoryByHostId(String hostId, String beforeTime) {
        return historyDao.findDbHistoryByHostId(hostId, beforeTime);
    }

    @Override
    public List<History> findKuHistoryByHostId(String kuId, String beforeTime) {
        return historyDao.findKuHistoryByHostId(kuId, beforeTime);
    }

    @Override
    public List<List<History>> findInGraphicsLine(History history) {
        //根据id查询图形列表
        List<InternetGraphics> graphicsList = internetGraphicsService.findGraphicsList(history.getHostId());

        List<List<History>> resList = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalTime = LocalDateTime.parse(history.getBeginTime(), dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(history.getEndTime(), dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();

        if (minutes <= 5) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 1);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (InternetGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<InGraphicsMonitor> graphicsMonitors = internetGraphicsService.findGraphicsMonitors(graphics.getId());
                if (graphicsMonitors.isEmpty()) {
                    continue;
                }
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (InGraphicsMonitor graphicsMonitor : graphicsMonitors) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyDao.findHistoryByInMonitorId(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        InternetColorLine(historyList, history1);
                    }

                    for (String string : xTime) {
                        History history3 = new History();
                        history3.setGatherTime(string);
                        history3.setReportData("null");
                        for (History history2 : historyList) {
                            if (string.equals(history2.getGatherTime())) {
                                history3.setReportData(history2.getReportData());
                            }
                        }
                        reportList.add(history3);
                    }
                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else if (minutes <= 60) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 2);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (InternetGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<InGraphicsMonitor> graphicsMonitors = internetGraphicsService.findGraphicsMonitors(graphics.getId());
                if (graphicsMonitors.isEmpty()) {
                    continue;
                }
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();
                for (InGraphicsMonitor graphicsMonitor : graphicsMonitors) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findInHistoryByOneTime(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        InternetColorLine(historyList, history1);
                    }

                    for (String string : xTime) {
                        History history3 = new History();
                        history3.setGatherTime(string);
                        history3.setReportData("null");
                        for (History history2 : historyList) {
                            if (string.equals(history2.getGatherTime())) {
                                history3.setReportData(history2.getReportData());
                            }
                        }
                        reportList.add(history3);
                    }
                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else if (minutes <= 720) {
            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 3);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (InternetGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<InGraphicsMonitor> graphicsMonitors = internetGraphicsService.findGraphicsMonitors(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();

                for (InGraphicsMonitor graphicsMonitor : graphicsMonitors) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(graphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findInHistoryByFiveTime(history);

                    history1.setGraphicsName(graphicsMonitor.getGraphicsName());

                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        InternetColorLine(historyList, history1);
                        for (String string : xTime) {
                            History history3 = new History();
                            history3.setGatherTime(string);
                            history3.setReportData("null");
                            for (History history2 : historyList) {
                                if (string.equals(history2.getGatherTime())) {
                                    history3.setReportData(history2.getReportData());
                                }
                            }
                            reportList.add(history3);
                        }
                    } else {
                        continue;
                    }

                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        } else {

            List<String> xTime = ConversionDateUtil.splitTime(history.getBeginTime(), history.getEndTime(), 4);

            //2.根据查询出的图表,分别查询出图表中对应的监控项数据
            for (InternetGraphics graphics : graphicsList) {
                //查询出每个图表下的监控项
                List<InGraphicsMonitor> graphicsMonitorList = internetGraphicsService.findGraphicsMonitors(graphics.getId());
                //根据监控项的ids查询监控项的数据
                //查询每条监控项的数据集合
                List<History> list = new LinkedList<>();

                for (InGraphicsMonitor inGraphicsMonitor : graphicsMonitorList) {

                    List<History> reportList = new LinkedList<>();
                    History history1 = new History();
                    history.setMonitorId(inGraphicsMonitor.getMonitorId());
                    List<History> historyList = historyMultiDao.findInHistoryByFifteenTime(history);

                    history1.setGraphicsName(inGraphicsMonitor.getGraphicsName());
                    if (!historyList.isEmpty()) {
                        history1.setName(historyList.get(0).getMonitorName());
                        InternetColorLine(historyList, history1);
                    }

                    for (String string : xTime) {
                        History history3 = new History();
                        history3.setGatherTime(string);
                        history3.setReportData("null");
                        for (History history2 : historyList) {
                            if (string.equals(history2.getGatherTime())) {
                                history3.setReportData(history2.getReportData());
                            }
                        }
                        reportList.add(history3);
                    }
                    List<String> stringList2 = reportList.stream().sorted(Comparator.comparing(History::getGatherTime)).map(History::getReportData).toList();
                    history1.setData(stringList2);
                    history1.setDataTimes(xTime);
                    if (StringUtils.isNotBlank(history1.getName())) {
                        list.add(history1);
                    }
                }
                if (!list.isEmpty()) {
                    resList.add(list);
                }
            }
        }
        return resList;
    }

    /**
     * 查询出网络的详情页
     */
    @Override
    public Map<String, Object> findInternetOverview(String internetId) {

        Map<String, Object> map = new HashMap<>();

        //将端口状态的信息转换成list放到map中
        History history = new History();
        history.setHostId(internetId);
        history.setMonitorId("301");
        List<History> list = historyDao.findHistoryByCondition(history, null, null);
        if (!list.isEmpty()) {
            String reportData = list.get(0).getReportData();
            List<Map<String, Object>> mapList = JSON.parseObject(reportData, List.class);
            map.put("podInfo", mapList);
        }

        History history2 = new History();
        history2.setHostId(internetId);
        history2.setMonitorId("304");
        List<History> list2 = historyDao.findHistoryByCondition(history2, null, null);
        if (!list2.isEmpty()) {
            String reportData = list2.get(0).getReportData();
            Map map1 = JSON.parseObject(reportData, Map.class);
            map.put("systemInfo", map1);
        }
        return map;
    }

    @Override
    public List<History> findInHistoryByHostId(String internetId, String beforeTime) {
        return historyDao.findInHistoryByHostId(internetId, beforeTime);
    }

    @Override
    public List<History> findInternetToGatherTime(String internetId, String beforeTime) {
        return historyDao.findInternetToGatherTime(internetId, beforeTime);
    }

    @Override
    public List<History> findHistoryByHostId(String id, String beforeTime) {
        return historyDao.findHistoryByHostId(id, beforeTime);
    }

    @Override
    public List<History> findHistoryByHostIds( String beforeTime) {
        return historyDao.findHistoryByHostIds(beforeTime);
    }
}
