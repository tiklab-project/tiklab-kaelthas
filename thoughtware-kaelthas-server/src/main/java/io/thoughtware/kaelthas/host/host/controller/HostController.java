package io.thoughtware.kaelthas.host.host.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.host.host.model.Host;
import io.thoughtware.kaelthas.host.host.service.HostService;
import io.thoughtware.kaelthas.host.monitor.model.HostMonitor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/hostList")
public class HostController {

    @Resource
    private HostService hostService;

    /**
     * 根据条件查询主机信息
     */
    @RequestMapping(path = "/findPageHost", method = RequestMethod.POST)
    public Result<Pagination<Host>> findPageHost(@RequestBody Host host) {
        Pagination<Host> pageHost = hostService.findPageHost(host);
        return Result.ok(pageHost);
    }


    /**
     * 创建主机
     */
    @RequestMapping(path = "/createHost", method = RequestMethod.POST)
    public Result<String> createHost(@RequestBody @NotNull @Valid Host host) {
        String res = hostService.createHost(host);
        return Result.ok(res);
    }

    /**
     * 修改主机信息
     */
    @RequestMapping(value = "/updateHost",method = RequestMethod.POST)
    public Result<String> updateHost(@RequestBody Host host){
        hostService.updateHost(host);
        return Result.ok(null,"更新成功");
    }

    /**
     * 根据主机的id进行查询主机下监控项和触发器数量信息
     */
    @RequestMapping(value = "/findHostById",method = RequestMethod.POST)
    public Result<Host> findHostById(@NotNull String id){
        Host resHost = hostService.findHostById(id);
        return Result.ok(resHost);
    }

    /**
     * 根据id删除主机
     */
    @RequestMapping(value = "/deleteHostById",method = RequestMethod.POST)
    public void deleteHostById(@NotNull String id){
        hostService.deleteHostById(id);
    }

    /**
     * 根据主机的ip地址查询主机下的所有监控项
     */
    @RequestMapping(value = "/findMonitorItemListByHostIp",method = RequestMethod.POST)
    public List<HostMonitor> findMonitorItemListByHostIp(@RequestBody Host host){
        List<HostMonitor> monitorList = hostService.findMonitorItemListByHostIp(host.getIp());
        return monitorList;
    }


    @RequestMapping(value = "/findHostPage",method = RequestMethod.POST)
    public Result<Pagination<Host>> findHostPage(@RequestBody Host host){
        Pagination<Host> pagination = hostService.findHostPage(host);
        return Result.ok(pagination);
    }

    /**
     * 根据主机下的监控项大类和主机id查询出主机下的监控项
     */
    @RequestMapping(value = "/findMonitorByCategories",method = RequestMethod.POST)
    public Result<List<HostMonitor>> findMonitorForHost(@RequestBody Host host){
        List<HostMonitor> monitors = hostService.findMonitorForHost(host);
        return Result.ok(monitors);
    }


    /**
     * 查询切换主机页面的
     */
    @RequestMapping(value = "/findRecentHostList",method = RequestMethod.POST)
    public Result<List<Host>> findRecentHostList(String hostId){
        List<Host> list = hostService.findRecentHostList(hostId);
        return Result.ok(list);
    }

}
