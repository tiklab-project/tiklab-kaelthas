package io.tiklab.kaelthas.db.dbTrigger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.collection.util.AgentSqlUtil;
import io.tiklab.kaelthas.common.util.StringUtil;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;
import io.tiklab.kaelthas.db.dbDynamic.service.DbDynamicService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.common.javascripts.ConversionScriptsUtils;
import io.tiklab.kaelthas.db.dbTrigger.dao.DbTriggerDao;
import io.tiklab.kaelthas.db.dbTrigger.entity.DbTriggerEntity;
import io.tiklab.kaelthas.db.dbTrigger.model.DbTrigger;
import io.tiklab.kaelthas.db.dbTriggerMedium.service.DbTriggerMediumService;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.common.util.ConversionDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
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

            dbTrigger.setId(dbTriggerId);

            //将选择的告警类型,分别插入到中间表中
            dbTriggerMediumService.createTriggerMedium(dbTrigger);

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

            dbTriggerMediumService.createTriggerMedium(dbTrigger);
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

    @Scheduled(cron = "0 0/2 * * * ? ")
    public void TimerTrigger() {
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
    }

    //平均值计算,求时间段内的平均值
    private void getPercentValueTrigger(DbTriggerEntity dbTriggerEntity) {

        String flag;

        Alarm alarm = new Alarm();
        alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

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
            String avgNumber = StringUtil.getAvgNumber(values1);

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

            String flag;

            Alarm alarm = new Alarm();

            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, dbTriggerEntity.getRangeTime(), null);

            List<History> informationList = historyService.findInformationToGatherTime(dbTriggerEntity.getDbId(), beforeTime);

            Collection<List<History>> values = informationList.stream().collect(Collectors.groupingBy(History::getGatherTime)).values();

            if (values.isEmpty()) {
                return;
            }

            String strJson = StringUtil.getAvgNumber(values);
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
                        flag = "true";
                    } else {
                        flag = "false";
                    }
                } catch (ScriptException e) {
                    throw new RuntimeException(e);
                }
            } else {
                flag = "false";
            }

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


    private void getLastValueTrigger(DbTriggerEntity dbTriggerEntity) {

        String flag;

        Alarm alarm = new Alarm();
        alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());
        if (StringUtils.isBlank(dbTriggerEntity.getExpression())) {
            return;
        }

        String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);

        //根据触发器所在的数据库,将监控项指标全部查询出来,依次进行比对(查询20分钟内的数据)
        List<History> historyList = historyService.findDbHistoryByHostId(dbTriggerEntity.getDbId(), beforeTime);

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
            String json = StringUtil.getString(list);

            //将字符串转换成JSON
            JSONObject jsonObject = JSONObject.parseObject(json);

            //将表达式替换成值
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

    @Override
    public void deleteByDbId(String id) {
        if (StringUtils.isBlank(id)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DbTriggerEntity.class)
                .eq("dbId", id)
                .get();
        dbTriggerDao.deleteByDbId(deleteCondition);
    }
}
