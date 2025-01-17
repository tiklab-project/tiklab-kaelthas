package io.tiklab.kaelthas.kubernetes.history.service;

import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistoryQuery;

import java.util.List;

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
}
