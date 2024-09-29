package io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.kubernetes.kubernetesTrigger.model.KuTrigger;

import java.util.List;

public interface KuTriggerService {

    Pagination<KuTrigger> findKuTriggerPage(KuTrigger kuTrigger);

    String createKuTrigger(KuTrigger kuTrigger);

    void deleteKuTrigger(String id);

    void updateKuTrigger(KuTrigger kuTrigger);

    //根据kuId删除触发器信息
    void deleteKuTriggerByKuId(String id);

    //根据kuId查询触发器信息
    List<KuTrigger> findKuTriggerByKuId(String id);
}
