package io.tiklab.kaelthas.kubernetes.kubernetesInfo.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * k8s监控的信息表
 */
@JoinProvider(model = Kubernetes.class)
public interface KubernetesService {

    //k8s的分页查询
    Pagination<Kubernetes> findKbInfoPage(Kubernetes kubernetes);

    //创建k8s监控监控
    String createKbInfo(Kubernetes kubernetes);

    //修改k8s监控信息
    void updateKbInfo(Kubernetes kubernetes);

    //根据id删除监控的k8s信息
    void deleteKuInfo(String id);

    //根据时间倒排,查询四个k8s监控信息
    List<Kubernetes> findKuDropDown();

    //根据id查询k8s的监控信息
    Kubernetes findKuInfoById(String id);

    @FindOne
    Kubernetes findOne(@NotNull String id);

    @FindList
    List<Kubernetes> findList(List<String> idList);

    //查询所有k8s监控信息
    @FindAll
    List<Kubernetes> findAll();


    /**
     * 根据id查询k8s信息的概括（告警、监控项、触发器、图形）
     * @param  kuId
     */
    Kubernetes findKuGeneralize(String kuId);
}
