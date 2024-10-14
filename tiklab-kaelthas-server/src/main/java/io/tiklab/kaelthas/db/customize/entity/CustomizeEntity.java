package io.tiklab.kaelthas.db.customize.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name = "mtc_customize_sql")
public class CustomizeEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "db_id")
    private String dbId;

    @Column(name = "expression")
    private String expression;

    @Column(name = "statement_sql")
    private String statementSql;

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
