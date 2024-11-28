package io.tiklab.kaelthas.internet.internetItem.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 网络监控的监控项字典表
 */
@Entity
@Table(name = "mtc_internet_item")
public class InternetItemEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 表达式
     */
    @Column(name = "expression")
    private String expression;

    /**
     * 描述
     */
    @Column(name = "describe")
    private String describe;

    /**
     * 网络类型
     */
    @Column(name = "internet_type")
    private Integer internetType;

    /**
     * 数据类型(1.数值,2.json)
     */
    @Column(name = "report_type")
    private Integer reportType;

    @Column(name = "is_optional")
    private Integer isOptional;

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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getInternetType() {
        return internetType;
    }

    public void setInternetType(Integer internetType) {
        this.internetType = internetType;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Integer isOptional) {
        this.isOptional = isOptional;
    }
}
