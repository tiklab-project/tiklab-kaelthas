package io.tiklab.kaelthas.internet.internetTrigger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.collection.util.AgentSqlUtil;
import io.tiklab.kaelthas.common.javascripts.ConversionScriptsUtils;
import io.tiklab.kaelthas.common.util.ConversionDateUtil;
import io.tiklab.kaelthas.common.util.StringUtil;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.internet.internetTrigger.dao.InTriggerDao;
import io.tiklab.kaelthas.internet.internetTrigger.entity.InTriggerEntity;
import io.tiklab.kaelthas.internet.internetTrigger.model.InTrigger;
import io.tiklab.kaelthas.internet.internetTriggerMedium.service.InTriggerMediumService;
import io.tiklab.toolkit.beans.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InTriggerServiceImpl implements InTriggerService {

    @Autowired
    private InTriggerDao inTriggerDao;

    @Autowired
    private InTriggerMediumService inTriggerMediumService;

    @Autowired
    private ConversionScriptsUtils conversionScriptsUtils;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private HistoryService historyService;

    @Override
    public Pagination<InTrigger> findTriggerPage(InTrigger inTrigger) {
        return inTriggerDao.findTriggerPage(inTrigger);
    }

    @Override
    public String createInTrigger(InTrigger inTrigger) {
        try {
            //创建触发器的时候,将创建的消息通知方案添加到与消息通知方案的关联表中
            InTriggerEntity entity = BeanMapper.map(inTrigger, InTriggerEntity.class);
            String string = inTriggerDao.createInTrigger(entity);

            //将传递过来的消息方案添加到触发器与消息方案中间表当中
            if (!inTrigger.getMediumIds().isEmpty()) {
                inTriggerMediumService.createTriggerMedium(string, inTrigger.getMediumIds());
            }
            return string;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTrigger(String id) {
        try {
            //根据触发器id删除触发器
            inTriggerDao.deleteTrigger(id);
            //根据触发器id删除触发器和消息方案的中间表
            inTriggerMediumService.deleteByTrigger(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTrigger(InTrigger inTrigger) {
        //如果没有修改消息通知方案
        if (inTrigger.getMediumIds().isEmpty()) {
            InTriggerEntity entity = BeanMapper.map(inTrigger, InTriggerEntity.class);
            inTriggerDao.updateTrigger(entity);
            //如果修改了消息通知方案
        } else {
            try {
                //先删除中间表的数据,然后再将数据插入
                inTriggerMediumService.deleteByTrigger(inTrigger.getId());
                inTriggerMediumService.createTriggerMedium(inTrigger.getId(), inTrigger.getMediumIds());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteByInId(String internetId) {
        if (StringUtils.isBlank(internetId)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(InTriggerEntity.class)
                .eq("internetId", internetId)
                .get();
        inTriggerDao.deleteByInId(deleteCondition);
    }

    @Override
    public List<String> findTriggerByInId(String internetId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InTriggerEntity.class)
                .eq("internetId", internetId)
                .get();

        List<InTriggerEntity> entityList = inTriggerDao.findTriggerByInId(queryCondition);

        return entityList.stream().map(InTriggerEntity::getId).toList();
    }

    @Scheduled(cron = "0 0/2 * * * ? ")
    public void TimerInTrigger() {
        List<InTriggerEntity> inTriggerEntityList = inTriggerDao.findTriggerAll();

        if (inTriggerEntityList.isEmpty()) {
            return;
        }

        for (InTriggerEntity inTriggerEntity : inTriggerEntityList) {
            switch (inTriggerEntity.getScheme()) {
                case 1:
                    getLastValueInTrigger(inTriggerEntity);
                    break;
                case 2:
                    getAvgValueInTrigger(inTriggerEntity);
                    break;
                case 3:
                    getPercentInTrigger(inTriggerEntity);
                    break;
            }
        }
    }

    private void getPercentInTrigger(InTriggerEntity trigger) {
        try {
            String flag;

            Alarm alarm = new Alarm();
            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

            if (StringUtils.isBlank(trigger.getExpression())) {
                return;
            }

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //将当前的触发器的表达式进行切割,获取表达式,将时间段内的表达式获取出来,然后进行计算
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);
            List<History> informationList = historyService.findInternetToGatherTime(trigger.getInternetId(), beforeTime);

            if (informationList.isEmpty()) {
                return;
            }

            //根据监控项id进行分组,然后进行计算平均值
            Collection<List<History>> values1 = informationList.stream().collect(Collectors.groupingBy(History::getMonitorId)).values();
            String avgNumber = StringUtil.getAvgNumber(values1);

            JSONObject jsonObject = JSONObject.parseObject(avgNumber);

            String string = conversionScriptsUtils.replaceValue(trigger.getExpression(), jsonObject);

            Set<String> functionList = conversionScriptsUtils.getFunctionList(string);

            if (functionList.isEmpty()) {

                try {
                    Object eval = engine.eval(string);
                    flag = JSON.toJSONString(eval);

                } catch (ScriptException e) {
                    throw new RuntimeException(e);
                }
            } else {
                flag = "false";
            }

            //如果触发成功的话进行插入操作
            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(trigger.getInternetId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(4);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarmForInternet(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAvgValueInTrigger(InTriggerEntity trigger) {
        try {

            String flag;

            Alarm alarm = new Alarm();

            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);

            List<History> informationList = historyService.findInternetToGatherTime(trigger.getInternetId(), beforeTime);

            Collection<List<History>> values = informationList.stream().collect(Collectors.groupingBy(History::getGatherTime)).values();

            if (values.isEmpty()) {
                return;
            }

            String strJson = StringUtil.getAvgNumber(values);

            JSONObject jsonObject = JSONObject.parseObject(strJson);
            //将表达式替换成值,然后进行运算
            String string = conversionScriptsUtils.replaceValue(trigger.getExpression(), jsonObject);

            //查看这个表达式当中有没有没有被替换掉的表达式,如果有的话就不进行运算了,没有才进行运算
            Set<String> functionList = conversionScriptsUtils.getFunctionList(string);
            if (functionList.isEmpty()) {
                try {
                    Object eval = engine.eval(string);
                    String jsonString = JSON.toJSONString(eval);
                    if ("true".equals(jsonString)) {
                        flag = "true";
                    }else {
                        flag = "false";
                    }
                } catch (ScriptException e) {
                    throw new RuntimeException(e);
                }
            }else {
                flag = "false";
            }

            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(trigger.getInternetId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(4);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarmForInternet(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    private void getLastValueInTrigger(InTriggerEntity trigger) {
        try {
            String flag;

            Alarm alarm = new Alarm();
            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());
            if (StringUtils.isBlank(trigger.getExpression())) {
                return;
            }

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);

            //根据触发器所在的数据库,将监控项指标全部查询出来,依次进行比对(查询20分钟内的数据)
            List<History> historyList = historyService.findInHistoryByHostId(trigger.getInternetId(), beforeTime);

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

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //将数据换成JSON的字符串
            String json = StringUtil.getString(list);

            //将字符串转换成JSON
            JSONObject jsonObject = JSONObject.parseObject(json);

            //将表达式中的
            String value = conversionScriptsUtils.replaceValue(trigger.getExpression(), jsonObject);

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
                flag = "false";
            }

            //如果触发成功的话进行插入操作
            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(trigger.getInternetId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(4);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarmForInternet(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<InTrigger> findLikeTrigger(String hostId, String expression) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InTriggerEntity.class)
                .eq("internetId", hostId)
                .like("expression", expression)
                .get();
        List<InTriggerEntity> triggerEntityList = inTriggerDao.findLikeTrigger(queryCondition);

        return BeanMapper.mapList(triggerEntityList,InTrigger.class);
    }
}
