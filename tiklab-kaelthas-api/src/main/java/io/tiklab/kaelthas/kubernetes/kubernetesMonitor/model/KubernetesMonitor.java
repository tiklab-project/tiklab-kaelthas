package io.tiklab.kaelthas.kubernetes.kubernetesMonitor.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;
import io.tiklab.kaelthas.kubernetes.kubernetesItem.model.KubernetesItem;

@Join
@Mapper
public class KubernetesMonitor {

    /**
     * id
     */
    private String id;

    /**
     * 数据源id
     */
    private String kuId;

    /**
     * 监控项名称
     */
    private String name;

    /**
     * 数据保留时间
     */
    private Integer retentionTime;


    @Mappings({
            @Mapping(source = "kubernetesItem.id", target = "kuItemId")
    })
    @JoinQuery(key = "id")
    private KubernetesItem kubernetesItem;

    /**
     * 监控项状态(1. 已启用，2. 未启用）
     */
    private Integer status;

    /**
     * dbitem表的id
     */
    private String kuItemId;

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

    public String getKuId() {
        return kuId;
    }

    public void setKuId(String kuId) {
        this.kuId = kuId;
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

    public KubernetesItem getKubernetesItem() {
        return kubernetesItem;
    }

    public void setKubernetesItem(KubernetesItem kubernetesItem) {
        this.kubernetesItem = kubernetesItem;
    }

    public String getKuItemId() {
        return kuItemId;
    }

    public void setKuItemId(String kuItemId) {
        this.kuItemId = kuItemId;
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
