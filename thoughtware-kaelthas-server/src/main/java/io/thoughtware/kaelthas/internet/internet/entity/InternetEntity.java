package io.thoughtware.kaelthas.internet.internet.entity;


import io.thoughtware.dal.jpa.annotation.*;

import java.util.Date;

/**
* 
* @TableName mtc_internet
*/

@Table(name = "mtc_internet")
@Entity
public class InternetEntity {

    /**
    * id
    */
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
    * 名称
    */
    @Column(name = "name")
    private String name;

    /**
    * ip
    */
    @Column(name = "ip")
    private String ip;

    /**
    * 1代表交换机,2代表路由器
    */
    @Column(name = "type")
    private Integer type;

    /**
    * 1.开启,0.关闭
    */
    @Column(name = "status")
    private Integer status;

    /**
    * 是否可用
    */
    @Column(name = "usability")
    private Integer usability;

    /**
    * 创建时间
    */
    @Column(name = "create_time")
    private String createTime;

    /**
    * 修改时间
    */
    @Column(name = "update_time")
    private String updateTime;

    /**
    * 颜色
    */
    @Column(name = "color")
    private Integer color;

    /**
     * 端口号
     */
    @Column(name = "port")
    private Integer port;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUsability() {
        return usability;
    }

    public void setUsability(Integer usability) {
        this.usability = usability;
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
