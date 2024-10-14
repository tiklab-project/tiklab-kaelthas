package io.tiklab.kaelthas.host.trigger.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_trigger")
public class TriggerEntity {
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    //触发器名称
    @Column(name = "name")
    private String name;

    //触发器状态 1.已启用 2.未启用
    @Column(name = "state")
    private Integer state;

    //触发器创建来源(1.通过主机中的监控项创建的,2.通过模板当中的监控项创建的)
    @Column(name = "source")
    private Integer source;

    //严重性 (1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类)
    @Column(name = "severity_level")
    private Integer severityLevel;

    //触发器描述
    @Column(name = "describe")
    private String describe;

    //主机id
    @Column(name = "host_id")
    private String hostId;

    //监控项id
    @Column(name = "monitor_id")
    private String monitorId;

    @Column(name = "medium_type")
    private Integer mediumType;

    @Column(name = "expression")
    private String expression;

    @Column(name = "scheme")
    private Integer scheme;

    @Column(name = "range_time")
    private Integer rangeTime;

    @Column(name = "percentage")
    private Integer percentage;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getMediumType() {
        return mediumType;
    }

    public void setMediumType(Integer mediumType) {
        this.mediumType = mediumType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getScheme() {
        return scheme;
    }

    public void setScheme(Integer scheme) {
        this.scheme = scheme;
    }

    public Integer getRangeTime() {
        return rangeTime;
    }

    public void setRangeTime(Integer rangeTime) {
        this.rangeTime = rangeTime;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
