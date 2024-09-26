package io.thoughtware.kaelthas.host.triggerExpression.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

@Mapper
@Join
public class TriggerExpression {

    private String id;

    private String expression;

    private Integer operator;

    private Integer numericalValue;

    private String triggerId;

    private Integer source;

    private String monitorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }
}