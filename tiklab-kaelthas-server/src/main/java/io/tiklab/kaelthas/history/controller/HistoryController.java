package io.tiklab.kaelthas.history.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.history.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;


    /**
     * 根据条件查询监控数据
     */
    @RequestMapping(value = "/findInformationPage", method = RequestMethod.POST)
    public Result<Pagination<History>> findInformationPage(@RequestBody History history) {
        Pagination<History> pagination = historyService.findInformationPage(history);
        return Result.ok(pagination);
    }


    /**
     * 根据多个监控项信息和时间查询上报数据,并且返回数据
     */
    @RequestMapping(value = "/findInformationByGraphics", method = RequestMethod.POST)
    public Result<List<List<History>>> findInformationByGraphics(@RequestBody History history) {
        List<List<History>> informationList = historyService.findInformationByGraphics(history);
        return Result.ok(informationList);
    }

    /**
     * 单条数据的图形查询
     */
    @RequestMapping(value = "/findInformationByLine", method = RequestMethod.POST)
    public Result<History> findInformationByLine(@RequestBody History history) {
        return Result.ok(historyService.findInformationByLine(history));
    }

    @RequestMapping(value = "/findGraphicsLine", method = RequestMethod.POST)
    public Result<List<List<History>>> findGraphicsLine(@RequestBody History history) {
        List<List<History>> list = historyService.findGraphicsLine(history);
        return Result.ok(list);
    }

    /**
     * 查询k8s中的监控图形
     */
    @RequestMapping(value = "/findKuGraphicsLine", method = RequestMethod.POST)
    public Result<List<List<History>>> findKuGraphicsLine(@RequestBody History history) {
        List<List<History>> list = historyService.findKuGraphicsLine(history);
        return Result.ok(list);
    }

    /**
     * 获取k8s的详情
     */
    @RequestMapping(value = "/findKuOverviewTotal", method = RequestMethod.POST)
    public Result<?> findKuOverviewTotal(@NotNull String kuId) {
        Map<String, Object> mapList = historyService.findKuOverviewTotal(kuId);
        return Result.ok(mapList);
    }

    /**
     * 网络设备的图形展示
     */
    @RequestMapping(value = "/findInGraphicsLine",method = RequestMethod.POST)
    public Result<?> findInGraphicsLine(@RequestBody History history){
        List<List<History>> list = historyService.findInGraphicsLine(history);
        return Result.ok(list);
    }

    /**
     * 获取网络详情
     */
    @RequestMapping(value = "/findInternetOverview",method = RequestMethod.POST)
    public Result<?> findInternetOverview(@NotNull String internetId){
        Map<String,Object> objectMap = historyService.findInternetOverview(internetId);
        return Result.ok(objectMap);
    }

}
