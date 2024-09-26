package io.thoughtware.kaelthas.host.home.service;

import io.thoughtware.kaelthas.alarm.model.Alarm;
import io.thoughtware.kaelthas.alarm.service.AlarmService;
import io.thoughtware.kaelthas.host.graphics.service.GraphicsService;
import io.thoughtware.kaelthas.host.host.service.HostService;
import io.thoughtware.kaelthas.host.hostGroup.service.HostGroupService;
import io.thoughtware.kaelthas.host.monitor.service.HostMonitorService;
import io.thoughtware.kaelthas.host.template.service.TemplateService;
import io.thoughtware.kaelthas.host.trigger.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HostService hostService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private TriggerService triggerService;

    @Autowired
    private HostMonitorService hostMonitorService;

    @Autowired
    private GraphicsService graphicsService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private HostGroupService hostGroupService;

    @Override
    public List<Alarm> findAlarmTypeNum(String hostId) {
        return alarmService.findAlarmTypeNum(hostId);
    }

    @Override
    public Map<String, Long> findHostUsage() {
        //查询主机总数和可用主机数
        Map<String, Long> longMap = hostService.findHostUsage();

        //查询告警数量
        Long alarmNum = alarmService.findAlarmAllNum();
        longMap.put("alarmNum",alarmNum);

        //查询触发器数量
        Long triggerNum = triggerService.findTriggerAllNum();
        longMap.put("triggerNum",triggerNum);

        //查询监控项数量
        Long monitorNum = hostMonitorService.findMonitorAllNum();
        longMap.put("monitorNum",monitorNum);

        //查询图形数量
        Long graphicsNum = graphicsService.findGraphicsAllNum();
        longMap.put("graphicsNum",graphicsNum);

        //查询模板数量
        Long templateNum = templateService.findTemplateAllNum();
        longMap.put("templateNum",templateNum);

        //查询主机组数量
        Long hostGroupNum = hostGroupService.findHostGroupAllNum();
        longMap.put("hostGroupNum",hostGroupNum);

        //查询最近十天告警数
        Long alarmTimeNum = alarmService.findAlarmTimeNum();
        longMap.put("alarmTimeNum",alarmTimeNum);

        return longMap;
    }

    @Override
    public List<Map<String, Object>> findTypeDistribution() {
        //查询主机下的告警分布情况
        return alarmService.findTypeDistribution();
    }
}
