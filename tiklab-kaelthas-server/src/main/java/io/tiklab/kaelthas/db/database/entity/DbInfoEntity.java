package io.tiklab.kaelthas.db.database.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 监控数据库的信息存储表
 */
@Entity
@Table(name = "mtc_db_info")
public class DbInfoEntity{

    /**
     * id
     */
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 数据源名称
     */
    @Column(name = "name")
    private String name;

    /**
     * ip地址
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 用户名称
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 数据库类型
     */
    @Column(name = "db_type")
    private String dbType;

    /**
     * 数据库名称
     */
    @Column(name = "db_name")
    private String dbName;

    /**
     * 数据库端口
     */
    @Column(name = "db_port")
    private Integer dbPort;


    /**
     * 状态(1,开启,2,关闭)
     */
    @Column(name = "state")
    private Integer state;


    /**
     * 是否可用(1,可用,0,不可用)
     */
    @Column(name = "usability")
    private Integer usability;


    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;


    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private String updateTime;


    /**
     * 1.公共2,私密
     */
    @Column(name = "visibility")
    private Integer visibility;


    /**
     * 颜色类型
     */
    @Column(name = "color")
    private Integer color;


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
}
