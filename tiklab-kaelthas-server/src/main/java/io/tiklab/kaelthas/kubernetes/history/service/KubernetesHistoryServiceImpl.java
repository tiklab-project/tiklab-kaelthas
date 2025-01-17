package io.tiklab.kaelthas.kubernetes.history.service;

import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.kubernetes.graphics.model.KuGraphics;
import io.tiklab.kaelthas.kubernetes.graphics.service.KuGraphicsService;
import io.tiklab.kaelthas.kubernetes.graphicsMonitor.model.KuGraphicsMonitor;
import io.tiklab.kaelthas.kubernetes.graphicsMonitor.service.KuGraphicsMonitorService;
import io.tiklab.kaelthas.kubernetes.history.dao.KubernetesHistoryDao;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistoryQuery;
import io.tiklab.kaelthas.util.ConversionDateUtil;
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
import java.util.stream.Collectors;

@Service
public class KubernetesHistoryServiceImpl implements KubernetesHistoryService {

    @Autowired
    KubernetesHistoryDao kubernetesHistoryDao;


    @Autowired
    private KuGraphicsService kuGraphicsService;

    @Autowired
    private KuGraphicsMonitorService kuGraphicsMonitorService;



    @Override
    public void insertForList(List<KubernetesHistory> entityList) {

        //获取数据库表名
        String dbTableName = TableUtil.getK8sTableName(0);
        List<Map<String, Object>> mapList = getMapList(entityList);
        //拼接添加历史的数据的sql
        String historySql = SqlUtil.getBatchInsertSql(dbTableName, mapList);

        kubernetesHistoryDao.insertHistoryList(historySql);

        List<KubernetesHistory> list1 = new ArrayList<>();
        List<KubernetesHistory> list5 = new ArrayList<>();
        List<KubernetesHistory> list15 = new ArrayList<>();

        for (KubernetesHistory information : entityList) {
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
            kubernetesHistoryDao.insertHistoryList(historySql1);
        }

        if (!list5.isEmpty()) {
            List<Map<String, Object>> mapList5 = getMapList(list5);
            String historySql5 = SqlUtil.getBatchInsertSql(dbTableName+"_5", mapList5);
            kubernetesHistoryDao.insertHistoryList(historySql5);
        }

        if (!list15.isEmpty()) {
            List<Map<String, Object>> mapList15 = getMapList(list15);
            String historySql15 = SqlUtil.getBatchInsertSql(dbTableName+"_15", mapList15);
            kubernetesHistoryDao.insertHistoryList(historySql15);
        }
    }

    @Override
    public List<List<KubernetesHistory>> findKuGraphicsLine(KubernetesHistoryQuery kubernetesHistoryQuery) {
        List<List<KubernetesHistory>> resultList;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalTime = LocalDateTime.parse(kubernetesHistoryQuery.getBeginTime(), dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(kubernetesHistoryQuery.getEndTime(), dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();

        if (minutes <= 5) {
            resultList = joinMonitorHistoryData(kubernetesHistoryQuery, 1);
        } else if (minutes <= 60) {
            resultList = joinMonitorHistoryData(kubernetesHistoryQuery, 2);
        } else if (minutes <= 720) {
            resultList = joinMonitorHistoryData(kubernetesHistoryQuery, 3);
        } else {
            resultList = joinMonitorHistoryData(kubernetesHistoryQuery, 4);
        }

        return resultList;
    }

    @Override
    public List<KubernetesHistory> findKuHistoryByTime(String beforeTime) {
        String tableName = TableUtil.getK8sTableName(0);
        List<KubernetesHistory> kuHistoryList = kubernetesHistoryDao.findKuHistoryByTime(beforeTime, tableName);
        return kuHistoryList;
    }

    /**
     * 拼接监控历史数据
     * @param kubernetesHistoryQuery 查询数据
     * @param data 查询的时间段   1:5分钟内、2:60分钟内、 3:720分钟内、4:其他的
     */

    public List<List<KubernetesHistory>> joinMonitorHistoryData(KubernetesHistoryQuery kubernetesHistoryQuery, int data){
        List<List<KubernetesHistory>> resList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();

        //根据id查询图形列表
        List<KuGraphics> graphicsList = kuGraphicsService.findKuGraphicsList(kubernetesHistoryQuery.getKuId());
        List<String> xTime = ConversionDateUtil.splitTime(kubernetesHistoryQuery.getBeginTime(), kubernetesHistoryQuery.getEndTime(), data);

        //2.根据查询出的图表,分别查询出图表中对应的监控项数据
        for (KuGraphics graphics : graphicsList) {

            //查询出每个图表下的监控项
            List<KuGraphicsMonitor> graphicsMonitors = kuGraphicsMonitorService.findGraphicsMonitors(graphics.getId());
            if (graphicsMonitors.isEmpty()) {
                continue;
            }

            //根据监控项的ids查询监控项的数据
            //查询每条监控项的数据集合
            List<KubernetesHistory> list = new LinkedList<>();
            for (KuGraphicsMonitor graphicsMonitor : graphicsMonitors) {

                //通过监控项id查询监控历史记录
                kubernetesHistoryQuery.setKuMonitorId(graphicsMonitor.getMonitorId());

                //通过监控项id和时间段查询历史
                int endName = TableUtil.getHistoryEndName(data);
                String dbTableName = TableUtil.getK8sTableName(endName);
                List<KubernetesHistory> kubernetesHistoryList = kubernetesHistoryDao.findHistoryByKuMonitorId(kubernetesHistoryQuery, dbTableName);

                KubernetesHistory kubernetesHistory = new KubernetesHistory();
                kubernetesHistory.setGraphicsName(graphicsMonitor.getGraphicsName());

                if (!kubernetesHistoryList.isEmpty()) {
                    kubernetesHistoryList=kubernetesHistoryList.stream().sorted(Comparator.comparing(KubernetesHistory::getGatherTime))
                            .collect(Collectors.toList());

                    //监控项名字
                    kubernetesHistory.setName(kubernetesHistoryList.get(0).getMonitorName());
                    // setTriggerService(historyList, history1);

                    //循环添加时间、数据
                    for (int i=0;i<kubernetesHistoryList.size();i++){
                        String gatherTime = kubernetesHistoryList.get(i).getGatherTime();
                        String reportData = kubernetesHistoryList.get(i).getReportData();

                        if (i==0){
                            //查询的数据第一个时间不等于客户端传入的开始时间 添加内容为null
                            if (!kubernetesHistoryQuery.getBeginTime().equals(gatherTime)){
                                timeList.add(kubernetesHistoryQuery.getBeginTime());
                                dataList.add("null");
                            }else {
                                timeList.add(gatherTime);
                                dataList.add(reportData);
                            }
                        }
                        if (i==kubernetesHistoryList.size()-1){
                            //查询的数据最后一个时间不等于客户端传入的结束时间 添加内容为null
                            if (!kubernetesHistoryQuery.getEndTime().equals(gatherTime)){
                                timeList.add(kubernetesHistoryQuery.getEndTime());
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
                    kubernetesHistory.setData(dataList);
                    kubernetesHistory.setDataTimes(timeList);
                }else {
                    kubernetesHistory.setData(dataList);
                    kubernetesHistory.setDataTimes(xTime);
                }
                if (StringUtils.isNotBlank(kubernetesHistory.getName())) {
                    list.add(kubernetesHistory);
                }
            }
            if (!list.isEmpty()) {
                resList.add(list);
            }
        }
        return resList;
    }


    /**
     * 定义要插入表的字段,在插入数据的时候使用
     */
    private static List<Map<String, Object>> getMapList(List<KubernetesHistory> entityList) {
        return entityList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            String uuid = RandomStringUtils.randomAlphanumeric(12);
            map.put("id", uuid);
            map.put("ku_id", history.getKuId());
            map.put("ku_monitor_id", history.getKuMonitorId());
            map.put("report_data", history.getReportData());
            map.put("gather_time", history.getGatherTime());
            return map;
        }).toList();
    }
}
