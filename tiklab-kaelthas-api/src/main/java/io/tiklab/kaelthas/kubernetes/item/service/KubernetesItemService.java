package io.tiklab.kaelthas.kubernetes.item.service;

import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.kubernetes.item.model.KubernetesItem;

import java.util.List;

/**
 * k8s监控的监控项字典表
 */
@JoinProvider(model = KubernetesItem.class)
public interface KubernetesItemService {

    //查询所有监控字典项
    @FindAll
    List<KubernetesItem> findAll();

    //根据ids查询监控字典项
    @FindList
    List<KubernetesItem> findList(List<String> ids);

    //根据id查询监控字典项
    @FindOne
    KubernetesItem findOne(String id);

    //根据k8s中的类型进行查询字典项
    List<KubernetesItem> findItemList(KubernetesItem kubernetesItem);
}
