package io.tiklab.kaelthas.kubernetes.monitor.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * k8s的监控项
 */
@Table(name = "mtc_ku_monitor")
@Entity
public class KuMonitorEntity {

    /**
     * id
     */
    @Id
    @GeneratorValue(length = 12,strategy = GeneratorType.uuid)
    @Column(name = "id")
    private String id;

    /**
     * k8s信息表id
     */
    @Column(name = "ku_id")
    private String kuId;

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
     * dbitem表的id
     */
    @Column(name = "ku_item_id")
    private String kuItemId;

    /**
     * 模板复制的时候使用,将本表的监控项复制一份出来
     */
    @Column(name = "monitor_id")
    private String monitorId;

    /**
     * k8s中的namespace
     */
    @Column(name = "expression")
    private String expression;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKuId() {
        return kuId;
    }

    public void setKuId(String kuId) {
        this.kuId = kuId;
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

    public String getKuItemId() {
        return kuItemId;
    }

    public void setKuItemId(String kuItemId) {
        this.kuItemId = kuItemId;
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
