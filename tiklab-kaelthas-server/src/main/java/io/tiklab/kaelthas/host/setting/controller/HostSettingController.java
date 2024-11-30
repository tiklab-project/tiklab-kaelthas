package io.tiklab.kaelthas.host.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.kaelthas.host.setting.service.HostSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 主机下的设置
 */
@RestController
@RequestMapping("/hostSetting")
public class HostSettingController {

    @Autowired
    private HostSettingService hostSettingService;

    /**
     * 修改主机信息
     */
    @RequestMapping(value = "/updateHost",method = RequestMethod.POST)
    public Result<String> updateHost(@RequestBody Host host){
        hostSettingService.updateHost(host);
        return Result.ok(null,"更新成功");
    }

    /**
     * 根据id删除主机
     */
    @RequestMapping(value = "/deleteHostById",method = RequestMethod.POST)
    public Result<?> deleteHostById(@NotNull String id){
        hostSettingService.deleteHostById(id);
        return Result.ok();
    }

    /**
     * 根据主机的id进行查询主机下监控项和触发器数量信息
     */
    @RequestMapping(value = "/findHostById",method = RequestMethod.POST)
    public Result<Host> findHostById(@NotNull String id){
        Host resHost = hostSettingService.findHostById(id);
        return Result.ok(resHost);
    }

    /**
     * 查询所有主机组
     */
    @RequestMapping(value = "/findAllHostGroupList",method = RequestMethod.POST)
    public Result<List<HostGroup>> findAllHostGroupList(){
        List<HostGroup> resData = hostSettingService.findAllHostGroupList();
        return Result.ok(resData);
    }
}
