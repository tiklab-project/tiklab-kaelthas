package io.tiklab.kaelthas.db.dbDynamic.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;

public interface DbDynamicService {
    Pagination<DbDynamic> findDynamicPage(DbDynamic dbDynamic);

    void createDbDynamic(DbDynamic dbDynamic);
}
