package io.thoughtware.kaelthas.history.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_history_fifteen_minute")
public class HistoryFifteenEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "host_id")
    private String hostId;

    @Column(name = "monitor_id")
    private String monitorId;

    @Column(name = "monitor_name")
    private String monitorName;

    @Column(name = "gather_time")
    private String gatherTime;

    @Column(name = "report_data")
    private String reportData;

    @Column(name = "source")
    private Integer source;

    @Column(name = "monitor_item_id")
    private String monitorItemId;

    @Column(name = "interval_time")
    private String intervalTime;

    @Column(name = "data_categories")
    private String dataCategories;

    @Column(name = "data_subclass")
    private String dataSubclass;

    @Column(name = "report_type")
    private Integer reportType;

    @Column(name = "expression")
    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getDataCategories() {
        return dataCategories;
    }

    public void setDataCategories(String dataCategories) {
        this.dataCategories = dataCategories;
    }

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

    public String getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(String gatherTime) {
        this.gatherTime = gatherTime;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getMonitorItemId() {
        return monitorItemId;
    }

    public void setMonitorItemId(String monitorItemId) {
        this.monitorItemId = monitorItemId;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getDataSubclass() {
        return dataSubclass;
    }

    public void setDataSubclass(String dataSubclass) {
        this.dataSubclass = dataSubclass;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
}