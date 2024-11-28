package io.tiklab.kaelthas.db.dbItem.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 数据库监控项的字典表，创建监控项的时候选择字典项
 */
@Entity
@Table(name = "mtc_db_item")
public class DbItemEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    /**
     * 监控项表达式，部分字典项有参数，修改参数后采集不同的指标
     */
    @Column(name = "expression")
    private String expression;

    /**
     * 字典项的描述
     */
    @Column(name = "describe")
    private String describe;

    /**
     * 数据大小的类型，一样的数据类型的监控项才可以放到一张图当中展示
     */
    @Column(name = "data_type")
    private Integer dataType;

    /**
     * 数据库类型（PostgreSQL，MYSQL）
     */
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
