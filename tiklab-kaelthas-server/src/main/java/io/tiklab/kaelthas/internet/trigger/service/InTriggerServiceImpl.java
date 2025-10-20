package io.tiklab.kaelthas.internet.trigger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.db.agent.utils.AgentSqlUtil;
import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.internet.history.service.InternetHistoryService;
import io.tiklab.kaelthas.internet.trigger.model.InTriggerQuery;
import io.tiklab.kaelthas.util.ConversionScriptsUtils;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.util.StringUtil;
import io.tiklab.kaelthas.internet.trigger.dao.InTriggerDao;
import io.tiklab.kaelthas.internet.trigger.entity.InTriggerEntity;
import io.tiklab.kaelthas.internet.trigger.model.InTrigger;
import io.tiklab.kaelthas.internet.triggerMedium.service.InTriggerMediumService;
import io.tiklab.toolkit.beans.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 网络监控中的触发器
 */
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
    InternetHistoryService internetHistoryService;

    @Override
    public List<InTrigger> findTriggerList(InTriggerQuery inTriggerQuery) {
        List<InTriggerEntity> inTriggerEntityList = inTriggerDao.findInTriggerList(inTriggerQuery);
        List<InTrigger> inTriggers = BeanMapper.mapList(inTriggerEntityList, InTrigger.class);
        return inTriggers;
    }

    //分页查询触发器
    @Override
    public Pagination<InTrigger> findTriggerPage(InTrigger inTrigger) {
        return inTriggerDao.findTriggerPage(inTrigger);
    }

    //创建触发器
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

    //根据id删除触发器
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

    //修改触发器
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

    //根据监控网络的id删除触发器
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

    //根据监控网络的id查询触发器
    @Override
    public List<String> findTriggerByInId(String internetId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InTriggerEntity.class)
                .eq("internetId", internetId)
                .get();

        List<InTriggerEntity> entityList = inTriggerDao.findTriggerByInId(queryCondition);

        return entityList.stream().map(InTriggerEntity::getId).collect(Collectors.toList());
    }

    //触发器定时器,定时拉取触发器进行告警
    //@Scheduled(cron = "0 0/5 * * * ? ")
    public void TimerInTrigger() {
        //查询网络下打开的触发器
        List<InTriggerEntity> inTriggerEntityList = inTriggerDao.findInTriggerList(new InTriggerQuery().setState(1));

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

    //百分比触发,计算一定时间内触发的占比超过指定数值
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
            //通过网络ID、时间、表达式查询监控历史数据(查询20分钟内的数据)
            String triggerExData = conversionScriptsUtils.getTriggerExData(trigger.getExpression());
            List<InternetHistory> informationList = internetHistoryService.findInternetToGatherTime(trigger.getInternetId(), beforeTime,triggerExData);

            if (informationList.isEmpty()) {
                return;
            }



            //根据监控项id进行分组,然后进行计算平均值
            Collection<List<InternetHistory>> values1 = informationList.stream().collect(Collectors.groupingBy(InternetHistory::getInternetMonitorId)).values();
            String avgNumber = StringUtil.getInAvgNumber(values1);

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

    //平均值触发,计算在一定时间段内的平均值进行触发
    private void getAvgValueInTrigger(InTriggerEntity trigger) {
        try {

            String flag;

            Alarm alarm = new Alarm();

            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //通过网络ID、时间、表达式查询监控历史数据(查询20分钟内的数据)
            String triggerExData = conversionScriptsUtils.getTriggerExData(trigger.getExpression());
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);
            List<InternetHistory> informationList = internetHistoryService.findInternetToGatherTime(trigger.getInternetId(), beforeTime,triggerExData);

            Collection<List<InternetHistory>> values = informationList.stream().collect(Collectors.groupingBy(InternetHistory::getGatherTime)).values();

            if (values.isEmpty()) {
                return;
            }

            String strJson = StringUtil.getInAvgNumber(values);

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

    //最近值触发,使用存储的最后一个数据进行触发
    private void getLastValueInTrigger(InTriggerEntity trigger) {
        try {
            String flag;

            Alarm alarm = new Alarm();
            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());
            if (StringUtils.isBlank(trigger.getExpression())) {
                return;
            }

            //通过网络ID、时间、表达式查询监控历史数据(查询20分钟内的数据)
            String triggerExData = conversionScriptsUtils.getTriggerExData(trigger.getExpression());
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);
            List<InternetHistory> historyList = internetHistoryService.findInternetToGatherTime(trigger.getInternetId(), beforeTime,triggerExData);

            if (historyList.isEmpty()) {
                return;
            }

            //排序
            List<InternetHistory> list = historyList.stream().sorted(Comparator.comparing(InternetHistory::getGatherTime).reversed()).toList();


            //将触发器的表达式和数值进行替换,看是否能够进行触发

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //将数据换成JSON的字符串
            String json = StringUtil.getInString(list);

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

    //根据监控项表达式模糊查询触发器表达式
    @Override
    public List<InTrigger> findLikeTrigger(String hostId, String expression) {

        List<InTriggerEntity> triggerEntityList = inTriggerDao.findLikeTrigger(hostId,expression);

        return BeanMapper.mapList(triggerEntityList,InTrigger.class);
    }
}
