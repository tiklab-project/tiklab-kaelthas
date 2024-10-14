package io.tiklab.kaelthas.internet.internet.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.internet.service.InternetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/internet")
public class InternetController {

    @Autowired
    private InternetService internetService;

    /**
     * 查询网络列表
     */
    @RequestMapping(value = "/findInternetPage",method = RequestMethod.POST)
    public Result<?> findInternetPage(@RequestBody Internet internet){
        Pagination<Internet> pagination = internetService.findInternetPage(internet);
        return Result.ok(pagination);
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/findInternetById",method = RequestMethod.POST)
    public Result<Internet> findInternetById(@NotNull String id){
        Internet internet = internetService.findInternetById(id);
        return Result.ok(internet);
    }

    /**
     * 添加网络
     */
    @RequestMapping(value = "/createInternet",method = RequestMethod.POST)
    public Result<?> createInternet(@RequestBody Internet internet){
        String string = internetService.createInternet(internet);
        return Result.ok(string);
    }

    /**
     * 删除网络
     */
    @RequestMapping(value = "/deleteInternet",method = RequestMethod.POST)
    public Result<?> deleteInternet(@NotNull String id){
        internetService.deleteInternet(id);
        return Result.ok();
    }

    /**
     * 修改网络
     */
    @RequestMapping(value = "/updateInternet",method = RequestMethod.POST)
    public Result<?> updateInternet(@RequestBody Internet internet){
        internetService.updateInternet(internet);
        return Result.ok();
    }
}
