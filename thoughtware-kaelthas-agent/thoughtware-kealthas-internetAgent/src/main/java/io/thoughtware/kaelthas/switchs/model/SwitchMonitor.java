package io.thoughtware.kaelthas.switchs.model;

import io.thoughtware.core.page.Page;
public class SwitchMonitor {

    /**
     * id
     */
    private String id;

    /**
     * 数据源id
     */
    private String internetId;

    /**
     * 监控项名称
     */
    private String name;

    /**
     * 数据保留时间
     */
    private Integer retentionTime;


    /**
     * 监控项状态(1. 已启用，2. 未启用）
     */
    private Integer status;

    /**
     * dbitem表的id
     */
    private String internetItemId;

    /**
     * 模板复制的时候使用,将本表的监控项复制一份出来
     */
    private String monitorId;

    /**
     * k8s中的namespace
     */
    private String nameSpace;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * pod名称
     */
    private String podName;

    /**
     * 节点名称
     */
    private String nodeName;

    private String ip;

    private Integer port;

    private String apiToken;

    private String expression;

    private Page pageParam = new Page();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternetId() {
        return internetId;
    }

    public void setInternetId(String internetId) {
        this.internetId = internetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(Integer retentionTime) {
        this.retentionTime = retentionTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInternetItemId() {
        return internetItemId;
    }

    public void setInternetItemId(String internetItemId) {
        this.internetItemId = internetItemId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
