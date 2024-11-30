package io.tiklab.kaelthas.host.dynamic.service;

import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.dynamic.model.Dynamic;

import java.util.List;

/**
 * 首页上展示的动态信息,已经废弃
 */
@JoinProvider(model = Dynamic.class)
public interface DynamicService {

    /**
     * 根据ids查询动态list
     */
    @FindList
    List<Dynamic> findList(List<String> idList);

    /**
     * 查询10条动态信息
     */
    List<Dynamic> findDynamicList();

    /**
     * 创建动态信息
     */
    void createDynamic(Dynamic dynamic);

    /**
     * 根据主机的id删除动态信息
     */
    void deleteByHostId(String hostId);
}
