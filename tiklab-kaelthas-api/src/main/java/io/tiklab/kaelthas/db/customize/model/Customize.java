package io.tiklab.kaelthas.db.customize.model;

import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 自定义SQL
 */
@Join
@Mapper
public class Customize {


    private String id;

    //监控数据库的id
    private String dbId;

    //条件表达式
    private String expression;

    //自定义SQL
    private String statementSql;

    //描述
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
