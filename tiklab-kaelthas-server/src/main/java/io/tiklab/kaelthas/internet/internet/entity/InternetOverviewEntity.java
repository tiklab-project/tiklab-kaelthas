package io.tiklab.kaelthas.internet.internet.entity;

import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * 网络监控概览信息表
 */

@Table(name = "mtc_internet_overview")
@Entity
public class InternetOverviewEntity {

    /**
     * id
     */
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 信息
     */
    @Column(name = "report_data")
    private String reportData;



    /**
     * 网络id
     */
    @Column(name = "internet_id")
    private String internetId;


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

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public String getInternetId() {
        return internetId;
    }

    public void setInternetId(String internetId) {
        this.internetId = internetId;
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
}
