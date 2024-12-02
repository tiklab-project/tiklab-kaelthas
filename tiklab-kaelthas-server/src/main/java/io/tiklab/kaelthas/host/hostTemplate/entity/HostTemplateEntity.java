package io.tiklab.kaelthas.host.hostTemplate.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 主机和模板的关联表(中间表)
 */
@Entity
@Table(name = "mtc_host_template")
public class HostTemplateEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    //模板id
    @Column(name = "template_id")
    private String templateId;

    //监控指标id
    @Column(name = "host_id")
    private String hostId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
