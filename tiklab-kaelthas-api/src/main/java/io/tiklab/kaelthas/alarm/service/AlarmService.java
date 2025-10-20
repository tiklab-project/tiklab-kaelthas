package io.tiklab.kaelthas.alarm.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.alarm.model.AlarmQuery;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.alarm.model.Alarm;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 所有告警方法的实现
 */
@JoinProvider(model = Alarm.class)
public interface AlarmService {


    @FindOne
    Alarm findOne(@NotNull String id);

    /**
     * 条件查询列表
     * @param alarmQuery
     * @return
     */
    List<Alarm> findAlarmList(AlarmQuery alarmQuery);

    //分页查询
    Pagination<Alarm> findAlarmPage(Alarm alarm);

    //主机创建告警
    void createAlarm(Alarm alarm);

    /**
     * 根据触发器删除告警消息，删除触发器的时候会删除告警消息
     */
    void deleteByTriggerId(String triggerId);

    //根据条件查询告警的list，实际上是查询告警的数量
    List<Alarm> findAlarmList(Alarm alarm);

    //改变告警的状态，未解决->已解决
    void updateAlarm(Alarm alarm);

    //根据主机id查询不同告警等级的数量
    List<Alarm> findAlarmTypeNum(String hostId);

    //查询未解决告警的总数量
    Long findAlarmAllNum();

    //查询最近十天告警数
    Long findAlarmTimeNum();

    //查询主机下的告警分布情况
    List<Map<String, Object>> findTypeDistribution();

    //数据库监控创建告警
    void createAlarmByDbMonitor(Alarm alarm);

    //k8s监控创建告警
    void createAlarmForKubernetes(Alarm alarm);

    //网络设备触发之后发送告警消息
    void createAlarmForInternet(Alarm alarm);

    //根据ip分类查询出告警总数,已解决告警数量,未解决告警数量
    Map<String,Integer> findAlarmNumByCondition(Alarm alarm);
}