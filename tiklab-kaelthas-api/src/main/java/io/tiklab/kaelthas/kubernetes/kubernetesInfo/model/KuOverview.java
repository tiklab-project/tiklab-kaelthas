package io.tiklab.kaelthas.kubernetes.kubernetesInfo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.core.page.Page;
import io.tiklab.kaelthas.kubernetes.item.model.KubernetesItem;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinField;

import java.sql.Timestamp;

@Join
@Mapper
public class KuOverview {

    /**
     * id
     */
    private String id;

    /**
     * kuId k8s表的id
     */
    private String kuId;

    @Mappings({
            @Mapping(source = "kubernetesItem.id",target = "kuItemId")
    })
    @JoinField(key = "id")
    private KubernetesItem kubernetesItem;



    /**
     * reportData 信息
     */
    private String reportData;

    /**
     * type 类型 1-22 1-18为数字，19-22为json
     */
    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private Timestamp createTime;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKuId() {
        return kuId;
    }

    public void setKuId(String kuId) {
        this.kuId = kuId;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    public KubernetesItem getKubernetesItem() {
        return kubernetesItem;
    }

    public KuOverview setKubernetesItem(KubernetesItem kubernetesItem) {
        this.kubernetesItem = kubernetesItem;
        return this;
    }
}
