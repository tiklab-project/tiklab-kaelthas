package io.thoughtware.kaelthas.host.trigger.model;

public class TriggerList {

    private String id;

    //触发器名称
    private String name;

    private String monitorName;

    //触发器状态 1.已启用 2.未启用
    private Integer triggerStatus;

    //严重性 (1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类)
    private Integer severityLevel;

    //触发器描述
    private String describe;

    //主机id
    private String hostId;

    //监控项id
    private String monitorId;

    //触发器表达式的id
    private String expressionId;

    //关系表达式
    private String expression;

    //运算符（1.>  2.<  3.=  4.>=  5.<=  6.<>)
    private Integer operator;

    //表达式的数值
    private Integer numericalValue;

    //通知消息类型
    private Integer mediumType;

    private Integer source;

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

    public Integer getTriggerStatus() {
        return triggerStatus;
    }

    public void setTriggerStatus(Integer triggerStatus) {
        this.triggerStatus = triggerStatus;
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

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(String expressionId) {
        this.expressionId = expressionId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
