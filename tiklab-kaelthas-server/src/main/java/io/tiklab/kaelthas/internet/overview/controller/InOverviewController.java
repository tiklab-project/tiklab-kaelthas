package io.tiklab.kaelthas.internet.overview.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.internet.overview.service.InOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 网络监控概况页
 */
@RestController
@RequestMapping("/inOverview")
public class InOverviewController {

    @Autowired
    private InOverviewService inOverviewService;

    /**
     * 获取网络详情
     */
    @RequestMapping(value = "/findInternetOverview",method = RequestMethod.POST)
    public Result<?> findInternetOverview(@NotNull String internetId){
        Map<String,Object> objectMap = inOverviewService.findInternetOverview(internetId);
        return Result.ok(objectMap);
    }
}
