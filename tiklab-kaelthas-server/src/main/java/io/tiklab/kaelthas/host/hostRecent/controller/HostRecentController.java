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

@RequestMapping("/hostRecent")
@RestController
public class HostRecentController {

    @Autowired
    HostRecentService hostRecentService;

    @RequestMapping(value = "/createHostRecent", method = RequestMethod.POST)
    public String createHostRecent(@RequestBody HostRecent hostRecent) {
        return hostRecentService.createHostRecent(hostRecent);
    }


    @RequestMapping(value = "/findHostRecentList", method = RequestMethod.POST)
    public Result<List<HostRecent>> findHostRecentList() {
        List<HostRecent> hostRecentList = hostRecentService.findHostRecentList();
        return Result.ok(hostRecentList);
    }

    @RequestMapping(value = "/updateHostRecent", method = RequestMethod.POST)
    public void updateHostRecent(@RequestBody HostRecent hostRecent) {
        hostRecentService.updateHostRecent(hostRecent);
    }
}

