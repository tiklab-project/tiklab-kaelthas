package io.tiklab.kaelthas.db.dbDynamic.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 监控数据库的动态信息表
 */
@Entity
@Table(name = "mtc_db_dynamic")
public class DbDynamicEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 检控数据库的id
     */
    @Column(name = "db_id")
    private String dbId;

    /**
     * 动态信息
     */
    @Column(name = "name")
    private String name;

    /**
     * 动态时间
     */
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
