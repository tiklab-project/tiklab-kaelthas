package io.thoughtware.kaelthas.alarm.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.alarm.model.Alarm;

import java.util.List;
import java.util.Map;

/**
* 接口环境 服务接口
*/
@JoinProvider(model = Alarm.class)
public interface AlarmService {

    Pagination<Alarm> findAlarmPage(Alarm alarm);

    void createAlarm(Alarm alarm);

    void deleteByTriggerId(String triggerId);

    List<Alarm> findAlarmList(Alarm alarm);

    void updateAlarm(Alarm alarm);

    List<Alarm> findAlarmTypeNum(String hostId);

    //查询未解决告警的总数量
    Long findAlarmAllNum();

    //查询最近十天告警数
    Long findAlarmTimeNum();

    List<Map<String, Object>> findTypeDistribution();

    void createAlarmByDbMonitor(Alarm alarm);

    void createAlarmForKubernetes(Alarm alarm);
}