package io.thoughtware.kaelthas.host.dynamic.service;

import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.host.dynamic.model.Dynamic;

import java.util.List;

@JoinProvider(model = Dynamic.class)
public interface DynamicService {

    @FindList
    List<Dynamic> findList(List<String> idList);

    List<Dynamic> findDynamicList();


    void createDynamic(Dynamic dynamic);

    void deleteByHostId(String hostId);
}
