package io.thoughtware.kaelthas.db.dbTrigger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.collection.util.SqlUtil;
import io.thoughtware.kaelthas.db.dbDynamic.model.DbDynamic;
import io.thoughtware.kaelthas.db.dbDynamic.service.DbDynamicService;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.common.javascripts.ConversionScriptsUtils;
import io.thoughtware.kaelthas.db.dbMonitor.model.DbMonitor;
import io.thoughtware.kaelthas.db.dbMonitor.service.DbMonitorService;
import io.thoughtware.kaelthas.db.dbTrigger.dao.DbTriggerDao;
import io.thoughtware.kaelthas.db.dbTrigger.entity.DbTriggerEntity;
import io.thoughtware.kaelthas.db.dbTrigger.model.DbTrigger;
import io.thoughtware.kaelthas.db.dbTriggerMedium.service.DbTriggerMediumService;
import io.thoughtware.kaelthas.alarm.model.Alarm;
import io.thoughtware.kaelthas.alarm.service.AlarmService;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.history.service.HistoryService;
import io.thoughtware.kaelthas.common.util.ConversionDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DbTriggerServiceImpl implements DbTriggerService {

    @Autowired
    private DbTriggerDao dbTriggerDao;

    @Autowired
    private DbTriggerMediumService dbTriggerMediumService;

    @Autowired
    private ConversionScriptsUtils conversionScriptsUtils;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private DbMonitorService dbMonitorService;

    @Autowired
    private DbDynamicService dbDynamicService;

    @Override
    public Pagination<DbTrigger> findDbTriggerPage(DbTrigger dbTrigger) {
        return dbTriggerDao.findDbTriggerPage(dbTrigger);
    }

    @Override
    public String createDbTrigger(DbTrigger dbTrigger) {
        try {
            DbTriggerEntity entity = BeanMapper.map(dbTrigger, DbTriggerEntity.class);
            String dbTriggerId = dbTriggerDao.createDbTrigger(entity);

            //将选择的告警类型,分别插入到中间表中
            dbTriggerMediumService.createTriggerMedium(dbTriggerId, dbTrigger.getMediumType());

            DbDynamic dbDynamic = new DbDynamic();
            dbDynamic.setDbId(dbTrigger.getDbId());
            dbDynamic.setName("创建触发器———" + dbTrigger.getName());
            dbDynamicService.createDbDynamic(dbDynamic);
            return dbTriggerId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteDbTrigger(String id) {
        try {
            dbTriggerDao.deleteDbTrigger(id);
            //删除关联表
            dbTriggerMediumService.deleteByTriggerId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDbTrigger(DbTrigger dbTrigger) {
        DbTriggerEntity entity = BeanMapper.map(dbTrigger, DbTriggerEntity.class);
        dbTriggerDao.updateDbTrigger(entity);

        if (dbTrigger.getMediumType() != null && !dbTrigger.getMediumType().isEmpty()) {
            //将之前的的关联删除,将修改后的进行添加
            dbTriggerMediumService.deleteByTriggerId(dbTrigger.getId());
            dbTriggerMediumService.createTriggerMedium(dbTrigger.getId(), dbTrigger.getMediumType());
        }
    }

    @Override
    public List<DbTrigger> findListByDbId(String hostId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbTriggerEntity.class)
                .eq("dbId", hostId)
                .eq("state", 1)
                .get();
        List<DbTriggerEntity> listByDbId = dbTriggerDao.findListByDbId(queryCondition);
        return BeanMapper.mapList(listByDbId, DbTrigger.class);
    }

    @Override
    public void insertAlarmForDbTrigger(List<History> histories) {

        try {

            Alarm alarm = new Alarm();
            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            QueryCondition queryCondition = QueryBuilders.createQuery(DbTriggerEntity.class)
                    .eq("state", 1)
                    .get();

            List<DbTriggerEntity> triggers = dbTriggerDao.findListByDbId(queryCondition);

            List<String> stringList = triggers.stream().map(DbTriggerEntity::getDbId).toList();

            if (triggers.isEmpty() || stringList.isEmpty()) {
                return;
            }

            Map<String, List<DbTriggerEntity>> triggerMap = triggers.stream().collect(Collectors.groupingBy(DbTriggerEntity::getDbId));

            for (String string : stringList) {
                List<DbTriggerEntity> triggerList = triggerMap.get(string);
                List<History> list = histories.stream().filter(k -> k.getHostId().equals(string)).toList();

                if (triggerList.isEmpty()) {
                    break;
                }

                for (DbTriggerEntity trigger : triggerList) {
                    int isTriggerNum = 1;

                    alarm.setAlertTime(SqlUtil.getDataTimeNow());

                    //查询当前时间区间的最后一个值进行判断
                    if (trigger.getScheme() == 1) {
                        isTriggerNum = getIsTriggerNum(list, trigger, engine, isTriggerNum);
                    }
                    //平均值计算
                    isTriggerNum = getIsTriggerNum(trigger, string, engine, isTriggerNum);

                    //触发的数据超过一定百分比后进行告警
                    isTriggerNum = getTriggerNum(trigger, string, engine, isTriggerNum);

                    //如果为1的话进行插入
                    if (isTriggerNum == 1) {
                        //将这个时间记录到告警表当中,以后告警表计算的告警持续时间都是使用这个字段
                        alarm.setStatus(2);
                        alarm.setHostId(string);
                        alarm.setTriggerId(trigger.getId());
                        alarm.setSendMessage(trigger.getDescribe());
                        alarm.setMachineType(2);
                        alarm.setSeverityLevel(trigger.getSeverityLevel());
                        //插入到告警表当中
                        alarmService.createAlarmByDbMonitor(alarm);
                    }
                }
            }
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "* */2 * * * * ")
    public void TimerTrigger() {
        //ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // 使用单线程池确保任务不会同时执行
        //executorService.scheduleAtFixedRate(() -> {
        //获取所有数据库下的触发器
        List<DbTriggerEntity> triggerList = dbTriggerDao.findAllTrigger();

        for (DbTriggerEntity dbTriggerEntity : triggerList) {
            //三种触发方式
            switch (dbTriggerEntity.getScheme()) {
                case 1:
                    getLastValueTrigger(dbTriggerEntity);
                    break;
                case 2:
                    getAvgValueTrigger(dbTriggerEntity);
                    break;
                case 3:
                    getPercentValueTrigger(dbTriggerEntity);
                    break;
            }
        }

        //}, 0, 10, TimeUnit.SECONDS);

    }

    //平均值计算,求时间段内的平均值
    private void getPercentValueTrigger(DbTriggerEntity dbTriggerEntity) {

        String flag;

        Alarm alarm = new Alarm();
        alarm.setAlertTime(SqlUtil.getDataTimeNow());

        if (StringUtils.isBlank(dbTriggerEntity.getExpression())) {
            return;
        }

        try {

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //将当前的触发器的表达式进行切割,获取表达式,将时间段内的表达式获取出来,然后进行计算
            //Set<String> functionList = conversionScriptsUtils.getFunctionList(dbTriggerEntity.getExpression());

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, dbTriggerEntity.getRangeTime(), null);
            List<History> informationList = historyService.findHistoryByGatherTime(dbTriggerEntity.getDbId(), beforeTime);

            if (informationList.isEmpty()) {
                return;
            }

            //根据监控项id进行分组,然后进行计算平均值
            Collection<List<History>> values1 = informationList.stream().collect(Collectors.groupingBy(History::getMonitorId)).values();
            String avgNumber = setAvgNumber(values1);

            JSONObject jsonObject = JSONObject.parseObject(avgNumber);

            String string = conversionScriptsUtils.replaceValue(dbTriggerEntity.getExpression(), jsonObject);

            Set<String> functionList = conversionScriptsUtils.getFunctionList(string);

            if (functionList.isEmpty()) {

                try {
                    Object eval = engine.eval(string);
                    flag = JSON.toJSONString(eval);

                } catch (ScriptException e) {
                    throw new RuntimeException(e);
                }
            } else {
                flag = "true";
            }

            //如果触发成功的话进行插入操作
            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(dbTriggerEntity.getDbId());
                alarm.setTriggerId(dbTriggerEntity.getId());
                alarm.setSendMessage(dbTriggerEntity.getDescribe());
                alarm.setMachineType(2);
                alarm.setSeverityLevel(dbTriggerEntity.getSeverityLevel());
                alarmService.createAlarmByDbMonitor(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    //求时间段内的数据超过一定百分比
    private void getAvgValueTrigger(DbTriggerEntity dbTriggerEntity) {

        try {
            Alarm alarm = new Alarm();

            alarm.setAlertTime(SqlUtil.getDataTimeNow());

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, dbTriggerEntity.getRangeTime(), null);

            List<History> informationList = historyService.findInformationToGatherTime(dbTriggerEntity.getDbId(), beforeTime);

            Collection<List<History>> values = informationList.stream().collect(Collectors.groupingBy(History::getGatherTime)).values();

            if (values.isEmpty()) {
                return;
            }
            BigDecimal total = new BigDecimal(values.size());

            BigDecimal triggerNum = new BigDecimal(0);

            for (List<History> value : values) {
                if (value.isEmpty()) {
                    continue;
                }

                String strJson = getString(value);
                JSONObject jsonObject = JSONObject.parseObject(strJson);
                //将表达式替换成值,然后进行运算
                String string = conversionScriptsUtils.replaceValue(dbTriggerEntity.getExpression(), jsonObject);

                //查看这个表达式当中有没有没有被替换掉的表达式,如果有的话就不进行运算了,没有才进行运算
                Set<String> functionList = conversionScriptsUtils.getFunctionList(string);
                if (functionList.isEmpty()) {

                    try {
                        Object eval = engine.eval(string);
                        String jsonString = JSON.toJSONString(eval);
                        if ("true".equals(jsonString)) {
                            triggerNum = triggerNum.add(BigDecimal.ONE);
                        }
                    } catch (ScriptException e) {
                        throw new RuntimeException(e);
                    }

                }

            }

            BigDecimal divide = triggerNum.divide(total, 2, RoundingMode.DOWN).multiply(BigDecimal.valueOf(100));

            if (divide.compareTo(BigDecimal.valueOf(dbTriggerEntity.getPercentage())) <= 0) {
                alarm.setStatus(2);
                alarm.setHostId(dbTriggerEntity.getDbId());
                alarm.setTriggerId(dbTriggerEntity.getId());
                alarm.setSendMessage(dbTriggerEntity.getDescribe());
                alarm.setMachineType(2);
                alarm.setSeverityLevel(dbTriggerEntity.getSeverityLevel());
                alarmService.createAlarmByDbMonitor(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }


    private void getLastValueTrigger(DbTriggerEntity dbTriggerEntity) {

        String flag;

        Alarm alarm = new Alarm();
        alarm.setAlertTime(SqlUtil.getDataTimeNow());
        if (StringUtils.isBlank(dbTriggerEntity.getExpression())) {
            return;
        }

        String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);

        //根据触发器所在的数据库,将监控项指标全部查询出来,依次进行比对(查询20分钟内的数据)
        List<History> historyList = historyService.findDbHistoryByHostId(dbTriggerEntity.getDbId(),beforeTime);

        if (historyList.isEmpty()) {
            return;
        }

        //将数据进行处理,取最后一个值
        List<History> list = historyList.stream()
                .collect(Collectors.toMap(
                        History::getMonitorId, // 根据 id 去重
                        history -> history, // 保留对象
                        (existing, replacement) -> replacement // 如果 id 相同，保留后者
                ))
                .values().stream().toList();

        //将触发器的表达式和数值进行替换,看是否能够进行触发
        try {

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //将数据换成JSON的字符串
            String json = getString(list);

            //将字符串转换成JSON
            JSONObject jsonObject = JSONObject.parseObject(json);

            //将表达式中的
            String value = conversionScriptsUtils.replaceValue(dbTriggerEntity.getExpression(), jsonObject);

            //查看这个表达式当中有没有没有被替换掉的表达式,如果有的话就不进行运算了,没有才进行运算
            Set<String> functionList = conversionScriptsUtils.getFunctionList(value);

            if (functionList.isEmpty()) {
                try {

                    Object eval = engine.eval(value);
                    flag = JSON.toJSONString(eval);

                } catch (ScriptException e) {
                    throw new RuntimeException(e);
                }
            } else {
                flag = "true";
            }

            //如果触发成功的话进行插入操作
            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(dbTriggerEntity.getDbId());
                alarm.setTriggerId(dbTriggerEntity.getId());
                alarm.setSendMessage(dbTriggerEntity.getDescribe());
                alarm.setMachineType(2);
                alarm.setSeverityLevel(dbTriggerEntity.getSeverityLevel());
                alarmService.createAlarmByDbMonitor(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DbTrigger> findLikeTrigger(String hostId, String expression) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbTriggerEntity.class)
                .eq("dbId", hostId)
                .like("expression", expression)
                .get();
        List<DbTriggerEntity> dbTriggerEntityList = dbTriggerDao.findLikeTrigger(queryCondition);
        return BeanMapper.mapList(dbTriggerEntityList, DbTrigger.class);
    }

    private int getTriggerNum(DbTriggerEntity trigger, String hostId, ScriptEngine engine, int isTriggerNum) {
        if (trigger.getScheme() == 3) {
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);
            List<History> informationList = historyService.findInformationToGatherTime(hostId, beforeTime);
            Collection<List<History>> values = informationList.stream().collect(Collectors.groupingBy(History::getGatherTime)).values();

            BigDecimal total = new BigDecimal(values.size());

            BigDecimal triggerNum = new BigDecimal(0);

            for (List<History> value : values) {
                if (!value.isEmpty()) {
                    String strJson = getString(value);
                    JSONObject jsonObject = JSONObject.parseObject(strJson);
                    //将表达式替换成值,然后进行运算
                    String string = conversionScriptsUtils.replaceValue(trigger.getExpression(), jsonObject);

                    //查看这个表达式当中有没有没有被替换掉的表达式,如果有的话就不进行运算了,没有才进行运算
                    Set<String> functionList = conversionScriptsUtils.getFunctionList(string);
                    if (functionList.isEmpty()) {
                        Object eval;
                        try {
                            eval = engine.eval(string);
                        } catch (ScriptException e) {
                            throw new RuntimeException(e);
                        }
                        String jsonString = JSON.toJSONString(eval);
                        if ("true".equals(jsonString)) {
                            triggerNum = triggerNum.add(BigDecimal.ONE);
                        } else {
                            triggerNum = triggerNum.add(BigDecimal.ZERO);
                        }
                    } else {
                        isTriggerNum += 1;
                        break;
                    }
                }
            }

            BigDecimal divide = triggerNum.divide(total, 2, RoundingMode.DOWN).multiply(BigDecimal.valueOf(100));

            if (divide.compareTo(BigDecimal.valueOf(trigger.getPercentage())) <= 0) {
                isTriggerNum += 1;
            }

        }
        return isTriggerNum;
    }

    private int getIsTriggerNum(DbTriggerEntity trigger, String hostId, ScriptEngine engine, int isTriggerNum) {
        if (trigger.getScheme() == 2) {
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);
            List<History> informationList = historyService.findHistoryByGatherTime(hostId, beforeTime);

            if (informationList.isEmpty()) {
                return 0;
            }

            //根据监控项id进行分组,然后进行计算平均值
            Collection<List<History>> values1 = informationList.stream().collect(Collectors.groupingBy(History::getMonitorId)).values();
            String avgNumber = setAvgNumber(values1);
            JSONObject jsonObject = JSONObject.parseObject(avgNumber);
            String string = conversionScriptsUtils.replaceValue(trigger.getExpression(), jsonObject);
            Set<String> functionList = conversionScriptsUtils.getFunctionList(string);
            if (functionList.isEmpty()) {
                Object eval;
                try {
                    eval = engine.eval(string);
                } catch (ScriptException e) {
                    throw new RuntimeException(e);
                }
                String jsonString = JSON.toJSONString(eval);
                if ("false".equals(jsonString)) {
                    isTriggerNum += 1;
                }
            } else {
                isTriggerNum += 1;
            }
        }
        return isTriggerNum;
    }

    private int getIsTriggerNum(List<History> entityList, DbTriggerEntity trigger, ScriptEngine engine, int isTriggerNum) {

        List<History> list = entityList.stream().collect(Collectors.groupingBy(
                History::getMonitorId,
                Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(History::getGatherTime)),
                        Optional -> Optional.orElse(null)
                )
        )).values().stream().toList();

        if (list.isEmpty()) {
            return 0;
        }

        //获取最后一组数据
        String lastValue = getLastValue(list);
        JSONObject jsonObject = JSONObject.parseObject(lastValue);
        //将表达式中{{}}中的值替换掉
        String string = conversionScriptsUtils.replaceValue(trigger.getExpression(), jsonObject);

        //检验是否没有匹配到的,如果存在没有监控项的数据,则不会进行告警
        Set<String> functionList = conversionScriptsUtils.getFunctionList(string);
        if (functionList.isEmpty()) {
            Object eval;
            try {
                eval = engine.eval(string);
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
            String jsonString = JSON.toJSONString(eval);
            if ("false".equals(jsonString)) {
                isTriggerNum += 1;
            }
        } else {
            isTriggerNum += 1;
        }

        return isTriggerNum;
    }

    private String getLastValue(List<History> historyList) {
        String strJson = "{";
        for (int i = 0; i < historyList.size(); i++) {
            //根据监控项id查询出监控项的监控表达式
            DbMonitor dbMonitor = dbMonitorService.findListById(historyList.get(i).getMonitorId());
            strJson = strJson.concat("'" + dbMonitor.getExpression() + "':'" + historyList.get(i).getReportData() + "'");
            if (i != historyList.size() - 1) {
                strJson = strJson.concat(",");
            }
        }
        strJson = strJson.concat("}");
        return strJson;
    }

    private static String setAvgNumber(Collection<List<History>> values1) {
        String strJson = "{";
        for (List<History> histories : values1) {
            double avg = 0;
            try {
                avg = histories.stream().mapToDouble(item -> Double.parseDouble((item.getReportData() == null || "null".equals(item.getReportData())) ? "0" : item.getReportData())).average().orElse(0);
            } catch (Exception e) {
                List<String> stringList = histories.stream().map(History::getReportData).toList();
                System.err.println("stringList = " + stringList);
                throw new RuntimeException(e);
            }
            strJson = strJson.concat("'" + histories.get(0).getExpression() + "':'" + avg + "',");
        }

        strJson = strJson.concat("}");
        return strJson;
    }

    private static String getString(List<History> value) {
        String strJson = "{";
        for (int i = 0; i < value.size(); i++) {
            strJson = strJson.concat("'" + value.get(i).getExpression() + "':'" + value.get(i).getReportData() + "'");
            if (i != value.size() - 1) {
                strJson = strJson.concat(",");
            }
        }
        strJson = strJson.concat("}");
        return strJson;
    }
}
