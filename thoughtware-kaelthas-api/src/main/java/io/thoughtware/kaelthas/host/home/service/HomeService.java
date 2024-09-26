package io.thoughtware.kaelthas.host.home.service;

import io.thoughtware.kaelthas.alarm.model.Alarm;

import java.util.List;
import java.util.Map;

public interface HomeService {
    List<Alarm> findAlarmTypeNum(String hostId);

    Map<String, Long> findHostUsage();

    List<Map<String, Object>> findTypeDistribution();

}
