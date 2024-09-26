package io.thoughtware.kaelthas.host.graphics.model;

import io.thoughtware.core.page.Page;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

import java.util.List;

@Join
@Mapper
public class Graphics {

    private String id;

    private String name;

    private String monitorId;

    private List<String> monitorIds;

    private String describe;

    private String width;

    private String height;

    private String hostId;

    private Integer source;

    private Integer monitorSum;

    private Page pageParam = new Page();

    public List<String> getMonitorIds() {
        return monitorIds;
    }

    public void setMonitorIds(List<String> monitorIds) {
        this.monitorIds = monitorIds;
    }

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

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Integer getMonitorSum() {
        return monitorSum;
    }

    public void setMonitorSum(Integer monitorSum) {
        this.monitorSum = monitorSum;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
