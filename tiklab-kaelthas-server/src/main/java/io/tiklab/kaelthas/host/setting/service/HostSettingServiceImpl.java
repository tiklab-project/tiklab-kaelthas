package io.tiklab.kaelthas.host.setting.service;

import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.kaelthas.host.hostGroup.service.HostGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostSettingServiceImpl implements HostSettingService{

    @Autowired
    private HostService hostService;

    @Autowired
    private HostGroupService hostGroupService;
    /**
     * 修改主机
     */
    @Override
    public void updateHost(Host host) {
        hostService.updateHost(host);
    }

    /**
     * 根据id删除主机
     */
    @Override
    public void deleteHostById(String id) {
        hostService.deleteHostById(id);
    }

    /**
     * 根据主机的id进行查询主机下监控项等信息
     */
    @Override
    public Host findHostById(String id) {
        return hostService.findHostById(id);
    }

    /**
     * 查询所有主机组的list信息
     */
    @Override
    public List<HostGroup> findAllHostGroupList() {
        return hostGroupService.findAllHostGroupList();
    }
}
