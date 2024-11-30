package io.tiklab.kaelthas.internet.monitor.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 网络监控的监控项
 */
@Entity
@Table(name = "mtc_internet_monitor")
public class InternetMonitorEntity {

    /**
     * id
     */
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 数据源id
     */
    @Column(name = "internet_id")
    private String internetId;

    /**
     * 监控项名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 数据保留时间
     */
    @Column(name = "retention_time")
    private Integer retentionTime;

    /**
     * 监控项状态(1. 已启用，2. 未启用）
     */
    @Column(name = "status")
    private Integer status;

    /**
     * internet_item表的id
     */
    @Column(name = "internet_item_id")
    private String internetItemId;

    /**
     * 模板复制的时候使用,将本表的监控项复制一份出来
     */
    @Column(name = "monitor_id")
    private String monitorId;

    /**
     * 表达式
     */
    @Column(name = "expression")
    private String expression;

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
}
