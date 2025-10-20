package io.tiklab.kaelthas.kubernetes.trigger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.db.agent.utils.AgentSqlUtil;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import io.tiklab.kaelthas.kubernetes.history.service.KubernetesHistoryService;
import io.tiklab.kaelthas.kubernetes.trigger.model.KuTriggerQuery;
import io.tiklab.kaelthas.util.ConversionScriptsUtils;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.util.StringUtil;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.kubernetes.trigger.dao.KuTriggerDao;
import io.tiklab.kaelthas.kubernetes.trigger.entity.KuTriggerEntity;
import io.tiklab.kaelthas.kubernetes.trigger.model.KuTrigger;
import io.tiklab.kaelthas.kubernetes.triggerMedium.service.KuTriggerMediumService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static io.tiklab.kaelthas.util.StringUtil.*;

/**
 * k8s监控中的触发器
 */
@Service
public class KuTriggerServiceImpl implements KuTriggerService {

    @Autowired
    private KuTriggerDao kuTriggerDao;

    @Autowired
    private KuTriggerMediumService kuTriggerMediumService;

    @Autowired
    private ConversionScriptsUtils conversionScriptsUtils;

    @Autowired
    private AlarmService alarmService;


    @Autowired
    private KubernetesHistoryService kubernetesHistoryService;


    //触发器分页查询
    @Override
    public Pagination<KuTrigger> findKuTriggerPage(KuTrigger kuTrigger) {
        return kuTriggerDao.findKuTriggerPage(kuTrigger);
    }

    //创建触发器
    @Override
    public String createKuTrigger(KuTrigger kuTrigger) {

        try {
            if (kuTrigger.getMediumType().isEmpty()) {
                return null;
            }
            KuTriggerEntity entity = BeanMapper.map(kuTrigger, KuTriggerEntity.class);
            String triggerId = kuTriggerDao.createKuTrigger(entity);
            kuTriggerMediumService.createKuTriggerMedium(triggerId, kuTrigger.getMediumType());
            return triggerId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //根据触发器id删除触发器
    @Override
    public void deleteKuTrigger(String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return;
            }
            kuTriggerDao.deleteKuTrigger(id);
            kuTriggerMediumService.deleteByTriggerId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //修改触发器
    @Override
    public void updateKuTrigger(KuTrigger kuTrigger) {
        KuTriggerEntity entity = BeanMapper.map(kuTrigger, KuTriggerEntity.class);
        kuTriggerDao.updateKuTrigger(entity);
    }

    //根据集群的id删除触发器
    @Override
    public void deleteKuTriggerByKuId(String id) {
        if (StringUtils.isBlank(id)) {
            return;
        }

        //查询触发器的ids
        List<KuTrigger> triggerEntities = this.findKuTriggerByKuId(id);
        //获取触发器的ids
        List<String> stringList = triggerEntities.stream().map(KuTrigger::getId).collect(Collectors.toList());
        //删除触发器的关联表信息
        kuTriggerMediumService.deleteByTriggerIds(stringList);
        //根据kuId删除触发器信息
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuTriggerEntity.class)
                .eq("kuId", id)
                .get();
        kuTriggerDao.deleteKuTriggerByKuId(deleteCondition);
    }

    //根据k8s监控id查询触发器list
    @Override
    public List<KuTrigger> findKuTriggerByKuId(String id) {
        List<KuTriggerEntity> kuTriggerByKuId = kuTriggerDao.findKuTriggerByKuId(id);
        return BeanMapper.mapList(kuTriggerByKuId, KuTrigger.class);
    }

    //定时任务,定时使用触发器进行告警
    //@Scheduled(cron = "0 0/5 * * * ? ")
    public void TimerKuTrigger() {
        //查询k8s下启动中的触发器
        List<KuTriggerEntity> kuTriggerEntityList = kuTriggerDao.findKuTriggerList(new KuTriggerQuery().setState(1));
        if (kuTriggerEntityList.isEmpty()) {
            return;
        }
        for (KuTriggerEntity trigger : kuTriggerEntityList) {
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

    //最近值触发,使用存储的最后一个数据进行触发
    private void getLastValueTrigger(KuTriggerEntity trigger) {
        try {
            String flag;

            Alarm alarm = new Alarm();
            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());
            if (StringUtils.isBlank(trigger.getExpression())) {
                return;
            }

            //通过k8sID、时间、表达式查询监控历史数据(查询20分钟内的数据)
            String triggerExData = conversionScriptsUtils.getTriggerExData(trigger.getExpression());
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);
            List<KubernetesHistory> historyList = kubernetesHistoryService.findKuHistoryByKuId(trigger.getKuId(), beforeTime,triggerExData);

            if (historyList.isEmpty()) {
                return;
            }

            //排序
            List<KubernetesHistory> list = historyList.stream().sorted(Comparator.comparing(KubernetesHistory::getGatherTime).reversed()).collect(Collectors.toList());


            //将触发器的表达式和数值进行替换,看是否能够进行触发

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //将数据换成JSON的字符串
            String json = StringUtil.getKuString(list.get(0));

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
                flag = "true";
            }

            //如果触发成功的话进行插入操作
            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(trigger.getKuId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(3);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarmForKubernetes(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    //平均值触发,计算在一定时间段内的平均值进行触发
    private void getAvgValueTrigger(KuTriggerEntity trigger) {
        try {

            String flag;

            Alarm alarm = new Alarm();
            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

            if (StringUtils.isBlank(trigger.getExpression())) {
                return;
            }

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //通过k8sID、时间、表达式查询监控历史数据(查询20分钟内的数据)
            String triggerExData = conversionScriptsUtils.getTriggerExData(trigger.getExpression());
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);
            List<KubernetesHistory> informationList = kubernetesHistoryService.findKuHistoryByKuId(trigger.getKuId(), beforeTime,triggerExData);

            if (informationList.isEmpty()) {
                return;
            }

            //根据监控项id进行分组,然后进行计算平均值
            Collection<List<KubernetesHistory>> values1 = informationList.stream().collect(Collectors.groupingBy(KubernetesHistory::getKuMonitorId)).values();
            String avgNumber = getKuAvgNumber(values1);

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
                alarm.setHostId(trigger.getKuId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(3);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarmForKubernetes(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    //百分比触发,计算一定时间内触发的占比超过指定数值
    private void getPercentValueTrigger(KuTriggerEntity trigger) {
        try {
            Alarm alarm = new Alarm();

            alarm.setAlertTime(AgentSqlUtil.getDataTimeNow());

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            //通过k8sID、时间、表达式查询监控历史数据(查询20分钟内的数据)
            String triggerExData = conversionScriptsUtils.getTriggerExData(trigger.getExpression());
            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);
            List<KubernetesHistory> informationList = kubernetesHistoryService.findKuHistoryByKuId(trigger.getKuId(), beforeTime,triggerExData);

            Collection<List<KubernetesHistory>> values = informationList.stream().collect(Collectors.groupingBy(KubernetesHistory::getGatherTime)).values();

            if (values.isEmpty()) {
                return;
            }
            BigDecimal total = new BigDecimal(values.size());

            BigDecimal triggerNum = new BigDecimal(0);


            for (List<KubernetesHistory> value : values) {
                if (value.isEmpty()) {
                    continue;
                }
                String strJson = StringUtil.getKuListString(value);
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
                            triggerNum = triggerNum.add(BigDecimal.ONE);
                        }
                    } catch (ScriptException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            BigDecimal divide = triggerNum.divide(total, 2, RoundingMode.DOWN).multiply(BigDecimal.valueOf(100));

            if (divide.compareTo(BigDecimal.valueOf(trigger.getPercentage())) >= 0) {
                alarm.setStatus(2);
                alarm.setHostId(trigger.getKuId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(3);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarmForKubernetes(alarm);
            }
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

}
