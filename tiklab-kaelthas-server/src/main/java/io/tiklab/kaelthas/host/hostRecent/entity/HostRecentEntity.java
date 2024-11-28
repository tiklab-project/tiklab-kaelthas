package io.tiklab.kaelthas.host.hostRecent.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 已废弃,存储最近主机的信息,只保存最近的四台,之前首页上展示的
 */
@Entity
@Table(name = "mtc_host_recent")
public class HostRecentEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 主机id
     */
    @Column(name = "host_id")
    private String hostId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 更改时间,点击的时候会调整前后顺序
     */
    @Column(name = "update_time")
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
