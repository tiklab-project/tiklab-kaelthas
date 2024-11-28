package io.tiklab.kaelthas.host.templateMonitor.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 废弃表,模板下的监控项
 */
@Entity
@Table(name = "mtc_template_monitor")
public class TemplateMonitorEntity {

    @Id
    @GeneratorValue(length = 12, strategy = GeneratorType.uuid)
    @Column(name = "id")
    private String id;

    //模板id
    @Column(name = "template_id")
    private String templateId;

    //监控项名称
    @Column(name = "name")
    private String name;

    //监控指标的id
    @Column(name = "monitor_item_id")
    private String monitorItemId;

    //间隔时间
    @Column(name = "interval_time")
    private String intervalTime;

    //数据保留时间
    @Column(name = "data_retention_time")
    private String dataRetentionTime;

    //监控项状态
    @Column(name = "monitor_status")
    private Integer monitorStatus;

    /**
     * 废弃字段,监控项监控数据是否成功
     */
    @Column(name = "information")
    private String information;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonitorItemId() {
        return monitorItemId;
    }

    public void setMonitorItemId(String monitorItemId) {
        this.monitorItemId = monitorItemId;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getDataRetentionTime() {
        return dataRetentionTime;
    }

    public void setDataRetentionTime(String dataRetentionTime) {
        this.dataRetentionTime = dataRetentionTime;
    }

    public Integer getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(Integer monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

}
