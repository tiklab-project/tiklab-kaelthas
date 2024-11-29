package io.tiklab.kaelthas.db.customize.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.customize.model.Customize;
import io.tiklab.kaelthas.db.customize.service.CustomizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 自定义SQL,没有开发
 */
@RestController
@RequestMapping("/customize")
public class CustomizeController {

    @Autowired
    private CustomizeService customizeService;

    /**
     * 分页查询自定义SQL的数据
     */
    @RequestMapping(value = "/findCustomizePage",method = RequestMethod.POST)
    public Result<?> findCustomizePage(@RequestBody Customize customize){
        Pagination<Customize> pagination = customizeService.findCustomizePage(customize);
        return Result.ok(pagination);
    }

    /**
     * 创建自定义SQL
     */
    @RequestMapping(value = "/createCustomize",method = RequestMethod.POST)
    public Result<String> createCustomize(@RequestBody Customize customize){
        String customizeId = customizeService.createCustomize(customize);
        return Result.ok(customizeId);
    }

    /**
     * 修改自定义SQL
     */
    @RequestMapping(value = "/updateCustomize",method = RequestMethod.POST)
    public void updateCustomize(@RequestBody Customize customize){
        customizeService.updateCustomize(customize);
    }

    /**
     * 删除自定义的SQL
     */
    @RequestMapping(value = "/deleteCustomize",method = RequestMethod.POST)
    public void deleteCustomize(@NotNull String id){
        customizeService.deleteCustomize(id);
    }

}
