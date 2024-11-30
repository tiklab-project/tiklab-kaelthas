package io.tiklab.kaelthas.db.trigger.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.trigger.model.DbTrigger;
import io.tiklab.kaelthas.db.trigger.service.DbTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/dbTrigger")
public class DbTriggerController {

    @Autowired
    private DbTriggerService dbTriggerService;

    @RequestMapping(value = "/findDbTriggerPage",method = RequestMethod.POST)
    public Result<Pagination<DbTrigger>> findDbTriggerPage(@RequestBody DbTrigger dbTrigger){
        Pagination<DbTrigger> pagination = dbTriggerService.findDbTriggerPage(dbTrigger);
        return Result.ok(pagination);
    }

    @RequestMapping(value = "/createDbTrigger",method = RequestMethod.POST)
    public Result<String> createDbTrigger(@RequestBody DbTrigger dbTrigger){
        String DbTriggerId = dbTriggerService.createDbTrigger(dbTrigger);
        return Result.ok(DbTriggerId);
    }

    @RequestMapping(value = "/deleteDbTrigger",method = RequestMethod.POST)
    public void deleteDbTrigger(@NotNull String id){
        dbTriggerService.deleteDbTrigger(id);
    }

    @RequestMapping(value = "/updateDbTrigger",method = RequestMethod.POST)
    public void updateDbTrigger(@RequestBody DbTrigger dbTrigger){
        dbTriggerService.updateDbTrigger(dbTrigger);
    }
}
