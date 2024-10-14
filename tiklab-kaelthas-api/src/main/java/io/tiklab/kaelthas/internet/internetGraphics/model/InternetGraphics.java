package io.tiklab.kaelthas.internet.internetGraphics.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.LinkedList;
import java.util.List;

@Join
@Mapper
public class InternetGraphics {

    /**
     * id
     */
    private String id;

    /**
     * 图形名称
     */
    private String name;

    /**
     * 描述
     */
    private String describe;

    /**
     * 网络id
     */
    private String internetId;

    /**
     * 1.来源于主机中监控项创建2.来源于模板中监控项创建
     */
    private Integer source;

    private Integer monitorNum;

    private List<String> monitorIds = new LinkedList<>();

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getInternetId() {
        return internetId;
    }

    public void setInternetId(String internetId) {
        this.internetId = internetId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(Integer monitorNum) {
        this.monitorNum = monitorNum;
    }

    public List<String> getMonitorIds() {
        return monitorIds;
    }

    public void setMonitorIds(List<String> monitorIds) {
        this.monitorIds = monitorIds;
    }

    public Page getParamPage() {
        return paramPage;
    }

    public void setParamPage(Page paramPage) {
        this.paramPage = paramPage;
    }
}
