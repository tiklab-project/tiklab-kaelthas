package io.tiklab.kaelthas.db.dbTriggerMedium.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_db_trigger_medium")
public class DbTriggerMediumEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "trigger_id")
    private String triggerId;

    @Column(name = "medium_id")
    private String mediumId;

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
