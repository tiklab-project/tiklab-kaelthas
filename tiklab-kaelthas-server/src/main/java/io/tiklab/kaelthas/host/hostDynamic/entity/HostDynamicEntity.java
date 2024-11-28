package io.tiklab.kaelthas.host.hostDynamic.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 主机下的动态信息,例如创建监控项的信息等
 */
@Entity
@Table(name = "mtc_host_dynamic")
public class HostDynamicEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 动态名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 动态的时间
     */
    @Column(name = "time")
    private String time;

    /**
     * 主机id
     */
    @Column(name = "host_id")
    private String hostId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
