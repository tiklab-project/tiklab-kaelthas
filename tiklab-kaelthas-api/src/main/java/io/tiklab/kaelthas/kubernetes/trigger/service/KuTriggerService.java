package io.tiklab.kaelthas.kubernetes.trigger.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.trigger.model.KuTrigger;

import java.util.List;

/**
 * k8s监控中的触发器
 */
public interface KuTriggerService {

    //触发器分页查询
    Pagination<KuTrigger> findKuTriggerPage(KuTrigger kuTrigger);

    //创建触发器
    String createKuTrigger(KuTrigger kuTrigger);

    //根据触发器id删除触发器
    void deleteKuTrigger(String id);

    //修改触发器
    void updateKuTrigger(KuTrigger kuTrigger);

    //根据kuId删除触发器信息
    void deleteKuTriggerByKuId(String id);

    //根据kuId查询触发器信息
    List<KuTrigger> findKuTriggerByKuId(String id);
}
