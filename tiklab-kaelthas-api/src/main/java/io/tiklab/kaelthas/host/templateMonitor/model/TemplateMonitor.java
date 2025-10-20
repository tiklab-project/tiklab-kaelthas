package io.tiklab.kaelthas.host.templateMonitor.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinField;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;

/**
 * 模板下的监控项
 */
@Join
@Mapper
public class TemplateMonitor {

    private String id;

    //模板id
    private String templateId;

    //监控项id
    private String monitorId;

    //监控项名称
    private String name;

    //监控指标的id
    private String monitorItemId;

    //监控指标的对象
    @Mappings({
            @Mapping(source = "monitorItem.id",target = "monitorItemId")
    })
    @JoinField(key = "id")
    private MonitorItem monitorItem;

    //间隔时间
    private String intervalTime;

    //数据保留时间
    private String dataRetentionTime;

    //监控项状态
    private Integer monitorStatus;

    //监控项来源
    private Integer monitorSource;

    private String expression;

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

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
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

    public MonitorItem getMonitorItem() {
        return monitorItem;
    }

    public void setMonitorItem(MonitorItem monitorItem) {
        this.monitorItem = monitorItem;
    }

    public Integer getMonitorSource() {
        return monitorSource;
    }

    public void setMonitorSource(Integer monitorSource) {
        this.monitorSource = monitorSource;
    }
}
