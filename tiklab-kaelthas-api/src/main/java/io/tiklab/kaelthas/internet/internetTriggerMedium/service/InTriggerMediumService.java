package io.tiklab.kaelthas.internet.internetTriggerMedium.service;

import java.util.List;

public interface InTriggerMediumService {

    void createTriggerMedium(String string, List<String> mediumType);

    void deleteByTrigger(String id);

    //根据触发器ids删除
    void deleteByTriggerIds(List<String> triggerIds);
}
