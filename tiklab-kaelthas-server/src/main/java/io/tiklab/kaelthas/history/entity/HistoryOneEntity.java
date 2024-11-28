package io.tiklab.kaelthas.history.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_history_one_minute")
public class HistoryOneEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * (监控主机的id,监控数据库的id,监控k8s的id和监控网络的id上报到数据存储使用的字段),只不过字段是hostId
     */
    @Column(name = "host_id")
    private String hostId;

    /**
     * 监控项的id(也是上报数据监控项的id,主机监控,数据库监控,k8s监控和网络监控 的监控项id)
     */
    @Column(name = "monitor_id")
    private String monitorId;

    /**
     * 上报的数据
     */
    @Column(name = "report_data")
    private String reportData;

    @Column(name = "gather_time")
    private String gatherTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public String getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(String gatherTime) {
        this.gatherTime = gatherTime;
    }
}