package io.tiklab.kaelthas.kubernetes.graphicsMonitor.model;


import io.tiklab.dal.jpa.annotation.Entity;
import io.tiklab.toolkit.beans.annotation.Mapper;

/**
 * 图形和监控项的中间表
 */
@Mapper
@Entity
public class KuGraphicsMonitor {

    /**
     *  id
     */
    private String id;

    /**
     * 监控项id
     */
    private String monitorId;

    /**
     * 图表id
     */
    private String graphicsId;

    /**
     * 图形名称
     */
    private String graphicsName;

    /**
     * 监控项名字
     */
    private String monitorName;


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

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
}
