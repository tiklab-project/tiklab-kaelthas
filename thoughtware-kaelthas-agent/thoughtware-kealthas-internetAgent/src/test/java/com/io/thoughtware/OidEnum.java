package com.io.thoughtware;

public enum OidEnum {

    //物理机CPU总核数、使用率
    HostCpuUtilization("1.3.6.1.2.1.25.3.3.1.2","物理机CPU总核数和使用率","walk"),

    //物理机内存总量
    HostMemoryTotal("1.3.6.1.2.1.25.2.2.0","物理机内存总量","get"),

    //物理机内存总量(用来计算内存的使用率或者拿到硬盘总量)
    HostMemoryTotalCalUtil("1.3.6.1.2.1.25.2.3.1.5","物理机内存总量(用来计算内存的使用率或者拿到硬盘总量)","walk"),

    //物理机内存使用率(用来计算内存的使用率或者拿到硬盘使用量)
    HostMemoryUsedCalUtil("1.3.6.1.2.1.25.2.3.1.6","物理机内存使用率(用来计算内存的使用率或者拿到硬盘使用量)","walk"),

    /**
     * 华为物理机CPU总核数
     */
    HuaWeiHostCpuTotal("1.3.6.1.4.1.2011.2.235.1.1.15.50.1.12","华为物理机CPU总核数","walk"),
    /**
     * 华为物理机CPU使用率, 单位%
     */
    HuaWeiHostCpuUtilization("1.3.6.1.4.1.2011.2.235.1.1.1.23.0","华为物理机CPU使用率","get"),
    /**
     * 华为物理机内存总量
     */
    HuaWeiHostMemoryTotal("1.3.6.1.4.1.2011.2.235.1.1.16.50.1.4","华为物理机内存总量","walk"),
    /**
     * 华为物理机内存使用率, 单位%
     */
    HuaWeiHostMemoryUtilization("1.3.6.1.4.1.2011.2.235.1.1.1.25.0","华为物理机内存总量","get"),

    //scv3000获得存储总量
    StorageScv3000Total("1.3.6.1.4.1.674.11000.2000.500.1.2.32.1.3", "scv3000获得存储总量(单位GB)","walk"),
    StorageScv3000Alloc("1.3.6.1.4.1.674.11000.2000.500.1.2.32.1.7", "scv3000获得存储分配量(单位GB)","walk"),
    StorageScv3000Used("1.3.6.1.4.1.674.11000.2000.500.1.2.32.1.6", "scv3000获得存储使用量(单位GB)","walk"),

    //PS6210存储总量
    StoragePs6210Total("1.3.6.1.4.1.12740.1.1.2.1.1.1","PS6210存储总量(单位MB)","get"),
    //PS6210存储空闲量
    StoragePs6210Free("1.3.6.1.4.1.12740.1.1.2.1.15.1","PS6210存储空闲量(单位MB)","get"),
    //PS6210存储使用量
    StoragePs6210Used("1.3.6.1.4.1.12740.1.1.2.1.2.1","PS6210存储已使用(单位MB)","get"),



    //FS8600获得存储总量
    StorageFs8600Total("1.3.6.1.4.1.674.11000.2000.200.1.38.1","FS8600获得存储总量(单位MB)","get"),
    //FS8600获得存储使用量
    StorageFs8600Used("1.3.6.1.4.1.674.11000.2000.200.1.38.3","FS8600获得存储使用量(单位MB)","get"),

    //存储总量
    StorageClusterTotal("1.3.6.1.4.1.12124.1.3.1.0", "存储总量(从磁盘集群获取)","get"),
    //存储已使用量
    StorageClusterUsed("1.3.6.1.4.1.12124.1.3.2.0", "存储使用量(从磁盘集群获取)","get");


    private String oid;
    private String describe;
    private String type;

    OidEnum(String oid, String describe, String type) {
        this.oid = oid;
        this.describe = describe;
        this.type = type;
    }

    public String getOid() {
        return oid;
    }

    public String getdDscribe() {
        return describe;
    }
}

