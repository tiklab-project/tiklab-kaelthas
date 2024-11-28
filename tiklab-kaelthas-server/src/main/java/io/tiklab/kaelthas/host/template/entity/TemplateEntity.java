package io.tiklab.kaelthas.host.template.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 模板表
 */
@Entity
@Table(name = "mtc_template")
public class TemplateEntity {

    @Id
    @GeneratorValue(length = 16)
    @Column(name = "id")
    private String id;

    //模板名称
    @Column(name = "name")
    private String name;

    //是否启用
    @Column(name = "is_open")
    private Integer isOpen;

    //主机组id
    @Column(name = "superior_id")
    private String superiorId;

    //模板描述
    @Column(name="describe")
    private String describe;

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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(String superiorId) {
        this.superiorId = superiorId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
