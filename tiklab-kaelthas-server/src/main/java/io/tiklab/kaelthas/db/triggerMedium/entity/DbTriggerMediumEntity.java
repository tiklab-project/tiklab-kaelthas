package io.tiklab.kaelthas.db.triggerMedium.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 监控数据库下触发器与通知渠道的关联表
 */
@Entity
@Table(name = "mtc_db_trigger_medium")
public class DbTriggerMediumEntity {

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
     * 通知渠道(例如,钉钉,企业微信)的id(字典表的id)
     */
    @Column(name = "medium_id")
    private String mediumId;

    /**
     * 监控数据库的id
     */
    @Column(name = "db_id")
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
