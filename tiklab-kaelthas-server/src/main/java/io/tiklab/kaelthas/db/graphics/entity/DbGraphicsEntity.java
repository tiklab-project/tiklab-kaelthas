package io.tiklab.kaelthas.db.graphics.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 监控数据库的图形信息存储表
 */
@Entity
@Table(name = "mtc_db_graphics")
public class DbGraphicsEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 监控数据库下图形的名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 图形描述
     */
    @Column(name = "describe")
    private String describe;

    /**
     * 监控数据库id
     */
    @Column(name = "db_id")
    private String dbId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
}
