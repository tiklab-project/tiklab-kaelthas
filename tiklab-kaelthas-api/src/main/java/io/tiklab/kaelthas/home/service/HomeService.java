package io.tiklab.kaelthas.home.service;

import io.tiklab.kaelthas.alarm.model.Alarm;

import java.util.List;
import java.util.Map;

/**
 * 首页的业务
 */
public interface HomeService {
    /**
     * 根据主机id查询不同告警等级的数量
     */
    List<Alarm> findAlarmTypeNum(String hostId);

    /**
     * 查询信息的数量情况
     */
    Map<String, Long> findHostUsage();

    /**
     * 查询主机下的告警分布情况
     */
    List<Map<String, Object>> findTypeDistribution();

}
