package io.tiklab.kaelthas.host.host.entity;


import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_host")
public class HostEntity {
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    //主机名称
    @Column(name = "name")
    private String name;

    //主机ip
    @Column(name = "ip")
    private String ip;

    //主机组id
    @Column(name = "host_group_id")
    private String hostGroupId;

    //主机状态 1.已启用 2.未启用
    @Column(name = "state")
    private Integer state;

    //主机可用性 1.可用 2.不可用 3.未知
    @Column(name = "usability")
    private Integer usability;

    //主机描述
    @Column(name="describe")
    private String describe;

    //主机创建时间
    @Column(name = "create_time")
    private String  createTime;

    //主机创建时间
    @Column(name = "update_time")
    private String  updateTime;

    //可见性
    @Column(name = "visibility")
    private Integer visibility;

    //颜色
    @Column(name = "color")
    private Integer color;


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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostGroupId() {
        return hostGroupId;
    }

    public void setHostGroupId(String hostGroupId) {
        this.hostGroupId = hostGroupId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUsability() {
        return usability;
    }

    public void setUsability(Integer usability) {
        this.usability = usability;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
