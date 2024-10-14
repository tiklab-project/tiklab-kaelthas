package io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.model.KuGraphicsMonitor;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.service.KuGraphicsMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kuGraphicsMonitor")
public class KuGraphicsMonitorController {

    @Autowired
    private KuGraphicsMonitorService kuGraphicsMonitorService;

    /**
     * 根据graphicsId查询图形下的监控项ids
     */
    @RequestMapping(value = "/findMonitorIds",method = RequestMethod.POST)
    public Result<List<String>> findMonitorIds(@RequestBody KuGraphicsMonitor kuGraphicsMonitor){
        List<String> stringList  = kuGraphicsMonitorService.findMonitorIds(kuGraphicsMonitor);
        return Result.ok(stringList);
    }
}
