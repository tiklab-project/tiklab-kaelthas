package io.tiklab.kaelthas.kubernetes.kubernetesInfo.service;


import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.KuOverview;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.KuOverviewQuery;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;
import java.util.Map;

/**
 * k8s监控概览信息存储表
 */
@JoinProvider(model = KuOverview.class)
public interface KuOverviewService {


    //创建k8s监控概览
    void createKuOverview(List<KuOverview> kuOverview);

    //更新k8s监控概览
    void updateKuOverview(KuOverview kuOverview);


    //根据监控k8s的id查询监控k8s的信息
    List<KuOverview> findKuByKuId(String kuId);

    //根据监控k8s的id查询监控k8s的信息
    List<KuOverview> findKuList(KuOverviewQuery kuOverviewQuery);


    /**
     * 根据id查询k8s服务器的概况页展示的信息数据
     */
    Map<String, Object> findKuOverviewTotal(String kuId);


}
