package io.thoughtware.kaelthas.db.dbTriggerMedium.service;

import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.db.dbTriggerMedium.model.DbTriggerMedium;

import java.util.List;

@JoinProvider(model = DbTriggerMedium.class)
public interface DbTriggerMediumService {

    void createTriggerMedium(String triggerId, List<String> mediumIds);

    void deleteByTriggerId(String id);
}
