package io.tiklab.kaelthas.host.setting.service;

import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;

import java.util.List;

/**
 * 主机设置页面的逻辑
 */
public interface HostSettingService {

    /**
     * 修改主机
     */
    void updateHost(Host host);

    /**
     * 根据id删除主机
     */
    void deleteHostById(String id);

    /**
     * 根据主机的id进行查询主机下监控项等信息
     */
    Host findHostById(String id);

    /**
     * 查询所有主机组的list信息
     */
    List<HostGroup> findAllHostGroupList();

}
