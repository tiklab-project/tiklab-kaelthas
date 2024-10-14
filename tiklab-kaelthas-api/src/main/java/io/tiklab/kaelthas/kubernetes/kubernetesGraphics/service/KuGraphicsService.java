package io.tiklab.kaelthas.kubernetes.kubernetesGraphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;

import java.util.List;

public interface KuGraphicsService{

    Pagination<KuGraphics> findKuGraphicsPage(KuGraphics kuGraphics);

    String createKuGraphics(KuGraphics kuGraphics);

    void deleteKuGraphics(String id);

    void updateKuGraphics(KuGraphics kuGraphics);

    List<KuGraphics> findKuGraphicsList(String hostId);

    void deleteByKuId(String id);
}
