package io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.model.KuTrigger;
import io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.service.KuTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/kuTrigger")
public class KuTriggerController {

    @Autowired
    private KuTriggerService kuTriggerService;

    @RequestMapping(value = "/findKuTriggerPage",method = RequestMethod.POST)
    public Result<?> findKuTriggerPage(@RequestBody KuTrigger kuTrigger){
        Pagination<KuTrigger> pagination = kuTriggerService.findKuTriggerPage(kuTrigger);
        return Result.ok(pagination);
    }

    @RequestMapping(value = "/createKuTrigger",method = RequestMethod.POST)
    public Result<?> createKuTrigger(@RequestBody KuTrigger kuTrigger){
        String sid = kuTriggerService.createKuTrigger(kuTrigger);
        return Result.ok(sid);
    }

    @RequestMapping(value = "/deleteKuTrigger",method = RequestMethod.POST)
    public Result<?> deleteKuTrigger(@NotNull String id){
        kuTriggerService.deleteKuTrigger(id);
        return Result.ok();
    }

    @RequestMapping(value = "/updateKuTrigger",method = RequestMethod.POST)
    public Result<?> updateKuTrigger(@RequestBody KuTrigger kuTrigger){
        kuTriggerService.updateKuTrigger(kuTrigger);
        return Result.ok();
    }
}
