package io.tiklab.kaelthas.host.monitorItem.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 监控项的字典项
 */
@Entity
@Table(name = "mtc_item")
public class MonitorItemEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 字典项的表达式
     */
    @Column(name = "name")
    private String name;

    /**
     * 数据大类(io,host,memory,cpu)
     */
    @Column(name = "data_categories")
    private String type;

    /**
     * 数据小类,具体的指标
     */
    @Column(name = "data_type")
    private String dataType;

    /**
     * 具体的指标名称
     */
    @Column(name = "data_subclass")
    private String dataSubclass;

    /**
     * 收集的数据类型(1.百分比,2.json,3.G,4.数值)
     */
    @Column(name = "report_type")
    private Integer reportType;

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
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
