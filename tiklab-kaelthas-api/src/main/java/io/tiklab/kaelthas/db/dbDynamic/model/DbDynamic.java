package io.tiklab.kaelthas.db.dbDynamic.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 监控数据库的动态信息
 */
@Mapper
@Join
public class DbDynamic {

    private String id;

    /**
     * 检控数据库的id
     */
    private String dbId;

    /**
     * 动态信息
     */
    private String name;

    /**
     * 动态时间
     */
    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
