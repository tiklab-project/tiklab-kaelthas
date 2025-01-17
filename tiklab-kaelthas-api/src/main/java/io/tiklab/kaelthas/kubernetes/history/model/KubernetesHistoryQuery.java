package io.tiklab.kaelthas.kubernetes.history.model;

public class KubernetesHistoryQuery {


    //监控的k8s id
    private String kuId;

    //监控项目id
    private String kuMonitorId;


    //数据类型
    private String dataCate;

    //开始时间
    private String beginTime;

    //结束时间
    private String endTime;

    public String getKuId() {
        return kuId;
    }

    public void setKuId(String kuId) {
        this.kuId = kuId;
    }

    public String getKuMonitorId() {
        return kuMonitorId;
    }

    public void setKuMonitorId(String kuMonitorId) {
        this.kuMonitorId = kuMonitorId;
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
