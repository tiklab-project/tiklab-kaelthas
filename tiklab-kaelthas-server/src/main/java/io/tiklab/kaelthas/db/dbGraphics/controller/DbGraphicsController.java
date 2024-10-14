package io.tiklab.kaelthas.db.dbGraphics.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.dbGraphics.model.DbGraphics;
import io.tiklab.kaelthas.db.dbGraphics.service.DbGraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/dbGraphics")
public class DbGraphicsController {

    @Autowired
    private DbGraphicsService dbGraphicsService;

    @RequestMapping(value = "/findGraphicsPage",method = RequestMethod.POST)
    public Result<?> findGraphicsPage(@RequestBody DbGraphics dbGraphics){
        Pagination<DbGraphics> dbGraphicsPagination = dbGraphicsService.findGraphicsPage(dbGraphics);
        return Result.ok(dbGraphicsPagination);
    }

    @RequestMapping(value = "/createGraphics",method = RequestMethod.POST)
    public Result<?> createGraphics(@RequestBody DbGraphics dbGraphics){
        return Result.ok(dbGraphicsService.createGraphics(dbGraphics));
    }

    @RequestMapping(value = "/deleteGraphics",method = RequestMethod.POST)
    public void deleteGraphics(@NotNull String id){
        dbGraphicsService.deleteGraphics(id);
    }

    @RequestMapping(value = "/updateGraphics",method = RequestMethod.POST)
    public void updateGraphics(@RequestBody DbGraphics dbGraphics){
        dbGraphicsService.updateGraphics(dbGraphics);
    }

    @RequestMapping(value = "/findMonitorIds",method = RequestMethod.POST)
    public Result<List<String>> findMonitorIds(@RequestBody DbGraphics dbGraphics){
        List<String> ids = dbGraphicsService.findMonitorIds(dbGraphics.getId());
        return Result.ok(ids);
    }
}
