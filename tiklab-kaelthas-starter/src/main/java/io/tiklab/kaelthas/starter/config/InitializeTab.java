package io.tiklab.kaelthas.starter.config;

import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import io.tiklab.kaelthas.timedtask.timer.service.CreHistoryTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* 启动项目的时候初始化数据库表 （历史表是动态创建的）
* */
@Component()
public class InitializeTab implements TiklabApplicationRunner {

    @Autowired
    CreHistoryTable creHistoryTable;
    @Override
    public void run() {
        creHistoryTable.createTable();
    }
}
