package io.tiklab.kaelthas.internet.monitor.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class InternetMonitor {

    /**
     * id
     */
    private String id;

    /**
     * 数据源id
     */
    private String internetId;

    /**
     * 监控项名称
     */
    private String name;

    /**
     * 数据保留时间
     */
    private Integer retentionTime;

    /**
     * 监控项状态(1. 已启用，2. 未启用）
     */
    private Integer status;

    /**
     * internet_item表的id
     */
    private String internetItemId;

    /**
     * 模板复制的时候使用,将本表的监控项复制一份出来
     */
    private String monitorId;

    /**
     * 表达式
     */
    private String expression;

    private Page paramPage = new Page();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternetId() {
        return internetId;
    }

    public void setInternetId(String internetId) {
        this.internetId = internetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(Integer retentionTime) {
        this.retentionTime = retentionTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInternetItemId() {
        return internetItemId;
    }

    public void setInternetItemId(String internetItemId) {
        this.internetItemId = internetItemId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Page getParamPage() {
        return paramPage;
    }

    public void setParamPage(Page paramPage) {
        this.paramPage = paramPage;
    }
}
