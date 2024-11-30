package io.tiklab.kaelthas.internet.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.setting.service.InSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 网络监控中的设置
 */
@RestController
@RequestMapping("/inSetting")
public class InSettingController {

    @Autowired
    private InSettingService inSettingService;

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/findInternetById",method = RequestMethod.POST)
    public Result<Internet> findInternetById(@NotNull String id){
        Internet internet = inSettingService.findInternetById(id);
        return Result.ok(internet);
    }

    /**
     * 删除网络
     */
    @RequestMapping(value = "/deleteInternet",method = RequestMethod.POST)
    public Result<?> deleteInternet(@NotNull String id){
        inSettingService.deleteInternet(id);
        return Result.ok();
    }

    /**
     * 修改网络
     */
    @RequestMapping(value = "/updateInternet",method = RequestMethod.POST)
    public Result<?> updateInternet(@RequestBody Internet internet){
        inSettingService.updateInternet(internet);
        return Result.ok();
    }
}
