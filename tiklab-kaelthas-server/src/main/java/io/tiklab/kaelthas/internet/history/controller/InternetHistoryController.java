package io.tiklab.kaelthas.internet.history.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.internet.history.model.InternetHistory;
import io.tiklab.kaelthas.internet.history.model.InternetHistoryQuery;
import io.tiklab.kaelthas.internet.history.service.InternetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internetHistory")
public class InternetHistoryController {

    @Autowired
    InternetHistoryService internetHistoryService;


    /**
     * 监控网络历史信息图表
     */
    @RequestMapping(value = "/findInGraphicsLine", method = RequestMethod.POST)
    public Result<List<List<InternetHistory>>> findGraphicsLine(@RequestBody InternetHistoryQuery internetHistoryQuery) {
        List<List<InternetHistory>> list = internetHistoryService.findGraphicsLine(internetHistoryQuery);
        return Result.ok(list);
    }
}
