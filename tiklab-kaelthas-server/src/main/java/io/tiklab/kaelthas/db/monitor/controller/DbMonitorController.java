package io.tiklab.kaelthas.db.monitor.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.item.model.DbItem;
import io.tiklab.kaelthas.db.monitor.model.DbMonitor;
import io.tiklab.kaelthas.db.monitor.service.DbMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/dbMonitor")
public class DbMonitorController {

    @Autowired
    private DbMonitorService dbMonitorService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "/findDbMonitorPage",method = RequestMethod.POST)
    public Result<?> findDbMonitorPage(@RequestBody DbMonitor dbMonitor){
        Pagination<DbMonitor> pagination = dbMonitorService.findDbMonitorPage(dbMonitor);
        return Result.ok(pagination);
    }

    /**
     * 根据监控数据库id查询监控项
     */
    @RequestMapping(value = "/findAllMonitor",method = RequestMethod.POST)
    public Result<?> findAllMonitor(@RequestBody DbMonitor dbMonitor){
        List<DbMonitor> monitorList = dbMonitorService.findAllMonitor(dbMonitor);
        return Result.ok(monitorList);
    }

    /**
     * 添加监控项
     */
    @RequestMapping(value = "/createDbMonitor",method = RequestMethod.POST)
    public Result<?> createDbMonitor(@RequestBody DbMonitor dbMonitor){
        String dbMonitorId  = dbMonitorService.createDbMonitor(dbMonitor);
        return Result.ok(dbMonitorId);
    }

    /**
     * 删除监控项
     */
    @RequestMapping(value = "/deleteDbMonitor",method = RequestMethod.POST)
    public void deleteDbMonitor(@NotNull String id){
        dbMonitorService.deleteDbMonitor(id);
    }

    /**
     * 修改监控信息
     */
    @RequestMapping(value = "/updateDbMonitor",method = RequestMethod.POST)
    public Result<?> updateDbMonitor(@RequestBody DbMonitor dbMonitor){
        dbMonitorService.updateDbMonitor(dbMonitor);
        return Result.ok();
    }

    /**
     * 根据监控项类型查询监控项item
     */
    @RequestMapping(value = "/findItemListByType",method = RequestMethod.POST)
    public Result<?> findItemListByType(@RequestBody DbItem dbItem){
        List<DbItem> dbItemList = dbMonitorService.findItemListByType(dbItem);
        return Result.ok(dbItemList);
    }
}
