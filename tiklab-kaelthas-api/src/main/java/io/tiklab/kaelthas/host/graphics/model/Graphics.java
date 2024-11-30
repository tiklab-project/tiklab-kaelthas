package io.tiklab.kaelthas.host.graphics.model;

import io.tiklab.core.page.Page;
import io.tiklab.dal.jpa.annotation.Column;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;

/**
 * 图形配置
 */
@Join
@Mapper
public class Graphics {

    private String id;

    /**
     * 图形名称(监控展示的时候会有显示
     */
    private String name;

    /**
     * 图形描述
     */
    private String describe;

    /**
     * 宽度,废弃字段,暂时没有使用
     */
    private String width;

    /**
     * 高度,废弃字段,暂时没有使用
     */
    private String height;

    /**
     * 主机id
     */
    private String hostId;

    /**
     * 来源(1.主机,2.模板),当前主机的图形只有在主机当中才能创建
     */
    private Integer source;

    /**
     * 监控项id
     */
    private String monitorId;

    /**
     * 监控项的ids
     */
    private List<String> monitorIds;

    /**
     * 监控项数量
     */
    private Integer monitorSum;

    /**
     * 分页信息
     */
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
