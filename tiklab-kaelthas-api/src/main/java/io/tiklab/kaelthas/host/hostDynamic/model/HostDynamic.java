package io.tiklab.kaelthas.host.hostDynamic.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 主机下的动态信息
 */
@Join
@Mapper
public class HostDynamic {


    private String id;

    /**
     * 动态名称
     */
    private String name;

    /**
     * 动态时间
     */
    private String time;

    /**
     * 主机id
     */
    private String hostId;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
