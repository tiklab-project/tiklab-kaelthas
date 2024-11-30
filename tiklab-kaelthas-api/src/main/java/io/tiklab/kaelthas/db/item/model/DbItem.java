package io.tiklab.kaelthas.db.item.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 监控字典项
 */
@Join
@Mapper
public class DbItem {

    private String id;

    /**
     * 监控项表达式，部分字典项有参数，修改参数后采集不同的指标
     */
    private String expression;

    /**
     * 字典项的描述
     */
    private String describe;

    /**
     * 数据大小的类型，一样的数据类型的监控项才可以放到一张图当中展示
     */
    private Integer dataType;

    /**
     * 数据库类型（PostgreSQL，MYSQL）
     */
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
