package io.thoughtware.kaelthas.internet.internetGraphics.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.internet.internetGraphics.model.InternetGraphics;

import java.util.List;

public interface InternetGraphicsService {

    //分页查询
    Pagination<InternetGraphics> findGraphicsPage(InternetGraphics internetGraphics);

    //创建图形
    String createGraphics(InternetGraphics internetGraphics);

    //删除图形
    void deleteGraphics(String id);

    //修改图形
    void updateGraphics(InternetGraphics internetGraphics);

    List<InternetGraphics> findGraphicsList(String internetId);

    void deleteGraphicsByInId(String id);
}
