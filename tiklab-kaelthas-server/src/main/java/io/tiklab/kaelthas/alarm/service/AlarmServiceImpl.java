package io.tiklab.kaelthas.alarm.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.alarm.entity.AlarmEntity;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.internet.service.InternetService;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.service.KubernetesService;
import io.tiklab.message.message.model.SendMessageNotice;
import io.tiklab.message.message.service.SendMessageNoticeService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import io.tiklab.kaelthas.alarm.dao.AlarmDao;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.common.util.ConversionDateUtil;
import io.tiklab.kaelthas.host.triggerMedium.service.TriggerMediumService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Exporter
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    AlarmDao alarmDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    TriggerMediumService triggerMediumService;

    @Autowired
    SendMessageNoticeService sendMessageNoticeService;

    @Autowired
    HostService hostService;

    @Autowired
    private DbInfoService dbInfoService;

    @Autowired
    private KubernetesService kubernetesService;

    @Autowired
    private InternetService internetService;

    @Value(value = "${base.url}")
    private String baseUrl;

    @Override
    public Pagination<Alarm> findAlarmPage(Alarm alarm) {
        return alarmDao.findAlarmPage(alarm);
    }

    @Override
    public void createAlarm(Alarm alarm) {
        try {
            AlarmEntity alarmEntity = BeanMapper.map(alarm, AlarmEntity.class);
            Host host = hostService.findHostById(alarm.getHostId());
            alarmEntity.setIp(host.getIp());
            alarmEntity.setName(host.getName());
            //插入之前应该查询是否已经存在数据,如果存在数据的话就修改告警持续时间
            QueryCondition queryCondition = QueryBuilders.createQuery(AlarmEntity.class)
                    .eq("triggerId", alarm.getTriggerId())
                    .eq("hostId", alarm.getHostId())
                    .eq("status", 2)
                    .get();

            List<AlarmEntity> alarmList = alarmDao.findListByHisId(queryCondition);

            if (alarmList == null || alarmList.isEmpty()) {
                String alarmId = alarmDao.createAlarm(alarmEntity);
                Alarm map = BeanMapper.map(alarmEntity, Alarm.class);
                map.setId(alarmId);
                //发送消息的模块,进行消息发送
                Map<String, Object> map1 = new HashMap<>();
                map1.put("hostName", host.getName());
                map1.put("alarmInformation", map.getSendMessage());
                sendMessage(map1);
            } else {
                //查询出已经存在的数据,根据当前时间计算出告警时长
                AlarmEntity alarm1 = alarmList.get(0);
                String gatherTime = alarm1.getAlertTime();

                if (StringUtils.isBlank(gatherTime)) {
                    alarm1.setDuration("没有数据,无法计算时间");
                } else {
                    //计算出相差的年月日时分秒
                    String string = ConversionDateUtil.formatToDateTime(gatherTime);
                    alarm1.setDuration(string);
                }
                //修改时间
                alarmDao.updateAlarm(alarm1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void createAlarmByDbMonitor(Alarm alarm) {
        AlarmEntity alarmEntity = BeanMapper.map(alarm, AlarmEntity.class);
        DbInfo dbInfo = dbInfoService.findDbInfoById(alarm.getHostId());
        alarmEntity.setIp(dbInfo.getIp());
        alarmEntity.setName(dbInfo.getName());
        //插入之前应该查询是否已经存在数据,如果存在数据的话就修改告警持续时间
        QueryCondition queryCondition = QueryBuilders.createQuery(AlarmEntity.class)
                .eq("triggerId", alarm.getTriggerId())
                .eq("hostId", alarm.getHostId())
                .eq("status", 2)
                .get();

        List<AlarmEntity> alarmList = alarmDao.findListByHisId(queryCondition);

        if (alarmList == null || alarmList.isEmpty()) {
            String alarmId = alarmDao.createAlarm(alarmEntity);
            Alarm map = BeanMapper.map(alarmEntity, Alarm.class);
            map.setId(alarmId);
            //发送消息的模块,进行消息发送
            Map<String, Object> map1 = new HashMap<>();
            map1.put("hostName", dbInfo.getName());
            map1.put("alarmInformation", map.getSendMessage());
            sendMessage(map1);
        } else {
            //查询出已经存在的数据,根据当前时间计算出告警时长
            AlarmEntity alarm1 = alarmList.get(0);
            String gatherTime = alarm1.getAlertTime();

            if (StringUtils.isBlank(gatherTime)) {
                alarm1.setDuration("没有数据,无法计算时间");
            } else {
                //计算出相差的年月日时分秒
                String string = ConversionDateUtil.formatToDateTime(gatherTime);
                alarm1.setDuration(string);
            }
            //修改时间
            alarmDao.updateAlarm(alarm1);
        }
    }

    @Override
    public void createAlarmForKubernetes(Alarm alarm) {
        AlarmEntity alarmEntity = BeanMapper.map(alarm, AlarmEntity.class);
        Kubernetes kuInfoById = kubernetesService.findKuInfoById(alarm.getHostId());
        alarmEntity.setIp(kuInfoById.getIp());
        alarmEntity.setName(kuInfoById.getName());
        //插入之前应该查询是否已经存在数据,如果存在数据的话就修改告警持续时间
        QueryCondition queryCondition = QueryBuilders.createQuery(AlarmEntity.class)
                .eq("triggerId", alarm.getTriggerId())
                .eq("hostId", alarm.getHostId())
                .eq("status", 2)
                .get();

        List<AlarmEntity> alarmList = alarmDao.findListByHisId(queryCondition);

        if (alarmList == null || alarmList.isEmpty()) {
            String alarmId = alarmDao.createAlarm(alarmEntity);
            Alarm map = BeanMapper.map(alarmEntity, Alarm.class);
            map.setId(alarmId);
            //发送消息的模块,进行消息发送
            Map<String, Object> map1 = new HashMap<>();
            map1.put("hostName", kuInfoById.getName());
            map1.put("alarmInformation", map.getSendMessage());
            sendMessage(map1);
        } else {
            //查询出已经存在的数据,根据当前时间计算出告警时长
            AlarmEntity alarm1 = alarmList.get(0);
            String gatherTime = alarm1.getAlertTime();

            if (StringUtils.isBlank(gatherTime)) {
                alarm1.setDuration("没有数据,无法计算时间");
            } else {
                //计算出相差的年月日时分秒
                String string = ConversionDateUtil.formatToDateTime(gatherTime);
                alarm1.setDuration(string);
            }
            //修改时间
            alarmDao.updateAlarm(alarm1);
        }
    }

    @Override
    public void createAlarmForInternet(Alarm alarm) {
        AlarmEntity alarmEntity = BeanMapper.map(alarm, AlarmEntity.class);
        Internet internetById = internetService.findInternetById(alarm.getHostId());
        alarmEntity.setIp(internetById.getIp());
        alarmEntity.setName(internetById.getName());
        //插入之前应该查询是否已经存在数据,如果存在数据的话就修改告警持续时间
        QueryCondition queryCondition = QueryBuilders.createQuery(AlarmEntity.class)
                .eq("triggerId", alarm.getTriggerId())
                .eq("hostId", alarm.getHostId())
                .eq("status", 2)
                .get();

        List<AlarmEntity> alarmList = alarmDao.findListByHisId(queryCondition);

        if (alarmList == null || alarmList.isEmpty()) {
            String alarmId = alarmDao.createAlarm(alarmEntity);
            Alarm map = BeanMapper.map(alarmEntity, Alarm.class);
            map.setId(alarmId);
            //发送消息的模块,进行消息发送
            Map<String, Object> map1 = new HashMap<>();
            map1.put("hostName", internetById.getName());
            map1.put("alarmInformation", map.getSendMessage());
            sendMessage(map1);
        } else {
            //查询出已经存在的数据,根据当前时间计算出告警时长
            AlarmEntity alarm1 = alarmList.get(0);
            String gatherTime = alarm1.getAlertTime();

            if (StringUtils.isBlank(gatherTime)) {
                alarm1.setDuration("没有数据,无法计算时间");
            } else {
                //计算出相差的年月日时分秒
                String string = ConversionDateUtil.formatToDateTime(gatherTime);
                alarm1.setDuration(string);
            }
            //修改时间
            alarmDao.updateAlarm(alarm1);
        }
    }

    private void sendMessage(Map<String, Object> map) {
        SendMessageNotice dispatchNotice = new SendMessageNotice();
        dispatchNotice.setId("b2ffa783e5a2");
        dispatchNotice.setSendId("111111");
        String jsonString = JSONObject.toJSONString(map);
        dispatchNotice.setQywechatData(jsonString);
        dispatchNotice.setSiteData(jsonString);
        dispatchNotice.setBaseUrl(baseUrl);
        String Link = (String) map.get("hostName");
        String pipeLineName = (String) map.get("alarmInformation");
        dispatchNotice.setLink(Link);
        dispatchNotice.setAction(pipeLineName);
        try {
            sendMessageNoticeService.sendMessageNotice(dispatchNotice);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByTriggerId(String triggerId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(AlarmEntity.class)
                .eq("triggerId", triggerId)
                .get();
        alarmDao.deleteByTriggerId(deleteCondition);
    }

    @Override
    public List<Alarm> findAlarmList(Alarm alarm) {
        QueryCondition queryCondition = QueryBuilders.createQuery(AlarmEntity.class)
                .eq("hostId", alarm.getHostId())
                .eq("status", alarm.getStatus())
                .get();
        List<AlarmEntity> entityList = alarmDao.findAlarmList(queryCondition);
        return BeanMapper.mapList(entityList, Alarm.class);
    }

    @Override
    public void updateAlarm(Alarm alarm) {
        String date = ConversionDateUtil.date(9);
        alarm.setResolutionTime(date);
        String string = ConversionDateUtil.formatToDateTime(alarm.getAlertTime());
        alarm.setDuration(string);
        alarmDao.updateAlarmState(alarm);
    }

    @Override
    public List<Alarm> findAlarmTypeNum(String hostId) {
        return alarmDao.findAlarmTypeNum(hostId);
    }

    @Override
    public Long findAlarmAllNum() {
        String beforeTenTime = ConversionDateUtil.findLocalDateTime(0, 240, null);
        return alarmDao.findAlarmAllNum(beforeTenTime);
    }

    @Override
    public Long findAlarmTimeNum() {
        //向前推十天的时间
        String beforeTenTime = ConversionDateUtil.findLocalDateTime(0, 240, null);
        return alarmDao.findAlarmTimeNum(beforeTenTime);
    }

    @Override
    public List<Map<String, Object>> findTypeDistribution() {
        String beforeTime = ConversionDateUtil.findLocalDateTime(0, 240, null);
        List<Map<String, Object>> typeDistribution = alarmDao.findTypeDistribution(beforeTime);
        return typeDistribution;
    }

    @Override
    public Map<String,Integer> findAlarmNumByCondition(Alarm alarm) {
        Map<String,Integer> map = new HashMap<>();

        QueryCondition queryCondition = QueryBuilders.createQuery(AlarmEntity.class)
                .eq("hostId", alarm.getHostId())
                .eq("status", 2)
                .get();
        List<AlarmEntity> alarmEntityList = alarmDao.findAlarmNumByCondition(queryCondition);
        map.put("alarmNum",alarmEntityList.size());
        return map;
    }
}
