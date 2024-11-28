package io.tiklab.kaelthas.db.dbGraphicsMonitor.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 图形和监控项的中间表
 */
@Entity
@Table(name = "mtc_db_graphics_monitor")
public class DbGraphicsMonitorEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 图形id
     */
    @Column(name = "graphics_id")
    private String graphicsId;

    /**
     * 监控项id
     */
    @Column(name = "monitor_id")
    private String monitorId;

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

    public String getGraphicsId() {
        return graphicsId;
    }

    public void setGraphicsId(String graphicsId) {
        this.graphicsId = graphicsId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
}
