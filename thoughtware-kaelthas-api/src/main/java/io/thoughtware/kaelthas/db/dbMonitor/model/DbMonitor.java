package io.thoughtware.kaelthas.db.dbMonitor.model;

import io.thoughtware.core.page.Page;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.kaelthas.db.dbItem.model.DbItem;

@Join
@Mapper
public class DbMonitor {


    private String id;

    /**
     * 数据源id
     */
    private String dbId;

    /**
     * 监控项名称
     */
    private String name;

    /**
     * 监控数据库名称
     */
    private String datName;

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
    private String dbItemId;

    @Mappings({
            @Mapping(source = "dbItem.id", target = "dbItemId")
    })
    @JoinQuery(key = "id")
    private DbItem dbItem;

    /**
     * 模板复制的时候使用,将本表的监控项复制一份出来
     */
    private String monitorId;

    /**
     * 监控项来源(1.系统指标,2.自定义SQL)
     */
    private Integer monitorType;

    private String expression;

    private String dbType;

    private String username;

    private String password;

    private String ip;

    private String port;

    private String monitorItemId;

    private Page pageParam = new Page();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatName() {
        return datName;
    }

    public void setDatName(String datName) {
        this.datName = datName;
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

    public String getDbItemId() {
        return dbItemId;
    }

    public void setDbItemId(String dbItemId) {
        this.dbItemId = dbItemId;
    }

    public DbItem getDbItem() {
        return dbItem;
    }

    public void setDbItem(DbItem dbItem) {
        this.dbItem = dbItem;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
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

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getMonitorItemId() {
        return monitorItemId;
    }

    public void setMonitorItemId(String monitorItemId) {
        this.monitorItemId = monitorItemId;
    }
}
