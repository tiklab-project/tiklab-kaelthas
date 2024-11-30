package io.tiklab.kaelthas.kubernetes.triggerMedium.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 触发器和通知消息渠道的中间表
 */
@Mapper
@Join
public class KuTriggerMedium {

    private String id;

    /**
     * 触发器id
     */
    private String triggerId;

    /**
     * 通知消息渠道id
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
