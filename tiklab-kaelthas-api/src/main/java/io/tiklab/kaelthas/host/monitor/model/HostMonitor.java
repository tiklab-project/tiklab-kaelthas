package io.tiklab.kaelthas.host.monitor.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.JoinField;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;

import java.util.List;

@Join
@Mapper
public class HostMonitor {

    private String id;

    //主机id
    private String hostId;

    //监控项名称
    private String name;

    //监控指标的id
    private String monitorItemId;

    //监控指标的对象
    @Mappings({
            @Mapping(source = "monitorItem.id",target = "monitorItemId")
    })
    @JoinField(key = "id")
    private MonitorItem monitorItem;

    //间隔时间
    private String intervalTime;

    //数据保留时间
    private String dataRetentionTime;

    //监控项状态
    private Integer monitorStatus;

    //监控项来源
    private Integer source;

    //模板id
    private String templateId;

    private List<String> templateIds;

    //监控项表达式
    private String expression;

    //监控项是否成功的消息
    private String information;

    //监控项大类
    private String categories;

    //监控项小类
    private String dataType;

    private Integer reportType;

    private String hostType;

    private String datName;

    private String username;

    private String password;

    private String ip;

    private String port;

    private String dbType;

    private Page pageParam = new Page();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonitorItemId() {
        return monitorItemId;
    }

    public void setMonitorItemId(String monitorItemId) {
        this.monitorItemId = monitorItemId;
    }

    public MonitorItem getMonitorItem() {
        return monitorItem;
    }

    public void setMonitorItem(MonitorItem monitorItem) {
        this.monitorItem = monitorItem;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getDataRetentionTime() {
        return dataRetentionTime;
    }

    public void setDataRetentionTime(String dataRetentionTime) {
        this.dataRetentionTime = dataRetentionTime;
    }

    public Integer getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(Integer monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<String> getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(List<String> templateIds) {
        this.templateIds = templateIds;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public String getDatName() {
        return datName;
    }

    public void setDatName(String datName) {
        this.datName = datName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
