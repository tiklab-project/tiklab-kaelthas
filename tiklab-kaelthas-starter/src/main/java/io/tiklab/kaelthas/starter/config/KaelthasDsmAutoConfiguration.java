package io.tiklab.kaelthas.starter.config;

import io.tiklab.dsm.model.DsmConfig;
import io.tiklab.dsm.model.DsmVersion;
import io.tiklab.dsm.support.DsmConfigBuilder;
import io.tiklab.dsm.support.DsmVersionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库脚本配置
 * @author zcamy
 */
@Configuration
public class KaelthasDsmAutoConfiguration {

    @Bean
    DsmConfig dsmConfig(){
        DsmConfig dsmConfig = new DsmConfig();

        dsmConfig.setVersionList(versionList());
        return dsmConfig;
    }

    /**
     * 初始化Dsm版本列表
     * @return
     */
    List<DsmVersion> versionList() {
        List<DsmVersion> versionList = new ArrayList<>();
        DsmVersion dsmVersion = DsmVersionBuilder.instance()
                .version("1.0.0")
                .db(new String[]{
                        "user_1.0.0",
                        //PrivilegeDsm
                        "privilege_1.0.0",
                        //LicenceDsm
                        "app-authorization_1.0.0",
                        //MessageDsm
                        "message_1.0.0",
                        //SecurityDsm
                        "oplog_1.0.0",
                        //TodoTaskDsm
                        "todotask_1.0.0",
                        "openapi_1.0.0",

                        "kealthas-privilege_1.0.0",
                        "xmonitor_1.0.1",
                        "xmonitor_1.0.2",
                        "xmonitor_1.0.3",
                        "xmonitor_1.0.8",
                }).get();
        versionList.add(dsmVersion);

        dsmVersion = DsmVersionBuilder.instance()
                .version("1.1.3")
                .db(new String[]{
                        "kaelthas_1.1.2",
                        "kaelthas_1.1.3"
                }).get();
        versionList.add(dsmVersion);

        dsmVersion = DsmVersionBuilder.instance()
                .version("user_2.0.0")
                .db(new String[]{
                        "user_2.0.0",
                        "user_2.0.1",
                }).get();
        versionList.add(dsmVersion);

        DsmVersion message_109 = DsmVersionBuilder.instance()
                .version("message_1.0.9")
                .db(new String[]{
                        "message_1.0.9",
                }).get();
        versionList.add(message_109);

        dsmVersion = DsmVersionBuilder.instance()
                .version("message_2.0.0")
                .db(new String[]{
                        "message_2.0.0",
                }).get();
        versionList.add(dsmVersion);
        return versionList;
    }

}
























