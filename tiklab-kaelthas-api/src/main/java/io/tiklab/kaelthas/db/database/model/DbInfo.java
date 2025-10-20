package io.tiklab.kaelthas.db.database.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 数据库监控信息
 */
@Join
@Mapper
public class DbInfo {

    /**
     * id
     */
    private String id;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 驱动名称
     */
    private String dbName;

    /**
     * 连接字符串
     */
    private Integer dbPort;


    /**
     * 状态(1,开启,2,关闭)
     */
    private Integer state;


    /**
     * 是否可用(1,可用,0,不可用)
     */
    private Integer usability;


    /**
     * 创建时间
     */
    private String createTime;


    /**
     * 修改时间
     */
    private String updateTime;


    /**
     * 1.公共2,私密
     */
    private Integer visibility;


    /**
     * 颜色类型
     */
    private Integer color;

    private Integer monitorNum;

    private Integer triggerNum;

    private Integer alarmNum;

    //图形数量
    private Integer graphicsNum;

    private String message;

    private Page pageParam = new Page();

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUsability() {
        return usability;
    }

    public void setUsability(Integer usability) {
        this.usability = usability;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(Integer monitorNum) {
        this.monitorNum = monitorNum;
    }

    public Integer getTriggerNum() {
        return triggerNum;
    }

    public void setTriggerNum(Integer triggerNum) {
        this.triggerNum = triggerNum;
    }

    public Integer getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(Integer alarmNum) {
        this.alarmNum = alarmNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public Integer getGraphicsNum() {
        return graphicsNum;
    }

    public void setGraphicsNum(Integer graphicsNum) {
        this.graphicsNum = graphicsNum;
    }
}
