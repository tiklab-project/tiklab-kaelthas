package io.tiklab.kaelthas.medium.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 消息渠道表(App,企业微信,站内信,邮箱)
 */
@Entity
@Table(name = "mtc_medium")
public class MediumEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 消息名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 消息类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 消息状态（1，开启   0，关闭）
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 消息描述
     */
    @Column(name = "details")
    private String details;

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}




