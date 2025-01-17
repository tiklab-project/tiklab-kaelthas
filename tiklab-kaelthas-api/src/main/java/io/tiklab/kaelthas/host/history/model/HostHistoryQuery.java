package io.tiklab.kaelthas.host.history.model;

public class HostHistoryQuery {


    //监控的主机id
    private String hostId;

    //监控项目id
    private String hostMonitorId;


    //数据类型
    private String dataCate;

    //开始时间
    private String beginTime;

    //结束时间
    private String endTime;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostMonitorId() {
        return hostMonitorId;
    }

    public void setHostMonitorId(String hostMonitorId) {
        this.hostMonitorId = hostMonitorId;
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
