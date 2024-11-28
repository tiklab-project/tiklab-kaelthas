package io.tiklab.kaelthas.host.monitor.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 主机下的监控项,模板下创建监控项也是在这个表中
 */
@Entity
@Table(name = "mtc_host_monitor")
public class HostMonitorEntity {

    @Id
    @GeneratorValue(length = 12, strategy = GeneratorType.uuid)
    @Column(name = "id")
    private String id;

    /**
     * 主机下创建监控项的话就是主机id,模板下创建监控项的话就是模板id
     */
    @Column(name = "host_id")
    private String hostId;

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
     * 监控项表达式
     */
    @Column(name = "expression")
    private String expression;

    /**
     * 废弃字段
     */
    @Column(name = "information")
    private String information;

    /**
     * 来源(1.主机,2.模板)
     */
    @Column(name = "source")
    private Integer source;

    /**
     * 监控项id,为空的时候hostid为模板id,不为空的时候证明被主机引用了
     */
    @Column(name = "template_id")
    private String templateId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
