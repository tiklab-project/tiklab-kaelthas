package io.tiklab.kaelthas.internet.monitor.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.item.model.InternetItem;
import io.tiklab.kaelthas.internet.monitor.model.InternetMonitor;
import io.tiklab.kaelthas.internet.monitor.service.InternetMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/internetMonitor")
@RestController
public class InternetMonitorController {

    @Autowired
    private InternetMonitorService internetMonitorService;

    /**
     * 监控项分页查询
     */
    @RequestMapping(value = "/findMonitorPage", method = RequestMethod.POST)
    public Result<?> findMonitorPage(@RequestBody InternetMonitor internetMonitor) {
        Pagination<InternetMonitor> pagination = internetMonitorService.findMonitorPage(internetMonitor);
        return Result.ok(pagination);
    }

    /**
     * 添加监控项
     */
    @RequestMapping(value = "/createMonitor", method = RequestMethod.POST)
    public Result<?> createMonitor(@RequestBody InternetMonitor internetMonitor) {
        String string = internetMonitorService.createMonitor(internetMonitor);
        return Result.ok(string);
    }

    /**
     * 删除监控项
     */
    @RequestMapping(value = "/deleteMonitor",method = RequestMethod.POST)
    public Result<?> deleteMonitor(@NotNull String id){
        internetMonitorService.deleteMonitor(id);
        return Result.ok();
    }

    /**
     * 修改监控项
     */
    @RequestMapping(value = "/updateMonitor",method = RequestMethod.POST)
    public Result<?> updateMonitor(@RequestBody InternetMonitor internetMonitor){
        internetMonitorService.updateMonitor(internetMonitor);
        return Result.ok();
    }

    /**
     * 根据网络id查询出网络下的监控项
     */
    @RequestMapping(value = "/findMonitorByInId",method = RequestMethod.POST)
    public Result<?> findMonitorByInId(@RequestBody InternetMonitor internetMonitor){
        List<InternetMonitor> monitorList = internetMonitorService.findMonitorByInId(internetMonitor);
        return Result.ok(monitorList);
    }

    /**
     * 根据类型查询对应的item
     */
    @RequestMapping(value = "/findItemList",method = RequestMethod.POST)
    public Result<?> findItemList(@RequestBody InternetItem internetItem){
        List<InternetItem> itemList = internetMonitorService.findItemList(internetItem);
        return Result.ok(itemList);
    }

}
