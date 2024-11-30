package io.tiklab.kaelthas.db.dbDynamic.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;

/**
 * 数据库监控中的动态
 */
public interface DbDynamicService {

    /**
     * 数据库监控的动态信息
     */
    Pagination<DbDynamic> findDynamicPage(DbDynamic dbDynamic);

    /**
     * 数据库监控中创建动态信息
     */
    void createDbDynamic(DbDynamic dbDynamic);
}
