package io.thoughtware.kaelthas.host.graphicsMonitor.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_graphics_monitor")
public class GraphicsMonitorEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "monitor_id")
    private String monitorId;

    @Column(name = "graphics_id")
    private String graphicsId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getGraphicsId() {
        return graphicsId;
    }

    public void setGraphicsId(String graphicsId) {
        this.graphicsId = graphicsId;
    }
}
