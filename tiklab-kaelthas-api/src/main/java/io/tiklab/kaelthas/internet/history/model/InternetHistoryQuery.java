package io.tiklab.kaelthas.internet.history.model;

public class InternetHistoryQuery {


    //监控的数据库id
    private String internetId;

    //监控项目id
    private String internetMonitorId;


    //数据类型
    private String dataCate;

    //开始时间
    private String beginTime;

    //结束时间
    private String endTime;

    public String getInternetId() {
        return internetId;
    }

    public void setInternetId(String internetId) {
        this.internetId = internetId;
    }

    public String getInternetMonitorId() {
        return internetMonitorId;
    }

    public void setInternetMonitorId(String internetMonitorId) {
        this.internetMonitorId = internetMonitorId;
    }

    public String getDataCate() {
        return dataCate;
    }

    public void setDataCate(String dataCate) {
        this.dataCate = dataCate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
