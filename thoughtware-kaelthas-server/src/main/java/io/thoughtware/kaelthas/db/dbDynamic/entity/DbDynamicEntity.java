package io.thoughtware.kaelthas.db.dbDynamic.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_db_dynamic")
public class DbDynamicEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "db_id")
    private String dbId;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
