package io.tiklab.kaelthas.host.home.service;

import io.tiklab.kaelthas.alarm.model.Alarm;

import java.util.List;
import java.util.Map;

public interface HomeService {
    List<Alarm> findAlarmTypeNum(String hostId);

    Map<String, Long> findHostUsage();

    List<Map<String, Object>> findTypeDistribution();

}
