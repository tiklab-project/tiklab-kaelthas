package io.tiklab.kaelthas.host.host.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;
import io.tiklab.kaelthas.host.graphics.model.Graphics;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.kaelthas.host.template.model.Template;

import java.util.List;

/**
 * 主机表
 */
@Join
@Mapper
public class Host {

    private String id;

    //主机名称
    private String name;

    //主机ip
    private String ip;

    //模板id
    private String templateId;

    //主机组信息
    @Mappings({
            @Mapping(source = "hostGroup.id", target = "hostGroupId")
    })
    @JoinQuery(key = "id")
    private HostGroup hostGroup;

    //模板信息
    private List<Template> templates;


    //图形信息
    private List<Graphics> graphics;

    //监控项数量
    private Integer monitorNum;

    //触发器数量
    private Integer triggerNum;

    //告警数量
    private Integer alarmNum;

    //主机状态 1.已启用 2.未启用
    private Integer state;

    //主机可用性 1.可用 2.不可用 3.未知
    private Integer usability;

    //主机描述
    private String describe;

    //主机创建时间
    private String createTime;

    //主机创建时间
    private String updateTime;

    //监控大类
    private String dataCategories;

    //可见性
    private Integer visibility;

    //颜色
    private Integer color;

    //消息
    private String message;

    //数据的分类
    private String dataCate;

    //告警等级
    private Integer severityLevel;

    //分页参数
    private Page pageParam = new Page();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDataCate() {
        return dataCate;
    }

    public void setDataCate(String dataCate) {
        this.dataCate = dataCate;
    }

    public String getDataCategories() {
        return dataCategories;
    }

    public void setDataCategories(String dataCategories) {
        this.dataCategories = dataCategories;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
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


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public HostGroup getHostGroup() {
        return hostGroup;
    }

    public void setHostGroup(HostGroup hostGroup) {
        this.hostGroup = hostGroup;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }


    public List<Graphics> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<Graphics> graphics) {
        this.graphics = graphics;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    public Integer getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(Integer alarmNum) {
        this.alarmNum = alarmNum;
    }

    public Integer getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }
}
