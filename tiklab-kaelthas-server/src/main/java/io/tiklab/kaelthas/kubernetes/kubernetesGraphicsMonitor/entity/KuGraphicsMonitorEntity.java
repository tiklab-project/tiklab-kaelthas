package io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_ku_graphics_monitor")
public class KuGraphicsMonitorEntity {

    /**
     *  id
     */
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
     * 图表id
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
