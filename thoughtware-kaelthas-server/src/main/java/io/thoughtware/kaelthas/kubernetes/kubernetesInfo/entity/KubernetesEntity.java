package io.thoughtware.kaelthas.kubernetes.kubernetesInfo.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Table(name = "mtc_ku_info")
@Entity
public class KubernetesEntity {

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
     * apiServer访问的端口号
     */
    @Column(name = "port")
    private Integer port;

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
     * 颜色类型
     */
    @Column(name = "color")
    private Integer color;

    /**
     * apiToken
     */
    @Column(name = "api_token")
    private String apiToken;

    @Column(name = "status")
    private Integer status;

    @Column(name = "usability")
    private Integer usability;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
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
}
