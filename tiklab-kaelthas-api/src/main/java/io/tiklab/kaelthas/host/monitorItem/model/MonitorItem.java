package io.tiklab.kaelthas.host.monitorItem.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class MonitorItem {


    //id
    private String id;

    //监控列表名称
    private String name;

    //监控类型
    private String type;

    //监控项小类
    private String dataType;

    //数据小类名称
    private String dataSubclass;

    //数据大类
    private String dataCategories;

    private Integer reportType;

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getDataCategories() {
        return dataCategories;
    }

    public void setDataCategories(String dataCategories) {
        this.dataCategories = dataCategories;
    }

    public String getDataSubclass() {
        return dataSubclass;
    }

    public void setDataSubclass(String dataSubclass) {
        this.dataSubclass = dataSubclass;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
