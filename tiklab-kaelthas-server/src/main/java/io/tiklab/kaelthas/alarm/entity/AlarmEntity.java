package io.tiklab.kaelthas.alarm.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_alarm")
public class AlarmEntity {
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "host_id")
    private String hostId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "send_message")
    private String sendMessage;

    @Column(name = "trigger_id")
    private String triggerId;

    @Column(name = "alert_time")
    private String alertTime;

    @Column(name = "duration")
    private String duration;

    @Column(name = "is_send")
    private String idSend;

    @Column(name = "resolution_time")
    private String resolutionTime;

    @Column(name = "machine_type")
    private Integer machineType;

    @Column(name = "ip")
    private String ip;

    @Column(name = "host_name")
    private String hostName;

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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }
}