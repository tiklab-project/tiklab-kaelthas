package io.tiklab.kaelthas.starter.config;


import io.tiklab.dal.boot.starter.annotation.EnableDal;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsClient;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsServer;
import io.tiklab.dsm.boot.starter.annotation.EnableDsm;
import io.tiklab.eam.boot.starter.annotation.EnableEamClient;
import io.tiklab.eam.boot.starter.annotation.EnableEamServer;
import io.tiklab.gateway.boot.starter.annotation.EnableGateway;
import io.tiklab.licence.boot.starter.annotation.EnableLicenceServer;
import io.tiklab.messsage.boot.starter.annotation.EnableMessageServer;
import io.tiklab.openapi.boot.starter.annotation.EnableOpenApi;
import io.tiklab.postgresql.EnablePostgresql;
import io.tiklab.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.tiklab.rpc.boot.starter.annotation.EnableRpc;
import io.tiklab.security.boot.stater.annotation.EnableSecurityServer;
import io.tiklab.toolkit.boot.starter.annotation.EnableToolkit;
import io.tiklab.user.boot.starter.annotation.EnableUserClient;
import io.tiklab.user.boot.starter.annotation.EnableUserServer;
import io.tiklab.kaelthas.EnableKaelthasServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author admin
 */

@Configuration
@EnableDal
@EnableRpc
@EnableGateway
@EnableToolkit

@EnableOpenApi
@EnableDcsClient
@EnableDcsServer
@EnablePostgresql
@EnableScheduling

@EnableDsm
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
public class KaelthasAutoConfiguration {

}
