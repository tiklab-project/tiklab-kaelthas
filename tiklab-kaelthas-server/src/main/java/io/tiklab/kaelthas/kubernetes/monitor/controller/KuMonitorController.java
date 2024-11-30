package io.tiklab.kaelthas.kubernetes.monitor.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.monitor.model.KuMonitor;
import io.tiklab.kaelthas.kubernetes.monitor.service.KuMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/kuMonitor")
public class KuMonitorController {

    @Autowired
    private KuMonitorService kuMonitorService;

    /**
     * 分页查询监控表
     */
    @RequestMapping(value = "/findKuMonitorPage",method = RequestMethod.POST)
    public Result<?> findKuMonitorPage(@RequestBody KuMonitor kuMonitor){
        Pagination<KuMonitor> pagination = kuMonitorService.findKuMonitorPage(kuMonitor);
        return Result.ok(pagination);
    }

    /**
     * 创建监控项
     */
    @RequestMapping(value = "/createKuMonitor",method = RequestMethod.POST)
    public Result<String> createKuMonitor(@RequestBody KuMonitor kuMonitor){
        String id = kuMonitorService.createKuMonitor(kuMonitor);
        return Result.ok(id);
    }

    /**
     * 删除监控项
     */
    @RequestMapping(value = "/deleteKuMonitor",method = RequestMethod.POST)
    public Result<?> deleteKuMonitor(@NotNull String id){
        kuMonitorService.deleteKuMonitor(id);
        return Result.ok();
    }

    /**
     * 修改监控项
     */
    @RequestMapping(value = "/updateKuMonitor",method = RequestMethod.POST)
    public Result<?> updateKuMonitor(@RequestBody KuMonitor kuMonitor){
        kuMonitorService.updateKuMonitor(kuMonitor);
        return Result.ok();
    }

    @RequestMapping(value = "/findAllKuMonitor",method = RequestMethod.POST)
    public Result<List<KuMonitor>> findAllKuMonitor(@NotNull String kuId){
        List<KuMonitor> kuMonitorList = kuMonitorService.findAllKuMonitor(kuId);
        return Result.ok(kuMonitorList);
    }
}
