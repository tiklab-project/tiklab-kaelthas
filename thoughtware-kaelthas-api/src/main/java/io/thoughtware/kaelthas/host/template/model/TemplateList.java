package io.thoughtware.kaelthas.host.template.model;

import io.thoughtware.core.page.Page;

public class TemplateList {

    private String id;

    //模板名称
    private String name;

    //是否启用
    private Integer isOpen;

    //主机组id
    private String superiorId;

    //监控项数量
    private String monitorNum;

    //模板描述
    private String describe;

    private Page pageParam = new Page();

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

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public String getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(String monitorNum) {
        this.monitorNum = monitorNum;
    }
}
