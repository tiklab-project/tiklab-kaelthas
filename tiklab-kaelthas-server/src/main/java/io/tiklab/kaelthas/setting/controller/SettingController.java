package io.tiklab.kaelthas.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.kaelthas.host.monitor.model.MonitorQuery;
import io.tiklab.kaelthas.host.template.model.Template;
import io.tiklab.kaelthas.setting.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 设置中首页的请求
 */
@RequestMapping("/system")
@RestController
public class SettingController {

    @Autowired
    private SettingService settingService;

    /**
     * 设置中首页的页面数据
     */
    @RequestMapping(value = "/count",method = RequestMethod.POST)
    public Result<Map<String,Object>> count(){
        Map<String,Object> map = settingService.count();
        return Result.ok(map);
    }

    /**
     * 添加主机组
     */
    @RequestMapping(value = "/createHostGroup",method = RequestMethod.POST)
    public void createHostGroup(@RequestBody HostGroup hostGroup){
        settingService.createHostGroup(hostGroup);
    }

    /**
     * 主机组分页查询
     */
    @RequestMapping(value = "/findHostGroupPage",method = RequestMethod.POST)
    public Result<Pagination<HostGroup>> findHostGroupPage(@RequestBody HostGroup hostGroup){
        Pagination<HostGroup> pagination = settingService.findHostGroupPage(hostGroup);
        return Result.ok(pagination);
    }

    /**
     * 删除主机组
     */
    @RequestMapping(value = "/deleteHostGroup",method = RequestMethod.POST)
    public void deleteHostGroup(String id){
        settingService.deleteHostGroup(id);
    }

    /**
     * 修改主机组
     */
    @RequestMapping(value = "/updateHostGroup",method = RequestMethod.POST)
    public void updateHostGroup(@RequestBody HostGroup hostGroup){
        settingService.updateHostGroup(hostGroup);
    }

    /**
     * 查询主机下的模板,分页查询
     */
    @RequestMapping(value = "/findTemplate",method = RequestMethod.POST)
    public Result<Pagination<Template>> findTemplate(@RequestBody MonitorQuery query){
        Pagination<Template> resData = settingService.findTemplate(query);
        return Result.ok(resData);
    }

    @RequestMapping(value = "/findTemplateById",method = RequestMethod.POST)
    public Result<Template> findTemplateById(@NotNull String id){
       Template resData = settingService.findTemplateById(id);
        return Result.ok(resData);
    }

    /**
     * 创建模板
     */
    @RequestMapping(value = "/createTemplate",method = RequestMethod.POST)
    public Result<String> createTemplate(@RequestBody Template template){
        String templateId = settingService.createTemplate(template);
        return Result.ok(templateId);
    }

    /**
     * 修改模板
     */
    @RequestMapping(value = "/updateTemplate",method = RequestMethod.POST)
    public void updateTemplate(@RequestBody Template template){
        settingService.updateTemplate(template);
    }

    /**
     * 根据模板id删除模板
     */
    @RequestMapping(value = "/deleteTemplate",method = RequestMethod.POST)
    public void deleteTemplate(@NotNull String id){
        settingService.deleteTemplate(id);
    }


}

