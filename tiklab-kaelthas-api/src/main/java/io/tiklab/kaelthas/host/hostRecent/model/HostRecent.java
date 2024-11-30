package io.tiklab.kaelthas.host.hostRecent.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;
import io.tiklab.kaelthas.host.host.model.Host;

@Join
@Mapper
public class HostRecent {

    private String id;

    //主机id
    private String hostId;

    //用户名称
    private String hostName;

    //状态
    private Integer state;

    //颜色
    private Integer color;

    //用户id
    private String userId;

    //主机信息
    @Mappings({
            @Mapping(source = "host.id", target = "hostId")
    })
    @JoinQuery(key = "id")
    private Host host;

    //修改时间
    private String updateTime;

    //告警数量
    private Integer alarmNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Integer getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(Integer alarmNum) {
        this.alarmNum = alarmNum;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
