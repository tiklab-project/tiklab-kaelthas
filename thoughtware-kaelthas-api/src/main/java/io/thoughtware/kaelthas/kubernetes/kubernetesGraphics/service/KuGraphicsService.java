package io.thoughtware.kaelthas.kubernetes.kubernetesGraphics.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;

import java.util.List;

public interface KuGraphicsService{

    Pagination<KuGraphics> findKuGraphicsPage(KuGraphics kuGraphics);

    String createKuGraphics(KuGraphics kuGraphics);

    void deleteKuGraphics(String id);

    void updateKuGraphics(KuGraphics kuGraphics);

    List<KuGraphics> findKuGraphicsList(String hostId);

    void deleteByKuId(String id);
}
