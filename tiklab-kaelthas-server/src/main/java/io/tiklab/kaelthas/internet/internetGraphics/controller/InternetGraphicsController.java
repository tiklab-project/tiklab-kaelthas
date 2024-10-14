package io.tiklab.kaelthas.internet.internetGraphics.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.internetGraphics.model.InternetGraphics;
import io.tiklab.kaelthas.internet.internetGraphics.service.InternetGraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RequestMapping("/internetGraphics")
@RestController
public class InternetGraphicsController {


    @Autowired
    private InternetGraphicsService internetGraphicsService;

    /**
     * 分页查询图形
     */
    @RequestMapping(value = "/findGraphicsPage",method = RequestMethod.POST)
    public Result<?> findGraphicsPage(@RequestBody InternetGraphics internetGraphics){
        Pagination<InternetGraphics> pagination = internetGraphicsService.findGraphicsPage(internetGraphics);
        return Result.ok(pagination);
    }

    /**
     * 创建图形
     */
    @RequestMapping(value = "/createGraphics",method = RequestMethod.POST)
    public Result<?> createGraphics(@RequestBody InternetGraphics internetGraphics){
        String string = internetGraphicsService.createGraphics(internetGraphics);
        return Result.ok(string);
    }

    @RequestMapping(value = "/deleteGraphics",method = RequestMethod.POST)
    public Result<?> deleteGraphics(@NotNull String id){
        internetGraphicsService.deleteGraphics(id);
        return Result.ok();
    }

    /**
     * 修改图形
     */
    @RequestMapping(value = "/updateGraphics",method = RequestMethod.POST)
    public Result<?> updateGraphics(@RequestBody InternetGraphics internetGraphics){
        internetGraphicsService.updateGraphics(internetGraphics);
        return Result.ok();
    }
}
