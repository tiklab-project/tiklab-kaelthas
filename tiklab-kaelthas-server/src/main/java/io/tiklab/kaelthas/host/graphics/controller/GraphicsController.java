package io.tiklab.kaelthas.host.graphics.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.graphics.model.Graphics;
import io.tiklab.kaelthas.host.graphics.service.GraphicsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/graphics")
public class GraphicsController {

    @Resource
    private GraphicsService graphicsService;

    /**
     * 根据条件查询图形
     */
    @RequestMapping(value = "/findGraphics", method = RequestMethod.POST)
    public Result<Pagination<Graphics>> findGraphicsPage(@RequestBody Graphics graphics) {
        Pagination<Graphics> resData = graphicsService.findGraphicsPage(graphics);
        return Result.ok(resData);
    }

    @RequestMapping(value = "/findMonitorIds",method = RequestMethod.POST)
    public Result<List<String>> findMonitorIds(@RequestBody Graphics graphics){
        List<String> list = graphicsService.findMonitorIds(graphics);
        return Result.ok(list);
    }

    /**
     * 添加图表
     */
    @RequestMapping(value = "/addGraphics", method = RequestMethod.POST)
    public Result<String> createGraphics(@RequestBody Graphics graphics) {
        String serviceGraphics = graphicsService.createGraphics(graphics);
        return Result.ok(serviceGraphics);
    }

    /**
     * 删除图形
     */
    @RequestMapping(value = "/deleteGraphics", method = RequestMethod.POST)
    public void deleteGraphics(@NotNull String id) {
        graphicsService.deleteGraphics(id);
    }

    /**
     * 修改图表
     */
    @RequestMapping(value = "/updateGraphics", method = RequestMethod.POST)
    public void updateGraphics(@RequestBody Graphics graphics) {
        graphicsService.updateGraphics(graphics);
    }

    /**
     * 查询单个的上报数据信息,用于单条数据图表的展示,前端页面已经没有调用
     */
    @RequestMapping(value = "/findGraphicsByHisInformation", method = RequestMethod.POST)
    public Result<List<Graphics>> findGraphicsByHisInformation(@RequestBody Graphics graphics) {
        List<Graphics> graphicsList = graphicsService.findGraphicsByHisInformation(graphics);
        return Result.ok(graphicsList);
    }

}
