package io.tiklab.kaelthas.host.overview.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;
import io.tiklab.kaelthas.host.overview.service.HostOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 主机概况页
 */
@RestController
@RequestMapping("/hostOverview")
public class HostOverviewController {

    @Autowired
    private HostOverviewService hostOverviewService;

    /**
     * 主机概况的动态信息查询
     */
    @RequestMapping(value = "/findDynamicList",method = RequestMethod.POST)
    public Result<Pagination<HostDynamic>> findDynamicList(@RequestBody HostDynamic hostDynamic){
        Pagination<HostDynamic> dynamicList = hostOverviewService.findDynamicList(hostDynamic);
        return Result.ok(dynamicList);
    }

    /**
     * 根据主机的id进行查询主机下监控项和触发器数量信息
     */
    @RequestMapping(value = "/findHostById",method = RequestMethod.POST)
    public Result<Host> findHostById(@NotNull String id){
        Host resHost = hostOverviewService.findHostById(id);
        return Result.ok(resHost);
    }
}
