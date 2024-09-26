package io.thoughtware.kaelthas.host.monitorItem.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_item")
public class MonitorItemEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "data_categories")
    private String type;

    @Column(name = "data_type")
    private String dataType;

    //数据小类名称
    @Column(name = "data_subclass")
    private String dataSubclass;

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
