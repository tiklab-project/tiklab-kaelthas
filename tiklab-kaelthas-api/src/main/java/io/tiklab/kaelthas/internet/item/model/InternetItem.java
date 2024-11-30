package io.tiklab.kaelthas.internet.item.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class InternetItem {

    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 描述
     */
    private String describe;

    /**
     * 网络类型
     */
    private Integer internetType;

    /**
     * 数据类型(1.数值,2.json)
     */
    private Integer reportType;

    /**
     * 数据是否能够被监控项使用
     */
    private Integer isOptional;

    /**
     * 分页参数
     */
    private Page paramPage = new Page();

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

    public Page getParamPage() {
        return paramPage;
    }

    public void setParamPage(Page paramPage) {
        this.paramPage = paramPage;
    }
}
