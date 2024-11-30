package io.tiklab.kaelthas.db.triggerMedium.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 触发器和消息渠道中间表
 */
@Join
@Mapper
public class DbTriggerMedium {


    private String id;

    //触发器id
    private String triggerId;

    //消息渠道id
    private String mediumId;

    //监控数据库id
    private String dbId;

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

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
}
