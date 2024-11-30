package io.tiklab.kaelthas.kubernetes.graphics.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.ArrayList;
import java.util.List;

/**
 * k8s中的图形配置
 */
@Mapper
@Join
public class KuGraphics {

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
     * 集群id
     */
    private String kuId;

    /**
     * 1.来源于主机中监控项创建2.来源于模板中监控项创建
     */
    private Integer source;

    private List<String> monitorIds = new ArrayList<>();

    /**
     * 监控项的数量
     */
    private Integer monitorNum;

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getKuId() {
        return kuId;
    }

    public void setKuId(String kuId) {
        this.kuId = kuId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public List<String> getMonitorIds() {
        return monitorIds;
    }

    public void setMonitorIds(List<String> monitorIds) {
        this.monitorIds = monitorIds;
    }

    public Integer getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(Integer monitorNum) {
        this.monitorNum = monitorNum;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
