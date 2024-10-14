package io.tiklab.kaelthas.host.hostGroup.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_host_group")
public class HostGroupEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    //主机组名称
    @Column(name = "name")
    private String name;

    //模主机组描述
    @Column(name="describe")
    private String describe;

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
}
