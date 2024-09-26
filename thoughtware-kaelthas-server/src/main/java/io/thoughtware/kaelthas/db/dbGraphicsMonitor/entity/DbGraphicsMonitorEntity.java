package io.thoughtware.kaelthas.db.dbGraphicsMonitor.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_db_graphics_monitor")
public class DbGraphicsMonitorEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "graphics_id")
    private String graphicsId;

    @Column(name = "monitor_id")
    private String monitorId;

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
}
