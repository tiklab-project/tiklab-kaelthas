package io.tiklab.kaelthas.kubernetes.trigger.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * k8s的触发器
 */
@Entity
@Table(name = "mtc_ku_trigger")
public class KuTriggerEntity {

    /**
     * 触发器主键
     */
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 触发器名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 描述（对触发器的描述）
     */
    @Column(name = "describe")
    private String describe;

    /**
     * 触发器状态(1. 已启用，2. 未启用）
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 严重性（1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类）
     */
    @Column(name = "severity_level")
    private Integer severityLevel;

    /**
     * 数据库id
     */
    @Column(name = "ku_id")
    private String kuId;

    /**
     * 触发器关系表达式
     */
    @Column(name = "expression")
    private String expression;

    /**
     * 1.平均值2.百分比,3.最后一个值
     */
    @Column(name = "scheme")
    private Integer scheme;

    /**
     * 时间范畴为几分钟的数据
     */
    @Column(name = "range_time")
    private Integer rangeTime;

    /**
     * 触发器的百分比达到多少进行告警
     */
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getKuId() {
        return kuId;
    }

    public void setKuId(String kuId) {
        this.kuId = kuId;
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
