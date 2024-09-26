package io.thoughtware.kaelthas.host.hostRecent.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_host_recent")
public class HostRecentEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "host_id")
    private String hostId;

    @Column(name = "user_id")
    private String userId;

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
