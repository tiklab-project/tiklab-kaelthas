package io.thoughtware.kaelthas.kubernetes.kubernetesTriggerMedium.service;

import java.util.List;

public interface KuTriggerMediumService {
    void deleteByTriggerIds(List<String> stringList);

    void createKuTriggerMedium(String triggerId, List<String> mediumType);

    void deleteByTriggerId(String triggerId);
}
