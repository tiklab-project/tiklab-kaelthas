package io.tiklab.kaelthas.kubernetes.graphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.graphics.model.KuGraphics;

import java.util.List;

/**
 * k8s下的图形配置
 */
public interface KuGraphicsService{

    //分页查询图形
    Pagination<KuGraphics> findKuGraphicsPage(KuGraphics kuGraphics);

    //创建图形
    String createKuGraphics(KuGraphics kuGraphics);

    //根据id删除图形
    void deleteKuGraphics(String id);

    //修改图形
    void updateKuGraphics(KuGraphics kuGraphics);

    //根据k8s监控的id查询图形信息
    List<KuGraphics> findKuGraphicsList(String hostId);

    //根据k8s监控的id删除图形信息
    void deleteByKuId(String id);
}
