package io.tiklab.kaelthas.db.overview.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;
import io.tiklab.kaelthas.db.dbDynamic.service.DbDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据库监控中的概况
 */
@Service
public class DbOverviewServiceImpl implements DbOverviewService{

    @Autowired
    private DbInfoService dbInfoService;

    @Autowired
    private DbDynamicService dbDynamicService;

    /**
     * 根据id查询数据源
     */
    @Override
    public DbInfo findDbInfoById(String id) {
        return dbInfoService.findDbInfoById(id);
    }

    /**
     * 数据库监控的动态信息
     */
    @Override
    public Pagination<DbDynamic> findDynamicPage(DbDynamic dbDynamic) {
        return dbDynamicService.findDynamicPage(dbDynamic);
    }
}
