package io.thoughtware.kaelthas.host.triggerMedium.service;


import java.util.List;

public interface TriggerMediumService {
    void createTriggerMedium(String triggerId, List<String> mediumIds);

    void deleteByTriggerId(String id);

    List<String> findMediumIdByTriggerId(String triggerId);
}
