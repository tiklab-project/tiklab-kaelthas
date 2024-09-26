package io.thoughtware.kaelthas.host.trigger.model;

import io.thoughtware.core.page.Page;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.kaelthas.host.monitor.model.HostMonitor;
import io.thoughtware.kaelthas.host.triggerExpression.model.TriggerExpression;

import java.util.List;

@Join
@Mapper
public class Trigger {

    private String id;

    //触发器名称
    private String name;

    //触发器状态 1.已启用 2.未启用
    private Integer state;

    //触发器创建来源(1.通过主机中的监控项创建的,2.通过模板当中的监控项创建的)
    private Integer source;

    //严重性 (1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类)
    private Integer severityLevel;

    //触发器描述
    private String describe;

    //主机id
    private String hostId;

    //监控项id
    private String monitorId;

    //监控项信息
    @Mappings({
            @Mapping(source = "monitor.id", target = "monitorId")
    })
    @JoinQuery(key = "id")
    private HostMonitor monitor;

    private List<TriggerExpression> function;

    //触发器表达式的id
    private String expressionId;

    //运算符（1.>  2.<  3.=  4.>=  5.<=  6.<>)
    private Integer operator;

    //表达式的数值
    private Integer numericalValue;

    private String expression;

    //通知消息类型
    private Integer mediumType;

    private List<String> mediumIds;

    private String mediumId;

    private Integer scheme;


    private Integer rangeTime;

    private Integer percentage;

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

    public HostMonitor getMonitor() {
        return monitor;
    }

    public void setMonitor(HostMonitor monitor) {
        this.monitor = monitor;
    }

    public List<TriggerExpression> getFunction() {
        return function;
    }

    public void setFunction(List<TriggerExpression> function) {
        this.function = function;
    }

    public String getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(String expressionId) {
        this.expressionId = expressionId;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getNumericalValue() {
        return numericalValue;
    }

    public void setNumericalValue(Integer numericalValue) {
        this.numericalValue = numericalValue;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getMediumType() {
        return mediumType;
    }

    public void setMediumType(Integer mediumType) {
        this.mediumType = mediumType;
    }

    public List<String> getMediumIds() {
        return mediumIds;
    }

    public void setMediumIds(List<String> mediumIds) {
        this.mediumIds = mediumIds;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
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

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
