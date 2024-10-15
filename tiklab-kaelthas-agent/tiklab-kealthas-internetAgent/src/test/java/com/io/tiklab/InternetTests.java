package com.io.tiklab;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class InternetTests {

    @Test
    void contextLoads() throws IOException {

        System.out.println("系统描述：" + GetMessage.getMessageByIpAndOid("172.10.1.1", MySnmp.Sys_Desc));
        System.out.println("设备型号：" + GetMessage.getMessageByIpAndOid("172.10.1.1", MySnmp.Sys_Name));
        System.out.println("运行时间：" + GetMessage.getMessageByIpAndOid("172.10.1.1", MySnmp.Sys_Up_Time));
//        System.out.println("获取容器网口当前速率：" + GetMessage.getMessageByIpAndOid("172.10.1.1", MySnmp.If_Speed));
//        System.out.println("获取容器网口当前时刻进流量：" + GetMessage.getMessageByIpAndOid("172.10.1.1", MySnmp.If_In_Octets));
//        System.out.println("获取容器网口当前时刻出流量：" + GetMessage.getMessageByIpAndOid("172.10.1.1", MySnmp.If_Out_Octets));
//
//        System.out.println("端口数量：" + GetMessage.getMessageByIpAndOid("172.10.1.1", MySnmp.if_Number));
//        System.out.println("端口描述：" + GetMessage.getMessageByIpAndOid("172.10.1.1", MySnmp.if_Descr));
//        System.out.println("端口状态：" + GetMessage.getMessageByIpAndOid("172.10.1.1", "1.3.6.1.2.1.2.2.1.8"));

    }

    @Test
    void contextHost(){

        SnmpRouterMetrics.extracted("172.10.1.1", MySnmp.if_Number);
    }

}
