package io.tiklab.kaelthas.db.monitor.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 数据库下的监控项
 */
@Entity
@Table(name = "mtc_db_monitor")
public class DbMonitorEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 数据源id
     */
    @Column(name = "db_id")
    private String dbId;

    /**
     * 监控项名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 监控数据库名称
     */
    @Column(name = "dat_name")
    private String datName;

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
    @Column(name = "db_item_id")
    private Integer dbItemId;

    /**
     * 模板复制的时候使用,将本表的监控项复制一份出来
     */
    @Column(name = "monitor_id")
    private String monitorId;

    /**
     * 监控项来源(1.系统指标,2.自定义SQL)当前没有自定义SQL
     */
    @Column(name = "monitor_type")
    private Integer monitorType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatName() {
        return datName;
    }

    public void setDatName(String datName) {
        this.datName = datName;
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

    public Integer getDbItemId() {
        return dbItemId;
    }

    public void setDbItemId(Integer dbItemId) {
        this.dbItemId = dbItemId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }
}
