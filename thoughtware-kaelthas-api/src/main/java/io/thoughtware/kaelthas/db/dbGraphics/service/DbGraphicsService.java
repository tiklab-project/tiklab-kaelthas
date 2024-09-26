package io.thoughtware.kaelthas.db.dbGraphics.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.db.dbGraphics.model.DbGraphics;

import java.util.List;

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
    List<DbGraphics> findDbGraphicsList(String hostId);
}
