package io.tiklab.kaelthas.kubernetes.monitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.monitor.model.KuMonitor;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * k8s监控中的监控项
 */
@JoinProvider(model = KuMonitor.class)
public interface KuMonitorService {

    //根据名称和id进行分页查询
    Pagination<KuMonitor> findKuMonitorPage(KuMonitor kuMonitor);

    //创建k8s下的监控项
    String createKuMonitor(KuMonitor kuMonitor);

    //根据监控项id删除监控项
    void deleteKuMonitor(String id);

    //修改监控项
    void updateKuMonitor(KuMonitor kuMonitor);

    //根据k8sid查询所有的监控项
    List<KuMonitor> findAllKuMonitor(String kuId);

    /**
     * 查询k8s下的集群和监控项信息
     */
    List<KuMonitor> findKuAndMonitor();

    //根据k8s监控的id删除监控项
    void deleteByKuId(String id);
}
