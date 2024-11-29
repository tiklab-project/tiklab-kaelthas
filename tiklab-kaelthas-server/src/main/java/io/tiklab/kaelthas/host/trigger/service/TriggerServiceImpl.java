package io.tiklab.kaelthas.host.trigger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.collection.utils.AgentSqlUtil;
import io.tiklab.kaelthas.util.StringUtil;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.util.ConversionScriptsUtils;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;
import io.tiklab.kaelthas.host.hostDynamic.service.HostDynamicService;
import io.tiklab.kaelthas.host.monitor.service.HostMonitorService;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.host.templateMonitor.service.TemplateMonitorService;
import io.tiklab.kaelthas.host.monitorItem.service.MonitorItemService;
import io.tiklab.kaelthas.host.trigger.dao.TriggerDao;
import io.tiklab.kaelthas.host.trigger.entity.TriggerEntity;
import io.tiklab.kaelthas.host.trigger.model.Trigger;
import io.tiklab.kaelthas.host.triggerExpression.service.TriggerExpressionService;
import io.tiklab.kaelthas.history.service.HistoryService;
import io.tiklab.kaelthas.host.triggerMedium.service.TriggerMediumService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 主机下的触发器
 */
@Service
@Exporter
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    TriggerDao triggerDao;

    @Autowired
    TriggerExpressionService triggerExpressionService;

    @Autowired
    HostMonitorService hostMonitorService;

    @Autowired
    MonitorItemService monitorItemService;

    @Autowired
    TemplateMonitorService templateMonitorService;

    @Autowired
    HistoryService historyService;

    @Autowired
    AlarmService alarmService;

    @Autowired
    ConversionScriptsUtils conversionScriptsUtils;

    @Autowired
    TriggerMediumService triggerMediumService;

    @Autowired
    HostDynamicService hostDynamicService;

    @Autowired
    HostService hostService;


    /**
     * 分页查询主机下的触发器
     */
    @Override
    public Pagination<Trigger> findTrigger(Trigger trigger) {
        //查询触发器
        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerEntity.class)
                .pagination(trigger.getPageParam())
                .eq("hostId", trigger.getHostId())
                .like("name", trigger.getName())
                .get();

        Pagination<TriggerEntity> pagination = triggerDao.findTrigger(queryCondition);

        List<Trigger> triggers = BeanMapper.mapList(pagination.getDataList(), Trigger.class);

        for (Trigger trigger1 : triggers) {
            List<String> mediumIdByTriggerId = triggerMediumService.findMediumIdByTriggerId(trigger1.getId());
            trigger1.setMediumIds(mediumIdByTriggerId);
        }

        return PaginationBuilder.build(pagination, triggers);
    }

    //查询所有的触发器
    @Override
    public List<Trigger> findTriggerAll() {
        List<TriggerEntity> triggerAll = triggerDao.findTriggerAll();
        return BeanMapper.mapList(triggerAll, Trigger.class);
    }

    //查询单个触发器
    @Override
    public Trigger findTriggerOne(String triggerId) {
        TriggerEntity trigger = triggerDao.findTriggerOne(triggerId);
        return BeanMapper.map(trigger, Trigger.class);
    }

    //根据ids查询触发器list
    @Override
    public List<Trigger> findTriggerList(List<String> ids) {
        List<TriggerEntity> triggerEntityList = triggerDao.findTriggerList(ids);
        return BeanMapper.mapList(triggerEntityList, Trigger.class);
    }

    /**
     * 添加触发器
     */
    @Override
    public String createTrigger(Trigger trigger) {
        try {
            TriggerEntity triggerEntity = BeanMapper.map(trigger, TriggerEntity.class);

            String triggerId = triggerDao.createTrigger(triggerEntity);

            List<String> mediumIds = trigger.getMediumIds();
            if (mediumIds != null && !mediumIds.isEmpty()) {
                triggerMediumService.createTriggerMedium(triggerId, mediumIds);

                HostDynamic hostDynamic = new HostDynamic();
                hostDynamic.setHostId(trigger.getHostId());
                hostDynamic.setName("创建触发器———" + trigger.getName());
                hostDynamic.setTime(ConversionDateUtil.date(9));
                hostDynamicService.createHostDynamic(hostDynamic);
            }
            return triggerId;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //删除触发器
    @Override
    public void deleteTriggerById(String id) {
        try {
            //先删除trigger表中的id
            triggerDao.deleteById(id);

            //再删除触发器表达式关系表当中的数据
            //triggerExpressionService.deleteByTriggerId(id);

            //删除告警表当中触发器的告警信息
            alarmService.deleteByTriggerId(id);

            //根据触发器id删除触发器和媒介中间表
            triggerMediumService.deleteByTriggerId(id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //修改触发器
    @Override
    public void updateTrigger(Trigger trigger) {
        TriggerEntity triggerEntity = BeanMapper.map(trigger, TriggerEntity.class);

        try {
            triggerDao.updateTrigger(triggerEntity);

            if (trigger.getMediumIds() != null && !trigger.getMediumIds().isEmpty()) {
                triggerMediumService.deleteByTriggerId(trigger.getId());
                triggerMediumService.createTriggerMedium(trigger.getId(), trigger.getMediumIds());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据主机id删除触发器
    @Override
    public void deleteByHostId(String hostId) {
        DeleteCondition deleteCondition = DeleteBuilders
                .createDelete(TriggerEntity.class)
                .eq("hostId", hostId)
                .get();
        triggerDao.deleteByHostId(deleteCondition);
    }

    //根据主机id查询触发器list
    @Override
    public List<Trigger> findTriggerListById(String hostId) {
        QueryCondition queryCondition = QueryBuilders
                .createQuery(TriggerEntity.class)
                .eq("hostId", hostId)
                .get();
        List<TriggerEntity> triggerList = triggerDao.findTriggerListById(queryCondition);
        return BeanMapper.mapList(triggerList, Trigger.class);
    }


    //定时拉取触发器,触发之后进行告警
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void insertAlarmForTrigger() {

        //将符合条件的触发器全部拉进来,进行判断(当前主机下根据当前监控项创建的触发器)
        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerEntity.class)
                .eq("state", 1)
                .get();
        List<TriggerEntity> triggers = triggerDao.findTriggerByHostIdAndMonitorId(queryCondition);

        if (triggers.isEmpty()) {
            return;
        }

        for (TriggerEntity trigger : triggers) {
            //三种触发方式
            switch (trigger.getScheme()) {
                case 1:
                    getLastValueTrigger(trigger);
                    break;
                case 2:
                    getAvgValueTrigger(trigger);
                    break;
                case 3:
                    getPercentValueTrigger(trigger);
                    break;
            }
        }
    }

    //百分比触发,计算一定时间内触发的占比超过指定数值
    private void getPercentValueTrigger(TriggerEntity trigger) {
        try {
            String flag;

            Alarm alarm = new Alarm();
            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

            if (StringUtils.isBlank(trigger.getExpression())) {
                return;
            }

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);
            List<History> informationList = historyService.findByHostTrigger(trigger.getHostId(), beforeTime);

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
                flag = "true";
            }

            //如果触发成功的话进行插入操作
            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(trigger.getHostId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(1);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarm(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    //平均值触发,计算在一定时间段内的平均值进行触发
    private void getAvgValueTrigger(TriggerEntity trigger) {
        try {

            String flag;

            Alarm alarm = new Alarm();

            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);

            List<History> informationList = historyService.findByHostTrigger(trigger.getHostId(), beforeTime);

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
                alarm.setHostId(trigger.getHostId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(1);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarm(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    //最近值触发,使用存储的最后一个数据进行触发
    private void getLastValueTrigger(TriggerEntity trigger) {
        String flag;

        Alarm alarm = new Alarm();
        alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());
        if (StringUtils.isBlank(trigger.getExpression())) {
            return;
        }

        String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);

        //根据触发器所在的数据库,将监控项指标全部查询出来,依次进行比对(查询20分钟内的数据)
        List<History> historyList = historyService.findByHostTrigger(trigger.getHostId(), beforeTime);

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
                flag = "true";
            }

            //如果触发成功的话进行插入操作
            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(trigger.getHostId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(1);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarm(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }


    //根据主机的id和表达式进行查询
    @Override
    public List<Trigger> findLikeTrigger(String hostId, String expression) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerEntity.class)
                .eq("hostId", hostId)
                .like("expression", expression)
                .get();

        List<TriggerEntity> entityList = triggerDao.findLikeTrigger(queryCondition);
        return BeanMapper.mapList(entityList, Trigger.class);
    }

    //查询触发器数量
    @Override
    public Long findTriggerAllNum() {
        return triggerDao.findTriggerAllNum();
    }
}
