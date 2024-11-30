package io.tiklab.kaelthas.internet.setting.service;

import io.tiklab.kaelthas.internet.internet.model.Internet;

/**
 * 网络监控中的设置
 */
public interface InSettingService {

    /**
     * 根据id查询
     */
    Internet findInternetById(String id);

    /**
     * 删除网络
     */
    void deleteInternet(String id);

    /**
     * 修改网络
     */
    void updateInternet(Internet internet);
}
