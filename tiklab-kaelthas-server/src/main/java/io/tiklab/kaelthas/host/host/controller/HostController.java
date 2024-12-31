package io.tiklab.kaelthas.host.host.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
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
     * 根据主机的id进行查询主机下监控项和触发器数量信息
     */
    @RequestMapping(value = "/findOneHost",method = RequestMethod.POST)
    public Result<Host> findOneHost(@NotNull String id){
        Host resHost = hostService.findOneHost(id);
        return Result.ok(resHost);
    }

    /**
     * 根据id删除主机
     */
    @RequestMapping(value = "/deleteHostById",method = RequestMethod.POST)
    public Result<?> deleteHostById(@NotNull String id){
        hostService.deleteHostById(id);
        return Result.ok();
    }

    /**
     * 根据主机的ip地址查询主机下的所有监控项
     */
    @RequestMapping(value = "/findMonitorItemListByHostIp",method = RequestMethod.POST)
    public List<HostMonitor> findMonitorItemListByHostIp(@RequestBody Host host){
        List<HostMonitor> monitorList = hostService.findMonitorItemListByHostIp(host.getIp());
        return monitorList;
    }


    /**
     * 主机页面的分页查询
     */
    @RequestMapping(value = "/findHostPage",method = RequestMethod.POST)
    public Result<Pagination<Host>> findHostPage(@RequestBody Host host){
        Pagination<Host> pagination = hostService.findHostPage(host);
        return Result.ok(pagination);
    }

    /**
     * 查询所有主机组
     */
    @RequestMapping(value = "/findAllHostGroupList",method = RequestMethod.POST)
    public Result<List<HostGroup>> findAllHostGroupList(){
        List<HostGroup> resData = hostService.findAllHostGroupList();
        return Result.ok(resData);
    }

}
