package io.tiklab.kaelthas.collection.entity.vo;


import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Join
@Mapper
public class HistoryVo {

    private String id;

    private String hostId;

    private String monitorId;

    private String graphicsId;

    private String gatherTime;

    private String reportData;

    private Integer source = 0;

    private String monitorItemId;

    private String intervalTime;

    //资源大类
    private String dataCategories;

    private String dataCate;

    //资源小类
    private String dataSubclass;

    //数据类型(1.数字,2.json)
    private Integer reportType;

    //主机名称
    private String hostName;

    //监控项名称
    private String monitorName;

    //图表名称
    private String name;

    //主机可用性
    private Integer usability;

    //开始时间
    private String beginTime;

    //结束时间
    private String endTime;

    private String triggerId;

    private Integer dataRange;

    private String expression;

    private String tenant;

    private String describe;

    private String graphicsName;

    private List<String> data = new ArrayList<>();

    private List<String> dataTimes = new ArrayList<>();

    private String monitorIds;

    private List<String> monitorIdList = new ArrayList<>();

    private List<Map<String,String>> mapList = new ArrayList<>();

    //分页参数
    private Page pageParam = new Page();

    private String problem;

    public HistoryVo() {
    }

    public String getDataCate() {
        return dataCate;
    }

    public void setDataCate(String dataCate) {
        this.dataCate = dataCate;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<String> getMonitorIdList() {
        return monitorIdList;
    }

    public void setMonitorIdList(List<String> monitorIdList) {
        this.monitorIdList = monitorIdList;
    }

    public List<String> getDataTimes() {
        return dataTimes;
    }

    public void setDataTimes(List<String> dataTimes) {
        this.dataTimes = dataTimes;
    }

    public String getMonitorIds() {
        return monitorIds;
    }

    public void setMonitorIds(String monitorIds) {
        this.monitorIds = monitorIds;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public Integer getDataRange() {
        return dataRange;
    }

    public void setDataRange(Integer dataRange) {
        this.dataRange = dataRange;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getGraphicsId() {
        return graphicsId;
    }

    public void setGraphicsId(String graphicsId) {
        this.graphicsId = graphicsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getUsability() {
        return usability;
    }

    public void setUsability(Integer usability) {
        this.usability = usability;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
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

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
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

    public String getDataCategories() {
        return dataCategories;
    }

    public void setDataCategories(String dataCategories) {
        this.dataCategories = dataCategories;
    }

    public List<Map<String, String>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, String>> mapList) {
        this.mapList = mapList;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", hostId='" + hostId + '\'' +
                ", monitorId='" + monitorId + '\'' +
                ", graphicsId='" + graphicsId + '\'' +
                ", gatherTime='" + gatherTime + '\'' +
                ", reportData='" + reportData + '\'' +
                ", source=" + source +
                ", monitorItemId='" + monitorItemId + '\'' +
                ", intervalTime='" + intervalTime + '\'' +
                ", dataCategories='" + dataCategories + '\'' +
                ", dataCate='" + dataCate + '\'' +
                ", dataSubclass='" + dataSubclass + '\'' +
                ", reportType=" + reportType +
                ", hostName='" + hostName + '\'' +
                ", monitorName='" + monitorName + '\'' +
                ", name='" + name + '\'' +
                ", usability=" + usability +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", triggerId='" + triggerId + '\'' +
                ", dataRange=" + dataRange +
                ", expression='" + expression + '\'' +
                ", tenant='" + tenant + '\'' +
                ", describe='" + describe + '\'' +
                ", data=" + data +
                ", dataTimes=" + dataTimes +
                ", monitorIds='" + monitorIds + '\'' +
                ", monitorIdList=" + monitorIdList +
                ", mapList=" + mapList +
                ", pageParam=" + pageParam +
                ", problem='" + problem + '\'' +
                '}';
    }
}