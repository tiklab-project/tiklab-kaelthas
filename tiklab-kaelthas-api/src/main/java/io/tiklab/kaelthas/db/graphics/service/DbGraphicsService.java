package io.tiklab.kaelthas.db.graphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.db.graphics.model.DbGraphics;

import java.util.List;

/**
 * 数据库监控的图形配置
 */
@JoinProvider(model = DbGraphics.class)
public interface DbGraphicsService {

    //查询图形
    Pagination<DbGraphics> findGraphicsPage(DbGraphics dbGraphics);

    //创建图形
    String createGraphics(DbGraphics dbGraphics);

    void deleteGraphics(String id);

    //修改图形
    void updateGraphics(DbGraphics dbGraphics);

    //根据图表id查询出图表下监控线的ids
    List<String> findMonitorIds(String graphicsId);

    //根据dbId查询出图形列表
    List<DbGraphics> findDbGraphicsList(String dbId);

    void deleteByDbId(String dbId);
}
