package io.thoughtware.kaelthas.host.trigger.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.host.trigger.model.Trigger;
import io.thoughtware.kaelthas.host.trigger.service.TriggerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/trigger")
public class TriggerController {

    @Resource
    private TriggerService triggerService;

    /**
     * 根据条件查询触发器
     */
    @RequestMapping(value = "/findTrigger",method = RequestMethod.POST)
    public Result<Pagination<Trigger>> findTrigger(@RequestBody Trigger trigger){

        Pagination<Trigger> triggerPagination = triggerService.findTrigger(trigger);
        return Result.ok(triggerPagination);
    }

    /**
     * 添加触发器
     */
    @RequestMapping(value = "/createTrigger",method = RequestMethod.POST)
    public Result<?> createTrigger(@RequestBody Trigger trigger){
        return Result.ok(triggerService.createTrigger(trigger));
    }

    /**
     * 删除触发器
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public Result<?> deleteById(@NotNull String id){
        triggerService.deleteTriggerById(id);
        return Result.ok();
    }

    /**
     * 修改触发器
     */
    @RequestMapping(value = "/updateTrigger",method = RequestMethod.POST)
    public void updateTrigger(@RequestBody Trigger trigger){
        triggerService.updateTrigger(trigger);
    }
}
