package io.tiklab.kaelthas.internet.internet.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.internet.model.Internet;

import java.util.List;

/**
 * 网络监控信息存储表
 */
public interface InternetService {

    //网络监控的分页查询
    Pagination<Internet> findInternetPage(Internet internet);

    //创建网络监控
    String createInternet(Internet internet);

    //根据监控网络的id删除监控的网络信息
    void deleteInternet(String id);

    //修改网络信息
    void updateInternet(Internet internet);

    //根据监控网络的id查询监控网络的信息
    Internet findInternetById(String id);

    //查询所有的网络信息
    List<Internet> findAll();

    /**
     * 根据id查询网络信息的概括（告警、监控项、触发器、图形）
     * @param  id
     */
    Internet findInternetGeneralize(String id);
}
