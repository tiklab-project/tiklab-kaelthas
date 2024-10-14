package io.tiklab.kaelthas.host.templateMonitor.controller;


import io.tiklab.core.Result;

import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.templateMonitor.model.TemplateMonitor;
import io.tiklab.kaelthas.host.templateMonitor.service.TemplateMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 接口环境 控制器
 */
@RestController
@RequestMapping("/templateMonitor")
public class TemplateMonitorController {

    @Autowired
    private TemplateMonitorService templateMonitorService;

    /**
     * 删除模板当中的监控项
     */
    @RequestMapping(path="/deleteTemplateMonitor",method = RequestMethod.POST)
    public Result<?> deleteEnvironment(@RequestBody TemplateMonitor templateMonitor){
        templateMonitorService.deleteTemplateMonitor(templateMonitor);
        return Result.ok();
    }

    /**
     * 修改模板当中的监控项
     */
    @RequestMapping(path="/updateTemplateMonitor",method = RequestMethod.POST)
    public Result<Void> updateTemplateMonitor(@RequestBody @NotNull @Valid HostMonitor hostMonitor){
        templateMonitorService.updateTemplateMonitor(hostMonitor);
        return Result.ok();
    }

    /**
     *根据模板id查询模板下监控项信息
     */
    @RequestMapping(path="/findTemplateMonitorByTemplateId",method = RequestMethod.POST)
    public Result<List<TemplateMonitor>> findTemplateMonitorByTemplateId(@NotNull String templateId){
        List<TemplateMonitor> templateMonitors = templateMonitorService.findIdsByTemplateId(templateId);
        return Result.ok(templateMonitors);
    }

    /**
     * 向模板当中添加监控项
     */
    @RequestMapping(value = "/createTemplateMonitor",method = RequestMethod.POST)
    public Result<?> createTemplateMonitor(@RequestBody HostMonitor hostMonitor){
        templateMonitorService.createTemplateMonitor(hostMonitor);
        return Result.ok();
    }

}
