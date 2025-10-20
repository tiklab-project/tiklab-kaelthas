package io.tiklab.kaelthas.internet.internet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.sql.Timestamp;

@Mapper
@Join
public class InternetOverview {

    /**
     * id
     */
    private String id;

    /**
     * 内容
     */
    private String reportData;

    /*
    * 监控的网络表id
    * */
    private String internetId;

    /**
     * 类型  系统：system、接口：pod
     */
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
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
