package io.tiklab.kaelthas.host.dynamic.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 主机的动态信息,首页展示,当前没有使用了
 */
@Join
@Mapper
public class Dynamic {


    private String id;

    /**
     * 动态名称
     */
    private String name;

    /**
     * 主机id
     */
    private String hostId;

    /**
     * 动态的时间
     */
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
