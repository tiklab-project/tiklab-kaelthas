package io.tiklab.kaelthas.host.home.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.host.home.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    /**
     * 告警类型分布情况
     */
    @RequestMapping(value = "/findAlarmTypeNum",method = RequestMethod.POST)
    public Result<?> findAlarmTypeNum(String hostId){
        List<Alarm> mapList = homeService.findAlarmTypeNum(hostId);
        return Result.ok(mapList);
    }

    /**
     * 主机使用情况
     */
    @RequestMapping(value = "/findHostUsage",method = RequestMethod.POST)
    public Result<Map<String,Integer>> findHostUsage(){
        Map<String,Long> map = homeService.findHostUsage();
        return Result.ok(map);
    }

    /**
     * 查询出各种类型(现在只有主机,后面会增加)的告警情况
     */
    @RequestMapping(value = "/findTypeDistribution",method = RequestMethod.POST)
    public Result<List<Map<String, Object>>> findTypeDistribution(){
        List<Map<String, Object>> map = homeService.findTypeDistribution();
        return Result.ok(map);
    }
}
