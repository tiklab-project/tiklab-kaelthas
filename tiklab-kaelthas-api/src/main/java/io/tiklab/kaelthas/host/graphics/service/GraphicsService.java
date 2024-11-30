package io.tiklab.kaelthas.host.graphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.graphics.model.Graphics;

import java.util.List;

/**
 * 主机下图形配置
 */
@JoinProvider(model = Graphics.class)
public interface GraphicsService {

    /**
     * 主机下图形的分页查询
     */
    Pagination<Graphics> findGraphicsPage(Graphics graphics);

    /**
     * 主机下的图形创建
     */
    String createGraphics(Graphics graphics);

    /**
     * 根据图形id删除图形
     */
    void deleteGraphics(String id);

    /**
     * 修改图形
     */
    void updateGraphics(Graphics graphics);

    /**
     * 根据主机id删除图形
     */
    void deleteGraphicsByHostId(String id);

    /**
     * 查询是否已经存在相同名称的图形
     */
    List<Graphics> findGraphicsList(Graphics graphics);

    //根据主机id查询出主机下配置的图表有多少,根据图表查询对应的数据返回
    List<Graphics> findInformationByGraphics(String hostId);

    //查询单个的上报数据信息,用于单条数据图表的展示
    List<Graphics> findGraphicsByHisInformation(Graphics graphics);

    //图形数量
    Long findGraphicsAllNum();

    //根据图形的id查询出图形下的监控项ids
    List<String> findMonitorIds(Graphics graphics);
}
