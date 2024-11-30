package io.tiklab.kaelthas.internet.triggerMedium.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class InTriggerMedium {

    /**
     * id
     */
    private String id;

    /**
     * 触发器id
     */
    private String triggerId;

    /**
     * 媒介id
     */
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
