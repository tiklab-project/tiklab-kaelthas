package io.tiklab.kaelthas.db.history.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.db.history.model.DbHistoryQuery;
import io.tiklab.kaelthas.db.history.service.DbHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dbHistory")
public class DbHistoryController {

    @Autowired
    DbHistoryService dbHistoryService;


    /**
     * 监控数据库历史数据图表
     */
    @RequestMapping(value = "/findGraphicsLine", method = RequestMethod.POST)
    public Result<List<List<DbHistory>>> findGraphicsLine(@RequestBody DbHistoryQuery dbHistoryQuery) {
        List<List<DbHistory>> list = dbHistoryService.findGraphicsLine(dbHistoryQuery);
        return Result.ok(list);
    }
}
