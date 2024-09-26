package io.thoughtware.kaelthas.db.dbTrigger.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.db.dbTrigger.model.DbTrigger;
import io.thoughtware.kaelthas.history.model.History;

import java.util.List;

@JoinProvider(model = DbTrigger.class)
public interface DbTriggerService {

    Pagination<DbTrigger> findDbTriggerPage(DbTrigger dbTrigger);

    String createDbTrigger(DbTrigger dbTrigger);

    void deleteDbTrigger(String id);

    void updateDbTrigger(DbTrigger dbTrigger);

    List<DbTrigger> findListByDbId(String hostId);

    void insertAlarmForDbTrigger(List<History> histories);

    List<DbTrigger> findLikeTrigger(String hostId, String expression);
}
