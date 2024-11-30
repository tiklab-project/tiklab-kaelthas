package io.tiklab.kaelthas.internet.setting.service;

import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.internet.service.InternetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 网络监控中的设置
 */
@Service
public class InSettingServiceImpl implements InSettingService{

    @Autowired
    private InternetService internetService;

    /**
     * 根据id查询
     */
    @Override
    public Internet findInternetById(String id) {
        return internetService.findInternetById(id);
    }

    /**
     * 删除网络
     */
    @Override
    public void deleteInternet(String id) {
        internetService.deleteInternet(id);
    }

    /**
     * 修改网络
     */
    @Override
    public void updateInternet(Internet internet) {
        internetService.updateInternet(internet);
    }
}
