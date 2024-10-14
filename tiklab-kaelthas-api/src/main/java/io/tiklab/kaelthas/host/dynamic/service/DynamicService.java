package io.tiklab.kaelthas.host.dynamic.service;

import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.dynamic.model.Dynamic;

import java.util.List;

@JoinProvider(model = Dynamic.class)
public interface DynamicService {

    @FindList
    List<Dynamic> findList(List<String> idList);

    List<Dynamic> findDynamicList();


    void createDynamic(Dynamic dynamic);

    void deleteByHostId(String hostId);
}
