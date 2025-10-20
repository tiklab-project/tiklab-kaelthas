package io.tiklab.kaelthas.alarm.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_alarm")
public class AlarmEntity {
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 监控主机id、监控数据库id、监控k8sid，监控网络id
     */
    @Column(name = "host_id")
    private String hostId;

    /**
     * 状态(1，已解决  2，未解决)
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 发送的消息(告警消息)
     */
    @Column(name = "send_message")
    private String sendMessage;

    /**
     * 触发器id
     */
    @Column(name = "trigger_id")
    private String triggerId;

    /**
     * 告警时间
     */
    @Column(name = "alert_time")
    private String alertTime;

    /**
     * 告警持续时间
     */
    @Column(name = "duration")
    private String duration;

    /**
     * 消息是否发送成功（1，成功   2，失败）功能没有做
     */
    @Column(name = "is_send")
    private String idSend;

    /**
     * 解决告警的时间
     */
    @Column(name = "resolution_time")
    private String resolutionTime;

    /**
     * 机器类型,(1,主机,2.数据库,3.k8s,4.网络监控)
     */
    @Column(name = "machine_type")
    private Integer machineType;

    /**
     * 设备ip
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 设备名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 告警等级
     */
    @Column(name = "severity_level")
    private Integer severityLevel;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getIdSend() {
        return idSend;
    }

    public void setIdSend(String idSend) {
        this.idSend = idSend;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }

}