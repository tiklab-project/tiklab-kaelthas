package io.thoughtware.kaelthas.internet.internetGraphicsMonitor.model;


import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

@Join
@Mapper
public class InGraphicsMonitor {


    private String id;

    private String graphicsId;

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
