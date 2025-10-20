package io.tiklab.kaelthas.host.graphicsMonitor.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 图形和监控项的关联表
 */
@Join
@Mapper
public class GraphicsMonitor {

    private String id;

    /**
     * 监控项id
     */
    private String monitorId;

    /**
     * 来源(1.主机,2.模板)暂时没有使用
     */
    private Integer source;

    /**
     * 图形的id
     */
    private String graphicsId;

    /**
     * 监控项名称
     */
    private String monitorName;

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

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getSource() {
        return source;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public void setSource(Integer source) {
        this.source = source;
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
}
