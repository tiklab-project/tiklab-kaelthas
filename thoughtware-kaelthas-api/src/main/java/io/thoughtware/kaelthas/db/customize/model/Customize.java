package io.thoughtware.kaelthas.db.customize.model;

import io.thoughtware.core.page.Page;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

@Join
@Mapper
public class Customize {

    private String id;

    private String dbId;

    private String expression;

    private String statementSql;

    private String describe;

    private Page pageParam = new Page();


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

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }
}
