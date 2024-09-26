package io.thoughtware.kaelthas.host.monitor.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.host.monitor.model.HostMonitor;
import io.thoughtware.kaelthas.host.monitorItem.model.MonitorItem;
import io.thoughtware.kaelthas.host.monitor.service.HostMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/monitor")
public class HostMonitorController {

    @Autowired
    private HostMonitorService monitorService;


    /**
     * 根据条件查询监控项
     */
    @RequestMapping(value = "/findMonitorCondition" ,method = RequestMethod.POST)
    public Result<Pagination<HostMonitor>> findMonitorPagination(@RequestBody HostMonitor hostMonitor){
        Pagination<HostMonitor> resData = monitorService.findMonitorPage(hostMonitor);
        return Result.ok(resData);
    }


    /**
     * 查询主机下所有的监控项
     */
    @RequestMapping(value = "/findAllMonitor",method = RequestMethod.POST)
    public Result<List<HostMonitor>> findAllMonitor(@RequestBody HostMonitor hostMonitor){
        return Result.ok(monitorService.findAllMonitor(hostMonitor));
    }


    /**
     * 根据监控类型查询MonitorItem中的监控项
     */
    @RequestMapping(value = "/findMonitorItemByName",method = RequestMethod.POST)
    public Result<List<MonitorItem>> findMonitorItemByName(@NotNull String name){
        List<MonitorItem> resData = monitorService.findMonitorItemByName(name);
        return Result.ok(resData);
    }


    /**
     * 创建监控项
     */
    @RequestMapping(value = "/createMonitor",method = RequestMethod.POST)
    public Result<String> addMonitor(@RequestBody HostMonitor monitor){
        return Result.ok(monitorService.createMonitor(monitor));
    }


    /**
     * 修改监控项
     */
    @RequestMapping(value = "/updateMonitor",method = RequestMethod.POST)
    public void updateMonitor(@RequestBody HostMonitor monitor){
        monitorService.updateMonitor(monitor);
    }


    /**
     * 删除监控项
     */
    @RequestMapping(value = "/deleteMonitorById",method = RequestMethod.POST)
    public Result<?> deleteMonitorById(@NotNull String id){
        monitorService.deleteMonitorById(id);
        return Result.ok();
    }


    /**
     * 根据模板id查询监控项信息
     */
    @RequestMapping(value = "/findMonitorByTemplateId",method = RequestMethod.POST)
    public Result<Pagination<HostMonitor>> findMonitorByTemplateId(@RequestBody HostMonitor hostMonitor){
        Pagination<HostMonitor> monitorList = monitorService.findMonitorByTemplateId(hostMonitor);
        return Result.ok(monitorList);
    }


    /**
     * 查询所有监控项选择
     */
    @RequestMapping(value = "/findMonitorItemAll",method = RequestMethod.POST)
    public Result<List<MonitorItem>> findMonitorItemAll(){
        List<MonitorItem> monitorItems = monitorService.findMonitorItemAll();
        return Result.ok(monitorItems);
    }

}
