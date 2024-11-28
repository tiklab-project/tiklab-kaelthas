package io.tiklab.kaelthas.host.graphics.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 主机的图形表
 */
@Entity
@Table(name = "mtc_graphics")
public class GraphicsEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 图形名称(监控展示的时候会有显示
     */
    @Column(name = "name")
    private String name;

    /**
     * 图形描述
     */
    @Column(name = "describe")
    private String describe;

    /**
     * 宽度,废弃字段,暂时没有使用
     */
    @Column(name = "width")
    private String width;

    /**
     * 高度,废弃字段,暂时没有使用
     */
    @Column(name = "height")
    private String height;

    /**
     * 主机id
     */
    @Column(name = "host_id")
    private String hostId;

    /**
     * 来源(1.主机,2.模板),当前主机的图形只有在主机当中才能创建
     */
    @Column(name = "source")
    private Integer source;

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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}