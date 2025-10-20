package io.tiklab.kaelthas.kubernetes.kubernetesInfo.entity;

import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * k8s监控信息表
 */
@Table(name = "mtc_ku_overview")
@Entity
public class KuOverviewEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * kuid
     */
    @Column(name = "ku_id")
    private String kuId;

    /**
     * kuItemId
     */
    @Column(name = "ku_item_id")
    private String kuItemId;


    /**
     * 信息
     */
    @Column(name = "report_data")
    private String reportData;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

    @Column(name = "create_time")
    private Timestamp createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKuId() {
        return kuId;
    }

    public void setKuId(String kuId) {
        this.kuId = kuId;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getKuItemId() {
        return kuItemId;
    }

    public void setKuItemId(String kuItemId) {
        this.kuItemId = kuItemId;
    }
}
