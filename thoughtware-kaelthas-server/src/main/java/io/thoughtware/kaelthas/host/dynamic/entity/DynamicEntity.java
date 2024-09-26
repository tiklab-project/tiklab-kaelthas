package io.thoughtware.kaelthas.host.dynamic.entity;


import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_dynamic")
public class DynamicEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;


    @Column(name = "name")
    private String name;

    @Column(name = "host_id")
    private String hostId;


    @Column(name = "update_time")
    private String updateTime;


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

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
