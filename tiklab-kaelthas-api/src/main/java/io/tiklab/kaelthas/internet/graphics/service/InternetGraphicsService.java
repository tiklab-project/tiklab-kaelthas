package io.tiklab.kaelthas.internet.graphics.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.graphics.model.InternetGraphics;
import io.tiklab.kaelthas.internet.graphicsMonitor.model.InGraphicsMonitor;

import java.util.List;
/**
 * 网络监控下的图形配置
 */
public interface InternetGraphicsService {

    //分页查询
    Pagination<InternetGraphics> findGraphicsPage(InternetGraphics internetGraphics);

    //创建图形
    String createGraphics(InternetGraphics internetGraphics);

    //删除图形
    void deleteGraphics(String id);

    //修改图形
    void updateGraphics(InternetGraphics internetGraphics);

    /**
     * 根据监控网络的id查询图形
     */
    List<InternetGraphics> findGraphicsList(String internetId);

    /**
     * 根据监控网络的id删除图形
     */
    void deleteGraphicsByInId(String id);

    /**
     * 根据图形id查询图形list
     */
    List<InGraphicsMonitor> findGraphicsMonitors(String id);
}
