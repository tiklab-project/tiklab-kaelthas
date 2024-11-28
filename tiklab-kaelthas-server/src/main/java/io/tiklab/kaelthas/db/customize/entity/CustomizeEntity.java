package io.tiklab.kaelthas.db.customize.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 自定义SQL存储表（属于自定义功能，暂时没有开发）
 */
@Entity
@Table(name = "mtc_customize_sql")
public class CustomizeEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    //监控数据库的id
    @Column(name = "db_id")
    private String dbId;

    //条件表达式
    @Column(name = "expression")
    private String expression;

    //自定义SQL
    @Column(name = "statement_sql")
    private String statementSql;

    //描述
    @Column(name = "describe")
    private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getStatementSql() {
        return statementSql;
    }

    public void setStatementSql(String statementSql) {
        this.statementSql = statementSql;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
