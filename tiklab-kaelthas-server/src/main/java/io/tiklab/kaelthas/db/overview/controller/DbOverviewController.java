package io.tiklab.kaelthas.db.overview.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;
import io.tiklab.kaelthas.db.overview.service.DbOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 数据库监控中的概况
 */
@RestController
@RequestMapping("/dbOverview")
public class DbOverviewController {

    @Autowired
    private DbOverviewService dbOverviewService;

    /**
     * 根据id查询数据源
     */
    @RequestMapping(value = "/findDbInfoById",method = RequestMethod.POST)
    public Result<?> findDbInfoById(@NotNull String id){
        DbInfo dbInfo = dbOverviewService.findDbInfoById(id);
        return Result.ok(dbInfo);
    }

    /**
     * 数据库监控的动态信息
     */
    @RequestMapping(value = "/findDynamicPage",method = RequestMethod.POST)
    public Result<?> findDynamicPage(@RequestBody DbDynamic dbDynamic){
        Pagination<DbDynamic> pagination = dbOverviewService.findDynamicPage(dbDynamic);
        return Result.ok(pagination);
    }
}
