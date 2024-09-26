package io.thoughtware.kaelthas.host.hostGroup.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.host.hostGroup.model.HostGroup;
import io.thoughtware.kaelthas.host.hostGroup.service.HostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hostGroup")
public class HostGroupController {

    @Autowired
    private HostGroupService hostGroupService;

    /**
     * 根据名称查询主机群组
     */
    @RequestMapping(value = "/findHostGroupByName",method = RequestMethod.POST)
    public Result<List<HostGroup>> findHostGroupByName(){
        List<HostGroup> resData = hostGroupService.findHostGroupByName();
        return Result.ok(resData);
    }

    /**
     * 查询所有主机组
     */
    @RequestMapping(value = "/findAllHostGroupList",method = RequestMethod.POST)
    public Result<List<HostGroup>> findAllHostGroupList(){
        List<HostGroup> resData = hostGroupService.findAllHostGroupList();
        return Result.ok(resData);
    }

    /**
     * 添加主机组
     */
    @RequestMapping(value = "/createHostGroup",method = RequestMethod.POST)
    public void createHostGroup(@RequestBody HostGroup hostGroup){
        hostGroupService.createHostGroup(hostGroup);
    }

    @RequestMapping(value = "/findHostGroupPage",method = RequestMethod.POST)
    public Result<Pagination<HostGroup>> findHostGroupPage(@RequestBody HostGroup hostGroup){
        Pagination<HostGroup> pagination = hostGroupService.findHostGroupPage(hostGroup);
        return Result.ok(pagination);
    }

    @RequestMapping(value = "/deleteHostGroup",method = RequestMethod.POST)
    public void deleteHostGroup(String id){
        hostGroupService.deleteHostGroup(id);
    }

    @RequestMapping(value = "/updateHostGroup",method = RequestMethod.POST)
    public void updateHostGroup(@RequestBody HostGroup hostGroup){
        hostGroupService.updateHostGroup(hostGroup);
    }

}
