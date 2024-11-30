package io.tiklab.kaelthas.kubernetes.item.model;

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
}
