package io.tiklab.kaelthas.alarm.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    /**
     *
     * 所有主机的告警信息
     * @param alarm
     * @return
     */
    @RequestMapping(value = "/findAlarmPage",method = RequestMethod.POST)
    public Result<?> findAlarmPage(@RequestBody Alarm alarm){
        Pagination<Alarm> alarmPagination = alarmService.findAlarmPage(alarm);
        return Result.ok(alarmPagination);
    }


    @RequestMapping(value = "/updateAlarm",method = RequestMethod.POST)
    public void updateAlarm(@RequestBody Alarm alarm){
        alarmService.updateAlarm(alarm);
    }


}
