package io.tiklab.kaelthas.host.triggerExpression.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * (暂未使用),主机触发器的表达式表,当前主机的触发表达式存放在触发器当中
 */
@Entity
@Table(name = "mtc_trigger_expression")
public class TriggerExpressionEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "expression")
    private String expression;

    @Column(name = "operator")
    private Integer operator;

    @Column(name = "numerical_value")
    private Integer numericalValue;

    @Column(name = "trigger_id")
    private String triggerId;

    @Column(name = "source")
    private Integer source;

    @Column(name = "monitor_id")
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