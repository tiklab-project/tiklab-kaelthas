package io.tiklab.kaelthas.starter.config;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.dal.boot.starter.annotation.EnableDal;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsClient;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsServer;
import io.tiklab.dsm.boot.starter.annotation.EnableDsm;
import io.tiklab.eam.boot.starter.annotation.EnableEamClient;
import io.tiklab.eam.boot.starter.annotation.EnableEamServer;
import io.tiklab.gateway.boot.starter.annotation.EnableGateway;
import io.tiklab.install.runner.TiklabApplicationRunner;
import io.tiklab.install.spring.boot.starter.EnableInstallServer;
import io.tiklab.kaelthas.timedtask.quartz.JobManager;
import io.tiklab.kaelthas.timedtask.quartz.RunJob;
import io.tiklab.kaelthas.timedtask.timer.service.CreHistoryTable;
import io.tiklab.licence.boot.starter.annotation.EnableLicenceServer;
import io.tiklab.messsage.boot.starter.annotation.EnableMessageServer;
import io.tiklab.openapi.boot.starter.annotation.EnableOpenApi;
import io.tiklab.postgresql.spring.boot.starter.EnablePostgresql;
import io.tiklab.postin.client.openapi.ParamConfig;
import io.tiklab.postin.client.openapi.ParamConfigBuilder;
import io.tiklab.postin.client.openapi.PostInClientConfig;
import io.tiklab.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.tiklab.rpc.boot.starter.annotation.EnableRpc;
import io.tiklab.security.boot.stater.annotation.EnableSecurityServer;
import io.tiklab.toolkit.boot.starter.annotation.EnableToolkit;
import io.tiklab.user.boot.starter.annotation.EnableUserClient;
import io.tiklab.user.boot.starter.annotation.EnableUserServer;
import io.tiklab.kaelthas.EnableKaelthasServer;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.HashMap;


/**
 * @author admin
 */

@Configuration
@EnableToolkit
@EnablePostgresql
@EnableDal
@EnableDsm

@EnableGateway
@EnableOpenApi
@EnableDcsClient
@EnableDcsServer
@EnableRpc
@EnableInstallServer

@EnableScheduling

//用户中心
@EnableUserServer
@EnableUserClient

//消息,日志,待办
@EnableMessageServer
@EnableSecurityServer

//登录,认证
@EnableEamClient
@EnableEamServer

//权限中心
@EnablePrivilegeServer
@EnableLicenceServer
@EnableKaelthasServer
@ComponentScan(value = "io.tiklab.kaelthas")
public class KaelthasConfiguration {


    @Configuration
    public class PostInClientAutoConfiguration {

        @Bean
        PostInClientConfig postInClientConfig(ParamConfig paramConfig){
            PostInClientConfig config = new PostInClientConfig();
            config.setParamConfig(paramConfig);

            return config;
        }

        @Bean
        ParamConfig paramConfig(){
            //设置请求头，属性名称：属性描述
            HashMap<String,String> headers = new HashMap<>();
            headers.put("accessToken","设置的apiKey");

            return ParamConfigBuilder.instance()
                    .setScanPackage("io.tiklab.kaelthas") //设置扫描的包路径
                    .prePath("/api")             //设置额外的前缀
                    .setHeaders(headers)               //设置请求头
                    .get();
        }

    }

    @Component
    public class QuartzListener implements TiklabApplicationRunner {


        @Autowired
        private JobManager jobManager;

        @Override
        public void run() {
            exec();
        }

        //定时任务执行
        public void exec(){
            try {
                jobManager.addJob(RunJob.class,"default");
            } catch (SchedulerException e) {
                throw new ApplicationException(e);
            }
        }
    }

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

}
