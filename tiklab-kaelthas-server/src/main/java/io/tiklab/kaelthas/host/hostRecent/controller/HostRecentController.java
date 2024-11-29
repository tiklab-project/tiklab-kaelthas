package io.tiklab.kaelthas.host.hostRecent.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.host.hostRecent.model.HostRecent;
import io.tiklab.kaelthas.host.hostRecent.service.HostRecentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 主机最近使用表,用于首页主机的显示(当前没有使用了),就算根据时间使用直接从主机当中拿就可以了,没有必要再创建一张表了,脑子真的是秀逗了
 */
@RequestMapping("/hostRecent")
@RestController
public class HostRecentController {

    @Autowired
    HostRecentService hostRecentService;

    /**
     * 创建临时表
     */
    @RequestMapping(value = "/createHostRecent", method = RequestMethod.POST)
    public String createHostRecent(@RequestBody HostRecent hostRecent) {
        return hostRecentService.createHostRecent(hostRecent);
    }

    /**
     * 查询主机最近使用表
     */
    @RequestMapping(value = "/findHostRecentList", method = RequestMethod.POST)
    public Result<List<HostRecent>> findHostRecentList() {
        List<HostRecent> hostRecentList = hostRecentService.findHostRecentList();
        return Result.ok(hostRecentList);
    }

    /**
     * 修改主机最近使用表
     */
    @RequestMapping(value = "/updateHostRecent", method = RequestMethod.POST)
    public void updateHostRecent(@RequestBody HostRecent hostRecent) {
        hostRecentService.updateHostRecent(hostRecent);
    }
}

