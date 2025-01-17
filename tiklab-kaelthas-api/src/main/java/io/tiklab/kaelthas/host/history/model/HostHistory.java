package io.tiklab.kaelthas.host.history.model;


import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库历史数据表信息
 */
@Join
@Mapper
public class HostHistory {

    private String id;

    /**
     * 监控的主机id
     */
    private String hostId;

    /**
     * 监控项的id
     */
    private String monitorId;

    /**
     * 图形id
     */
    private String graphicsId;

    /**
     * 数据上报的时间
     */
    private String gatherTime;

    /**
     * 上报的数据
     */
    private String reportData;

    /**
     * 来源
     */
    private Integer source = 0;

    /**
     * 监控项指标的id
     */
    private String monitorItemId;

    /**
     * 间隔时间
     */
    private String intervalTime;

    //资源大类
    private String dataCategories;

    /**
     * 数据类型
     */
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

    /**
     * 触发器id
     */
    private String triggerId;

    /**
     * 数据大小范围
     */
    private Integer dataRange;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 租户id
     */
    private String tenant;

    /**
     * 描述
     */
    private String describe;

    /**
     * 图形名称
     */
    private String graphicsName;

    /**
     * 监控项数值的list,用于图形展示
     */
    private List<String> data = new ArrayList<>();

    /**
     * 时间list,用于图形展示
     */
    private List<String> dataTimes = new ArrayList<>();

    /**
     * 字符串的监控项ids
     */
    private String monitorIds;

    /**
     * 监控项ids,list
     */
    private List<String> monitorIdList = new ArrayList<>();

    /**
     * 存储触发器的信息,用于图形展示
     */
    private List<Map<String,String>> mapList = new ArrayList<>();

    //分页参数
    private Page pageParam = new Page();

    /**
     * 问题
     */
    private String problem;

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

    public String getGraphicsId() {
        return graphicsId;
    }

    public void setGraphicsId(String graphicsId) {
        this.graphicsId = graphicsId;
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

    public String getDataCategories() {
        return dataCategories;
    }

    public void setDataCategories(String dataCategories) {
        this.dataCategories = dataCategories;
    }

    public String getDataCate() {
        return dataCate;
    }

    public void setDataCate(String dataCate) {
        this.dataCate = dataCate;
    }

    public String getDataSubclass() {
        return dataSubclass;
    }

    public void setDataSubclass(String dataSubclass) {
        this.dataSubclass = dataSubclass;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUsability() {
        return usability;
    }

    public void setUsability(Integer usability) {
        this.usability = usability;
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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
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

    public List<String> getMonitorIdList() {
        return monitorIdList;
    }

    public void setMonitorIdList(List<String> monitorIdList) {
        this.monitorIdList = monitorIdList;
    }

    public List<Map<String, String>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, String>> mapList) {
        this.mapList = mapList;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}