package io.tiklab.kaelthas.kubernetes.item.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * k8s监控项的字典项
 */
@Entity
@Table(name = "mtc_ku_item")
public class KubernetesItemEntity {

    /**
     *  id
     */
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     *  表达式
     */
    @Column(name = "expression")
    private String expression;

    /**
     *  描述
     */
    @Column(name = "describe")
    private String describe;

    /**
     *  监控类型
     */
    @Column(name = "kubernetes_type")
    private String kubernetesType;

    /**
     * 数据类型(1.数值,2.json 3. )
     */
    @Column(name = "report_type")
    private Integer reportType;


    /**
     *  名字
     */
    @Column(name = "name")
    private String name;

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
