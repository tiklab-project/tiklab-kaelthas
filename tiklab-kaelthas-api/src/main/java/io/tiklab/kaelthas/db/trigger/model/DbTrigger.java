package io.tiklab.kaelthas.db.trigger.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.ArrayList;
import java.util.List;

/**
 * 监控数据库触发器
 */
@Join
@Mapper
public class DbTrigger {

    private String id;

    /**
     * 触发器名称
     */
    private String name;

    /**
     * 触发器描述，发送企业微信消息的时候需要使用
     */
    private String describe;

    /**
     * 触发器的状态，是否开启触发器
     */
    private Integer state;

    /**
     * 触发器的告警等级
     */
    private Integer severityLevel;

    /**
     * 监控数据库的id
     */
    private String dbId;

    /**
     * 触发器表达式，触发的时候需要使用
     */
    private String expression;

    /**
     * 触发器等级(1.最后值判断,2.平均值判断,3.百分比判断)
     */
    private Integer scheme;

    /**
     * 采集距今多少分钟的时间进行判断,只有平均值和百分比才有这个字段
     */
    private Integer rangeTime;

    /**
     * 百分比,触发器的数据达到整体数据的百分比触发
     */
    private Integer percentage;

    /**
     * 消息渠道的id
     */
    private String mediumId;

    /**
     * 消息渠道的list,修改和新增触发器的时候使用
     */
    private List<String> mediumType = new ArrayList<>();

    /**
     * 分页参数
     */
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

    public List<String> getMediumType() {
        return mediumType;
    }

    public void setMediumType(List<String> mediumType) {
        this.mediumType = mediumType;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
