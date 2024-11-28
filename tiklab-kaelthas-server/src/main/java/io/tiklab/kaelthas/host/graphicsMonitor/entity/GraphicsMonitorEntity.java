package io.tiklab.kaelthas.host.graphicsMonitor.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 图形和监控项的关联表(中间表)
 */
@Entity
@Table(name = "mtc_graphics_monitor")
public class GraphicsMonitorEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 监控项id
     */
    @Column(name = "monitor_id")
    private String monitorId;

    /**
     * 图形的id
     */
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
