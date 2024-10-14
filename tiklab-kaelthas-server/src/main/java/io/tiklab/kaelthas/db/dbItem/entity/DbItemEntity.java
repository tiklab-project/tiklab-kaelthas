package io.tiklab.kaelthas.db.dbItem.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_db_item")
public class DbItemEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "expression")
    private String expression;

    @Column(name = "describe")
    private String describe;

    @Column(name = "data_type")
    private Integer dataType;

    @Column(name = "db_type")
    private String dbType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
