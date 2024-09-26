package io.thoughtware.kaelthas.kubernetes.kubernetesItem.service;

import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.kubernetes.kubernetesItem.model.KubernetesItem;

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
