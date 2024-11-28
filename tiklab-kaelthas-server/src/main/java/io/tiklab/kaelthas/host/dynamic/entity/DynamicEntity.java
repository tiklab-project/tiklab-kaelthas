package io.tiklab.kaelthas.host.dynamic.entity;


import io.tiklab.dal.jpa.annotation.*;

/**
 * 主机的动态信息表,之前在首页上展示的最近主机,页面没有展示,已经废弃了
 */
@Entity
@Table(name = "mtc_dynamic")
public class DynamicEntity {

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
     * 主机id
     */
    @Column(name = "host_id")
    private String hostId;

    /**
     * 动态的时间
     */
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
