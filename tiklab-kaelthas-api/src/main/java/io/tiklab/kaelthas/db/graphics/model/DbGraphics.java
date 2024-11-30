package io.tiklab.kaelthas.db.graphics.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.ArrayList;
import java.util.List;

/**
 * 图形配置
 */
@Join
@Mapper
public class DbGraphics {


    private String id;

    /**
     * 监控数据库下图形的名称
     */
    private String name;

    /**
     * 图形描述
     */
    private String describe;

    /**
     * 监控数据库id
     */
    private String dbId;

    /**
     * 监控项数量
     */
    private Integer monitorSum;

    /**
     * 监控项的ids,创建图形和修改图形的时候使用
     */
    private List<String> monitorIds = new ArrayList<>();

    /**
     * 分页信息
     */
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

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public Integer getMonitorSum() {
        return monitorSum;
    }

    public void setMonitorSum(Integer monitorSum) {
        this.monitorSum = monitorSum;
    }

    public List<String> getMonitorIds() {
        return monitorIds;
    }

    public void setMonitorIds(List<String> monitorIds) {
        this.monitorIds = monitorIds;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
