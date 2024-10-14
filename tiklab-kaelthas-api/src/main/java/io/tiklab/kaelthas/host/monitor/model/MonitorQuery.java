package io.tiklab.kaelthas.host.monitor.model;

import io.tiklab.core.page.Page;

public class MonitorQuery {

    //主机id
    private String hostId;

    //监控项类型(1，主机监控   2，模板监控)
    private Integer monitorSource;

    //模板id
    private String TemplateId;

    //监控项名称
    private String name;

    //分页
    private Page pageParam = new Page();

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Integer getMonitorSource() {
        return monitorSource;
    }

    public void setMonitorSource(Integer monitorSource) {
        this.monitorSource = monitorSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public String getTemplateId() {
        return TemplateId;
    }

    public void setTemplateId(String templateId) {
        TemplateId = templateId;
    }
}
