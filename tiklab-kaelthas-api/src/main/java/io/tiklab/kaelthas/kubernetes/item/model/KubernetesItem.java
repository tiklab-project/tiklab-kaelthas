package io.tiklab.kaelthas.kubernetes.item.model;

import io.tiklab.dal.jpa.annotation.Column;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * k8s监控项的字典表
 */
@Join
@Mapper
public class KubernetesItem {
    /**
     *  id
     */
    private String id;

    /**
     *  表达式
     */
    private String expression;

    /**
     *  描述
     */
    private String describe;

    /**
     *  监控类型
     */
    private String kubernetesType;

    /**
     * 数据类型(1.数值,2.json 3. )
     */

    private Integer reportType;


    /**
     *  名字
     */
    private String name;

    public String getId() {
        return id;
    }

    public KubernetesItem setId(String id) {
        this.id = id;
        return this;
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

    public String getKubernetesType() {
        return kubernetesType;
    }

    public void setKubernetesType(String kubernetesType) {
        this.kubernetesType = kubernetesType;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
