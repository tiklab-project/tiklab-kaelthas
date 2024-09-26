package io.thoughtware.kaelthas.internet.internetGraphicsMonitor.controller;

import io.thoughtware.core.Result;
import io.thoughtware.kaelthas.internet.internetGraphicsMonitor.service.InGraphicsMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/inGraphicsMonitor")
@RestController
public class InGraphicsMonitorController {

    @Autowired
    private InGraphicsMonitorService inGraphicsMonitorService;

    /**
     * 根据图形id查询出图形下的监控项ids
     */
    @RequestMapping(value = "/findMonitorIds",method = RequestMethod.POST)
    public Result<?> findMonitorIds(@NotNull String id){
        List<String> stringList = inGraphicsMonitorService.findMonitorIds(id);
        return Result.ok(stringList);
    }

}
