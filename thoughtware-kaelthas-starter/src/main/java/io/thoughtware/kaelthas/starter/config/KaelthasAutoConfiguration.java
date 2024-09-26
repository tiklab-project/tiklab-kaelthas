package io.thoughtware.kaelthas.starter.config;


import io.thoughtware.dal.boot.starter.annotation.EnableDal;
import io.thoughtware.dcs.boot.starter.annotation.EnableDcsClient;
import io.thoughtware.dcs.boot.starter.annotation.EnableDcsServer;
import io.thoughtware.dsm.boot.starter.annotation.EnableDsm;
import io.thoughtware.eam.boot.starter.annotation.EnableEamClient;
import io.thoughtware.eam.boot.starter.annotation.EnableEamServer;
import io.thoughtware.gateway.boot.starter.annotation.EnableGateway;
import io.thoughtware.licence.boot.starter.annotation.EnableLicenceServer;
import io.thoughtware.messsage.boot.starter.annotation.EnableMessageServer;
import io.thoughtware.openapi.boot.starter.annotation.EnableOpenApi;
import io.thoughtware.postgresql.EnablePostgresql;
import io.thoughtware.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.thoughtware.rpc.boot.starter.annotation.EnableRpc;
import io.thoughtware.security.boot.stater.annotation.EnableSecurityServer;
import io.thoughtware.toolkit.boot.starter.annotation.EnableToolkit;
import io.thoughtware.user.boot.starter.annotation.EnableUserClient;
import io.thoughtware.user.boot.starter.annotation.EnableUserServer;
import io.thoughtware.kaelthas.EnableKaelthasServer;
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
@ComponentScan(value = "io.thoughtware.kaelthas")
public class KaelthasAutoConfiguration {

}
