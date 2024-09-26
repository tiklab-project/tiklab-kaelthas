package io.thoughtware.kaelthas.internet.internetTrigger.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.internet.internetTrigger.model.InTrigger;
import io.thoughtware.kaelthas.internet.internetTrigger.service.InTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RequestMapping("/inTrigger")
@RestController
public class InTriggerController {

    @Autowired
    private InTriggerService inTriggerService;

    /**
     * 分页查询触发器
     */
    @RequestMapping(value = "/findTriggerPage",method = RequestMethod.POST)
    public Result<?> findTriggerPage(@RequestBody InTrigger inTrigger){
        Pagination<InTrigger> pagination = inTriggerService.findTriggerPage(inTrigger);
        return Result.ok(pagination);
    }

    /**
     * 添加触发器
     */
    @RequestMapping(value = "/createTrigger",method = RequestMethod.POST)
    public Result<?> createInTrigger(@RequestBody InTrigger inTrigger){
        String string = inTriggerService.createInTrigger(inTrigger);
        return Result.ok(string);
    }

    /**
     * 删除触发器
     */
    @RequestMapping(value = "/deleteTrigger",method = RequestMethod.POST)
    public Result<?> deleteTrigger(@NotNull String id){
        inTriggerService.deleteTrigger(id);
        return Result.ok();
    }

    /**
     * 修改触发器
     */
    @RequestMapping(value = "/updateTrigger",method = RequestMethod.POST)
    public Result<?> updateTrigger(@RequestBody InTrigger inTrigger){
        inTriggerService.updateTrigger(inTrigger);
        return Result.ok();
    }

}
