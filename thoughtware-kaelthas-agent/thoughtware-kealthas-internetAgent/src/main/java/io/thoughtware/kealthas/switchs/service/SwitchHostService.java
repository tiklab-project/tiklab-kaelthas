package io.thoughtware.kealthas.switchs.service;

import io.thoughtware.kealthas.switchs.dao.SwitchHostDao;
import io.thoughtware.kealthas.switchs.model.SwitchHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwitchHostService {

    @Autowired
    private SwitchHostDao switchHostDao;

    //使用定时任务获取配置信息,使用配置信息获取指标数据
    @Scheduled(cron = "* */2 * * * *")
    public void executeSwitchHost(){
        //获取配置的信息,监控项的信息
        List<SwitchHost> hostList = switchHostDao.findSwitchList();

        for (SwitchHost switchHost : hostList) {

        }

    }

}
