package io.thoughtware.kaelthas.db.dbDynamic.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.db.dbDynamic.model.DbDynamic;

public interface DbDynamicService {
    Pagination<DbDynamic> findDynamicPage(DbDynamic dbDynamic);

    void createDbDynamic(DbDynamic dbDynamic);
}
