package io.tiklab.kaelthas.host.history.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.host.history.model.HostHistory;
import io.tiklab.kaelthas.host.history.model.HostHistoryQuery;
import io.tiklab.kaelthas.host.history.service.HostHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hostHistory")
public class HostHistoryController {

    @Autowired
    HostHistoryService hostHistoryService;

    /**
     * 根据多个监控项信息和时间查询上报数据,并且返回数据
     */
    @RequestMapping(value = "/findInformationByGraphics", method = RequestMethod.POST)
    public Result<List<List<HostHistory>>> findInformationByGraphics(@RequestBody HostHistoryQuery hostHistoryQuery) {
        List<List<HostHistory>> informationList = hostHistoryService.findInformationByGraphics(hostHistoryQuery);
        return Result.ok(informationList);
    }
}
