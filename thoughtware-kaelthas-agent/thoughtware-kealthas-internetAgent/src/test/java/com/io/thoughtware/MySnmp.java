package com.io.thoughtware;

//定义snmp类，存储常量
public class MySnmp {
    //设备描述信息
    //1.3.6.1.4.1.11863.5.52
    public static final String Sys_Desc = "1.3.6.1.2.1.1.1";
    //设备开启时间
    public static final String Sys_Name = "1.3.6.1.2.1.1.5";
    //设备名称
    public static final String Sys_Up_Time = "1.3.6.1.2.1.1.3";
    //网卡接口速率
    public static final String If_Speed = "1.3.6.1.2.1.2.2.1.5.126";
    //网卡接口当前时刻进流量
    public static final String If_In_Octets = "1.3.6.1.2.1.2.2.1.10";
    //网卡接口当前时刻出流量
    public static final String If_Out_Octets = "1.3.6.1.2.1.2.2.1.16";

    //端口数量
    public static final String if_Number = "1.3.6.1.2.1.2.1.0";

    //端口描述
    public static final String if_Descr = "1.3.6.1.2.1.2.2.1.2";

    //端口状态
    public static final String if_Oper_Status = "1.3.6.1.2.1.2.2.1.8";
}
