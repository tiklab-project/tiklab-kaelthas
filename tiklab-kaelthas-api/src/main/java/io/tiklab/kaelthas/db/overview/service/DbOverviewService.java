package io.tiklab.kaelthas.db.overview.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;

/**
 * 数据库监控中的概况
 */
public interface DbOverviewService {

    /**
     * 根据id查询数据源
     */
    DbInfo findDbInfoById(String id);

    /**
     * 数据库监控的动态信息
     */
    Pagination<DbDynamic> findDynamicPage(DbDynamic dbDynamic);
}
