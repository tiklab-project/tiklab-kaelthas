package io.tiklab.kaelthas.db.dbTriggerMedium.service;

import io.tiklab.kaelthas.db.dbTrigger.model.DbTrigger;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.db.dbTriggerMedium.model.DbTriggerMedium;

import java.util.List;

@JoinProvider(model = DbTriggerMedium.class)
public interface DbTriggerMediumService {

    void createTriggerMedium(DbTrigger dbTrigger);

    void deleteByTriggerId(String id);

    void deleteByDbId(String dbId);
}
