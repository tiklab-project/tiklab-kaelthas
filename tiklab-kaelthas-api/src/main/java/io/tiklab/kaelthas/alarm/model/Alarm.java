package io.tiklab.kaelthas.alarm.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinField;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.trigger.model.Trigger;

/**
 * 告警表
 */
@Join
@Mapper
public class Alarm {

    private String id;

    /**
     * 监控主机id、监控数据库id、监控k8sid，监控网络id
     */
    private String hostId;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备ip
     */
    private String ip;

    /**
     * 触发器名称
     */
    private String triggerName;

    /**
     * 触发器实体类
     */
    @Mappings({
            @Mapping(source = "trigger.id", target = "triggerId")
    })
    @JoinField(key = "id")
    private Trigger trigger;

    /**
     * 主机实体类
     */
    @Mappings({
            @Mapping(source = "host.id", target = "hostId")
    })
    @JoinField(key = "id")
    private Host host;

    /**
     * 监控项id
     */
    private String monitorId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 告警持续时间
     */
    private String duration;

    /**
     * 发送的消息(告警消息)
     */
    private String sendMessage;

    /**
     * 触发器id
     */
    private String triggerId;

    /**
     * 告警时间
     */
    private String alertTime;

    /**
     * 上报数据的时间
     */
    private String gatherTime;

    /**
     * 告警等级
     */
    private Integer severityLevel;

    /**
     * 媒介类型
     */
    private String mediumType;

    /**
     * 是否发送
     */
    private Integer isSend;

    /**
     * 告警数量
     */
    private Integer alarmNum;

    /**
     * 解决时间
     */
    private String resolutionTime;

    /**
     * 机器类型,(1,主机,2.数据库,3.k8s,4.网络监控)
     */
    private Integer machineType;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public String getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(String gatherTime) {
        this.gatherTime = gatherTime;
    }

    public Integer getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getMediumType() {
        return mediumType;
    }

    public void setMediumType(String mediumType) {
        this.mediumType = mediumType;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public Integer getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(Integer alarmNum) {
        this.alarmNum = alarmNum;
    }

    public String getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(String resolutionTime) {
        this.resolutionTime = resolutionTime;
    }

    public Integer getMachineType() {
        return machineType;
    }

    public void setMachineType(Integer machineType) {
        this.machineType = machineType;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
}
