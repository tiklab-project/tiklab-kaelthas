package io.thoughtware.kaelthas.host.template.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.host.host.model.HostTemplate;
import io.thoughtware.kaelthas.host.monitor.model.HostMonitor;
import io.thoughtware.kaelthas.host.monitor.model.MonitorQuery;
import io.thoughtware.kaelthas.host.template.model.Template;
import io.thoughtware.kaelthas.host.template.service.TemplateService;
import io.thoughtware.kaelthas.host.templateMonitor.model.TemplateMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 查询所有模板
     */
    @RequestMapping(path = "/findTemplateAll",method = RequestMethod.POST)
    public Result<List<Template>> findTemplateAll(){
        List<Template> resList = templateService.findTemplateAll();
        return Result.ok(resList);
    }

    /**
     * 查询主机下的模板,分页查询
     */
    @RequestMapping(value = "/findTemplate",method = RequestMethod.POST)
    public Result<Pagination<Template>> findTemplate(@RequestBody MonitorQuery query){
        Pagination<Template> resData = templateService.findTemplate(query);
        return Result.ok(resData);
    }


    /**
     * 添加模板到主机当中
     */
    @RequestMapping(value = "/addTemplate",method = RequestMethod.POST)
    public void addTemplateForMonitor(@RequestBody HostTemplate hostTemplate){
        templateService.addTemplate(hostTemplate);
    }

    /**
     * 移除主机当中的模板
     */
    @RequestMapping(value = "/removeTemplateForHost",method = RequestMethod.POST)
    public Result<?> removeTemplateForHost(@RequestBody HostTemplate hostTemplate){
        templateService.removeTemplate(hostTemplate);
        return Result.ok();
    }


    /**
     * 创建模板
     */
    @RequestMapping(value = "/createTemplate",method = RequestMethod.POST)
    public Result<String> createTemplate(@RequestBody Template template){
        String templateId = templateService.createTemplate(template);
        return Result.ok(templateId);
    }


    /**
     * 根据模板id删除模板
     */
    @RequestMapping(value = "/deleteTemplate",method = RequestMethod.POST)
    public void deleteTemplate(@NotNull String id){
        templateService.deleteTemplate(id);
    }

    /**
     * 修改模板
     */
    @RequestMapping(value = "/updateTemplate",method = RequestMethod.POST)
    public void updateTemplate(@RequestBody Template template){
        templateService.updateTemplate(template);
    }

    /**
     * 删除模板当中的监控项
     */
    @RequestMapping(path="/deleteTemplateMonitor",method = RequestMethod.POST)
    public Result<?> deleteEnvironment(@RequestBody TemplateMonitor templateMonitor){
        templateService.deleteTemplateMonitor(templateMonitor);
        return Result.ok();
    }

    /**
     * 修改模板当中的监控项
     */
    @RequestMapping(path="/updateTemplateMonitor",method = RequestMethod.POST)
    public Result<?> updateTemplateMonitor(@RequestBody @NotNull @Valid HostMonitor hostMonitor){
        templateService.updateTemplateMonitor(hostMonitor);
        return Result.ok();
    }

    /**
     * 向模板当中添加监控项
     */
    @RequestMapping(value = "/createTemplateMonitor",method = RequestMethod.POST)
    public Result<?> createTemplateMonitor(@RequestBody HostMonitor hostMonitor){
        templateService.createTemplateMonitor(hostMonitor);
        return Result.ok();
    }
}
