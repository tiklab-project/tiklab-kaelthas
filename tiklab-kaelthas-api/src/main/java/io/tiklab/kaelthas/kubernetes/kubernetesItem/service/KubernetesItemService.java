package io.tiklab.kaelthas.kubernetes.kubernetesItem.service;

import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.kubernetes.kubernetesItem.model.KubernetesItem;

import java.util.List;

@JoinProvider(model = KubernetesItem.class)
public interface KubernetesItemService {


    @FindAll
    List<KubernetesItem> findAll();

    @FindList
    List<KubernetesItem> findList(List<String> ids);

    @FindOne
    KubernetesItem findOne(String id);

    List<KubernetesItem> findItemList(KubernetesItem kubernetesItem);
}
