package io.thoughtware.kaelthas.host.host.model;

public class HostList {

    private String id;

    //主机名称
    private String name;

    //主机ip
    private String ip;

    //模板id
    private String templateId;

    //主机组id
    private String hostGroupId;

    //可见性
    private Integer visibility;

    //颜色
    private Integer color;

    //模板数量
    private String templateNum;

    //监控项数量
    private String monitorNum;

    //触发器数量
    private String triggerNum;

    //图形数量
    private String graphicNum;

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

    //监控项数量
    private String countMonitor;

    public String getCountMonitor() {
        return countMonitor;
    }

    public void setCountMonitor(String countMonitor) {
        this.countMonitor = countMonitor;
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

    public String getTemplateNum() {
        return templateNum;
    }

    public void setTemplateNum(String templateNum) {
        this.templateNum = templateNum;
    }

    public String getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(String monitorNum) {
        this.monitorNum = monitorNum;
    }

    public String getTriggerNum() {
        return triggerNum;
    }

    public void setTriggerNum(String triggerNum) {
        this.triggerNum = triggerNum;
    }

    public String getGraphicNum() {
        return graphicNum;
    }

    public void setGraphicNum(String graphicNum) {
        this.graphicNum = graphicNum;
    }
}
