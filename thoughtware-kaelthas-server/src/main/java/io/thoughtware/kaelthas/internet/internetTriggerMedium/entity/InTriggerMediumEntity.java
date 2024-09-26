package io.thoughtware.kaelthas.internet.internetTriggerMedium.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_internet_trigger_medium")
public class InTriggerMediumEntity {

    /**
     * id
     */
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 触发器id
     */
    @Column(name = "trigger_id")
    private String triggerId;

    /**
     * 媒介id
     */
    @Column(name = "medium_id")
    private String mediumId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }
}
