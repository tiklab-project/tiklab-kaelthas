package io.tiklab.kaelthas.host.graphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.graphics.model.Graphics;

import java.util.List;

@JoinProvider(model = Graphics.class)
public interface GraphicsService {
    Pagination<Graphics> findGraphicsPage(Graphics graphics);

    String createGraphics(Graphics graphics);

    void deleteGraphics(String id);

    void updateGraphics(Graphics graphics);

    void deleteGraphicsByMonitorId(String monitorId);

    void deleteGraphicsByHostId(String id);

    List<Graphics> findGraphicsList(Graphics graphics);

    void deleteGraphicsByIds(String[] monitorIds);

    //根据主机id查询出主机下配置的图表有多少,根据图表查询对应的数据返回
    List<Graphics> findInformationByGraphics(String hostId);

    //查询单个的上报数据信息,用于单条数据图表的展示
    List<Graphics> findGraphicsByHisInformation(Graphics graphics);

    //图形数量
    Long findGraphicsAllNum();

    //根据图形的id查询出图形下的监控项ids
    List<String> findMonitorIds(Graphics graphics);
}
