package io.tiklab.kaelthas.starter.config;

import io.tiklab.openapi.config.AllowConfig;
import io.tiklab.openapi.config.AllowConfigBuilder;
import io.tiklab.openapi.config.OpenApiConfig;
import io.tiklab.user.util.util.CodeUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KaelthasOpenApiAutoConfiguration {

    @Autowired
    CodeUtilService codeUtilService;

    //路由
    @Bean
    OpenApiConfig openApiConfig(AllowConfig allowConfig){
        OpenApiConfig openApiConfig = new OpenApiConfig();
        openApiConfig.setAllowConfig(allowConfig);

        return openApiConfig;
    }


    //路由配置
    @Bean
    AllowConfig allowConfig(){
        String[] s =  new String[]{};


        return AllowConfigBuilder.instance()
                .allowUrls(s)
                .get();

    }
}
