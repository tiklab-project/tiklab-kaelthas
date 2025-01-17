package io.tiklab.kaelthas.history.service;

import io.tiklab.kaelthas.history.model.History;

import java.util.List;

public interface K8sHistoryService {

    //上传监控K8s信息存入历史数据表当中
    void insertForList(List<History> entityList);
}
