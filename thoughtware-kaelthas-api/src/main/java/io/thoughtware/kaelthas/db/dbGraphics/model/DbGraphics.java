package io.thoughtware.kaelthas.db.dbGraphics.model;

import io.thoughtware.core.page.Page;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

import java.util.ArrayList;
import java.util.List;

@Join
@Mapper
public class DbGraphics {

    private String id;

    private String name;

    private String describe;

    private String dbId;

    private Integer monitorSum;

    private List<String> monitorIds = new ArrayList<>();

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
