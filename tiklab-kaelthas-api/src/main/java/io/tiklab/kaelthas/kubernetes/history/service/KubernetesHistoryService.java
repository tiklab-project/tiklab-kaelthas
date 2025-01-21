package io.tiklab.kaelthas.kubernetes.history.service;

import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistoryQuery;

import java.util.List;
import java.util.Map;

public interface KubernetesHistoryService {

    //上传监控K8s信息存入历史数据表当中
    void insertForList(List<KubernetesHistory> entityList);

    /**
     * 查询监控k8s历史 图形表
     * @param kubernetesHistoryQuery hostHistoryQuery
     */
    List<List<KubernetesHistory>> findKuGraphicsLine(KubernetesHistoryQuery kubernetesHistoryQuery);

    /**
     * 查询距离当前时间指定分钟的数据
     * @param beforeTime 时间
     */
    List<KubernetesHistory> findKuHistoryByTime(String beforeTime);


    /**
     * k8s概况页展示的信息数据
     */
    Map<String,Object> findKuOverviewTotal(String kuId);

    /**
     * 根据k8s监控的id和时间查询时间之后的存储数据
     */
    List<KubernetesHistory> findKuHistoryByKuId(String kuId, String beforeTime);

}
