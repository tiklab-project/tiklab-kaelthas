package io.tiklab.kaelthas.kubernetes.kubernetesGraphics.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_ku_graphics")
public class KuGraphicsEntity {

    /**
     * id
     */
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 图形名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 描述
     */
    @Column(name = "describe")
    private String describe;

    /**
     * 集群id
     */
    @Column(name = "ku_id")
    private String kuId;

    /**
     * 1.来源于主机中监控项创建2.来源于模板中监控项创建
     */
    @Column(name = "source")
    private Integer source;

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

    public String getKuId() {
        return kuId;
    }

    public void setKuId(String kuId) {
        this.kuId = kuId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
