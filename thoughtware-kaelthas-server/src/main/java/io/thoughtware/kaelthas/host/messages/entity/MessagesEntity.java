package io.thoughtware.kaelthas.host.messages.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_message")
public class MessagesEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "medium_id")
    private String mediumId;

    @Column(name = "alarm_id")
    private String alarmId;

    @Column(name = "information")
    private String information;

    @Column(name = "send_time")
    private String sendTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
