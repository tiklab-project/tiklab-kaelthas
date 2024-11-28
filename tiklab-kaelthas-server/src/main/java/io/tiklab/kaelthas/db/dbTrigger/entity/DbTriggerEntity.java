package io.tiklab.kaelthas.db.dbTrigger.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 监控数据库的触发器
 */
@Entity
@Table(name = "mtc_db_trigger")
public class DbTriggerEntity {

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
     * 触发器描述，发送企业微信消息的时候需要使用
     */
    @Column(name = "describe")
    private String describe;

    /**
     * 触发器的状态，是否开启触发器
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 触发器的告警等级
     */
    @Column(name = "severity_level")
    private Integer severityLevel;

    /**
     * 监控数据库的id
     */
    @Column(name = "db_id")
    private String dbId;

    /**
     * 触发器表达式，触发的时候需要使用
     */
    @Column(name = "expression")
    private String expression;

    /**
     * 触发器等级(1.最后值判断,2.平均值判断,3.百分比判断)
     */
    @Column(name = "scheme")
    private Integer scheme;

    /**
     * 采集距今多少分钟的时间进行判断,只有平均值和百分比才有这个字段
     */
    @Column(name = "range_time")
    private Integer rangeTime;

    /**
     * 百分比,触发器的数据达到整体数据的百分比触发
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

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
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
