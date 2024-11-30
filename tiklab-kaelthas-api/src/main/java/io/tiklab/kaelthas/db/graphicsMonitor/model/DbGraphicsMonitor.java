package io.tiklab.kaelthas.db.graphicsMonitor.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 图形和监控项关联表
 */
@Join
@Mapper
public class DbGraphicsMonitor {


    private String id;

    /**
     * 图形id
     */
    private String graphicsId;

    /**
     * 监控项id
     */
    private String monitorId;

    /**
     * 监控数据库id
     */
    private String dbId;

    /**
     * 图形名称
     */
    private String graphicsName;

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

    public String getGraphicsName() {
        return graphicsName;
    }

    public void setGraphicsName(String graphicsName) {
        this.graphicsName = graphicsName;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
}
