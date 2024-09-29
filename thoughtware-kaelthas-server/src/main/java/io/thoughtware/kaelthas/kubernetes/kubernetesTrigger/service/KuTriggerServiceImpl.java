package io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.collection.util.SqlUtil;
import io.thoughtware.kaelthas.common.javascripts.ConversionScriptsUtils;
import io.thoughtware.kaelthas.common.util.ConversionDateUtil;
import io.thoughtware.kaelthas.alarm.model.Alarm;
import io.thoughtware.kaelthas.alarm.service.AlarmService;
import io.thoughtware.kaelthas.common.util.StringUtil;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.history.service.HistoryService;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.dao.KuTriggerDao;
import io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.entity.KuTriggerEntity;
import io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.model.KuTrigger;
import io.thoughtware.kaelthas.kubernetes.kubernetesTriggerMedium.service.KuTriggerMediumService;
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

import static io.thoughtware.kaelthas.common.util.StringUtil.*;

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
    private HistoryService historyService;

    @Override
    public Pagination<KuTrigger> findKuTriggerPage(KuTrigger kuTrigger) {
        return kuTriggerDao.findKuTriggerPage(kuTrigger);
    }

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
        List<String> stringList = triggerEntities.stream().map(KuTrigger::getId).toList();
        //删除触发器的关联表信息
        kuTriggerMediumService.deleteByTriggerIds(stringList);
        //根据kuId删除触发器信息
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuTriggerEntity.class)
                .eq("kuId", id)
                .get();
        kuTriggerDao.deleteKuTriggerByKuId(deleteCondition);
    }

    @Override
    public List<KuTrigger> findKuTriggerByKuId(String id) {
        QueryCondition queryCondition = QueryBuilders.createQuery(KuTriggerEntity.class)
                .eq("kuId", id)
                .get();
        List<KuTriggerEntity> kuTriggerByKuId = kuTriggerDao.findKuTriggerByKuId(queryCondition);
        return BeanMapper.mapList(kuTriggerByKuId, KuTrigger.class);
    }

    @Scheduled(cron = "* */2 * * * * ")
    public void TimerKuTrigger() {
        List<KuTriggerEntity> kuTriggerEntityList = kuTriggerDao.findAllTrigger();
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

    private void getLastValueTrigger(KuTriggerEntity trigger) {
        try {
            String flag;

            Alarm alarm = new Alarm();
            alarm.setAlertTime(SqlUtil.getDataTimeNow());
            if (StringUtils.isBlank(trigger.getExpression())) {
                return;
            }

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, 20, null);

            //根据触发器所在的数据库,将监控项指标全部查询出来,依次进行比对(查询20分钟内的数据)
            List<History> historyList = historyService.findKuHistoryByHostId(trigger.getKuId(), beforeTime);

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
                flag = "true";
            }

            //如果触发成功的话进行插入操作
            if ("true".equals(flag)) {
                alarm.setStatus(2);
                alarm.setHostId(trigger.getKuId());
                alarm.setTriggerId(trigger.getId());
                alarm.setSendMessage(trigger.getDescribe());
                alarm.setMachineType(2);
                alarm.setSeverityLevel(trigger.getSeverityLevel());
                alarmService.createAlarmForKubernetes(alarm);
            }

        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAvgValueTrigger(KuTriggerEntity trigger) {
        try {

            String flag;

            Alarm alarm = new Alarm();
            alarm.setAlertTime(SqlUtil.getDataTimeNow());

            if (StringUtils.isBlank(trigger.getExpression())) {
                return;
            }

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);
            List<History> informationList = historyService.findKuHistoryByHostId(trigger.getKuId(), beforeTime);

            if (informationList.isEmpty()) {
                return;
            }

            //根据监控项id进行分组,然后进行计算平均值
            Collection<List<History>> values1 = informationList.stream().collect(Collectors.groupingBy(History::getMonitorId)).values();
            String avgNumber = getAvgNumber(values1);

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

    private void getPercentValueTrigger(KuTriggerEntity trigger) {
        try {
            Alarm alarm = new Alarm();

            alarm.setAlertTime(SqlUtil.getDataTimeNow());

            ScriptEngine engine = conversionScriptsUtils.getScriptEngine();

            String beforeTime = ConversionDateUtil.findLocalDateTime(2, trigger.getRangeTime(), null);

            List<History> informationList = historyService.findKuHistoryByHostId(trigger.getKuId(), beforeTime);

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

                String strJson = StringUtil.getString(value);
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

            if (divide.compareTo(BigDecimal.valueOf(trigger.getPercentage())) <= 0) {
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
