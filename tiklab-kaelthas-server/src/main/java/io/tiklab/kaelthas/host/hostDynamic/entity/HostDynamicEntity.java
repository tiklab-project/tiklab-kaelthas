package io.tiklab.kaelthas.host.hostDynamic.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_host_dynamic")
public class HostDynamicEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;


    @Column(name = "name")
    private String name;


    @Column(name = "time")
    private String time;


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
