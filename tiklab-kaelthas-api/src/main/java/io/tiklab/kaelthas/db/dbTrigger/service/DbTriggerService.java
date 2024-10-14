package io.tiklab.kaelthas.db.dbTrigger.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.db.dbTrigger.model.DbTrigger;
import io.tiklab.kaelthas.history.model.History;

import java.util.List;

@JoinProvider(model = DbTrigger.class)
public interface DbTriggerService {

    Pagination<DbTrigger> findDbTriggerPage(DbTrigger dbTrigger);

    String createDbTrigger(DbTrigger dbTrigger);

    void deleteDbTrigger(String id);

    void updateDbTrigger(DbTrigger dbTrigger);

    List<DbTrigger> findListByDbId(String hostId);

    List<DbTrigger> findLikeTrigger(String hostId, String expression);
}
