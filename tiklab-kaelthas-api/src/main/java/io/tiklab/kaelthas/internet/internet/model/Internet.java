package io.tiklab.kaelthas.internet.internet.model;


import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
* 
* @TableName mtc_internet
*/

@Mapper
@Join
public class Internet {

    /**
    * id
    */
    private String id;

    /**
    * 名称
    */
    private String name;

    /**
    * ip
    */
    private String ip;

    /**
    * 1代表交换机,2代表路由器
    */
    private Integer type;

    /**
    * 1.开启,0.关闭
    */
    private Integer status;

    /**
    * 是否可用
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
    * 颜色
    */
    private Integer color;

    /**
     * 端口号
     */
    private Integer port;

    private Integer alarmNum;

    private String message;

    //分页参数
    private Page paramPage = new Page();

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
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

    public Page getParamPage() {
        return paramPage;
    }

    public void setParamPage(Page paramPage) {
        this.paramPage = paramPage;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
