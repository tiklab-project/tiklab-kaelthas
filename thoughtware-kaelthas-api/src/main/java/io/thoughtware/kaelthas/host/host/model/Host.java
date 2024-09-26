package io.thoughtware.kaelthas.host.host.model;

import io.thoughtware.core.page.Page;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.kaelthas.host.graphics.model.Graphics;
import io.thoughtware.kaelthas.host.hostGroup.model.HostGroup;
import io.thoughtware.kaelthas.host.monitor.model.HostMonitor;
import io.thoughtware.kaelthas.host.template.model.Template;
import io.thoughtware.kaelthas.host.trigger.model.Trigger;

import java.util.List;

@Join
@Mapper
public class Host {

    private String id;

    //主机名称
    private String name;

    private List<String> nameList;

    //主机ip
    private String ip;

    //模板id
    private String templateId;

    //主机组id
    private String hostGroupId;

    //主机组信息
    @Mappings({
            @Mapping(source = "hostGroup.id", target = "hostGroupId")
    })
    @JoinQuery(key = "id")
    private HostGroup hostGroup;

    //模板信息
    private List<Template> templates;

    //监控项信息
    private List<HostMonitor> monitors;

    //触发器信息
    private List<Trigger> triggers;

    //图形信息
    private List<Graphics> graphics;

    //模板数量
    private Integer templateNum;

    //监控项数量
    private Integer monitorNum;

    //触发器数量
    private Integer triggerNum;

    //图形数量
    private Integer graphicNum;

    //告警数量
    private Integer alarmNum;

    //主机状态 1.已启用 2.未启用
    private Integer state;

    //主机可用性 1.可用 2.不可用 3.未知
    private Integer usability;

    //主机描述
    private String describe;

    //主机创建时间
    private String createTime;

    //主机创建时间
    private String updateTime;

    //监控大类
    private String dataCategories;

    //可见性
    private Integer visibility;

    //颜色
    private Integer color;

    private String message;

    private String dataCate;

    private Integer severityLevel;

    private List<List<Integer>> leaveList;

    private Page pageParam = new Page();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDataCate() {
        return dataCate;
    }

    public void setDataCate(String dataCate) {
        this.dataCate = dataCate;
    }

    public String getDataCategories() {
        return dataCategories;
    }

    public void setDataCategories(String dataCategories) {
        this.dataCategories = dataCategories;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<List<Integer>> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<List<Integer>> leaveList) {
        this.leaveList = leaveList;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostGroupId() {
        return hostGroupId;
    }

    public void setHostGroupId(String hostGroupId) {
        this.hostGroupId = hostGroupId;
    }

    public HostGroup getHostGroup() {
        return hostGroup;
    }

    public void setHostGroup(HostGroup hostGroup) {
        this.hostGroup = hostGroup;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public List<HostMonitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<HostMonitor> monitors) {
        this.monitors = monitors;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public List<Graphics> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<Graphics> graphics) {
        this.graphics = graphics;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUsability() {
        return usability;
    }

    public void setUsability(Integer usability) {
        this.usability = usability;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTemplateNum() {
        return templateNum;
    }

    public void setTemplateNum(Integer templateNum) {
        this.templateNum = templateNum;
    }

    public Integer getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(Integer monitorNum) {
        this.monitorNum = monitorNum;
    }

    public Integer getTriggerNum() {
        return triggerNum;
    }

    public void setTriggerNum(Integer triggerNum) {
        this.triggerNum = triggerNum;
    }

    public Integer getGraphicNum() {
        return graphicNum;
    }

    public void setGraphicNum(Integer graphicNum) {
        this.graphicNum = graphicNum;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(Integer alarmNum) {
        this.alarmNum = alarmNum;
    }

    public Integer getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }
}
