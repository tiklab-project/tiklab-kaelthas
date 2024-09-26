package io.thoughtware.kaelthas.host.trigger.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.history.model.History;
import io.thoughtware.kaelthas.host.trigger.model.Trigger;

import java.util.List;

@JoinProvider(model = Trigger.class)
public interface TriggerService {
    Pagination<Trigger> findTrigger(Trigger trigger);

    @FindAll
    List<Trigger> findTriggerAll();

    @FindOne
    Trigger findTriggerOne(String triggerId);

    @FindList
    List<Trigger> findTriggerList(List<String> ids);

    String createTrigger(Trigger trigger);

    void deleteTriggerById(String id);

    void updateTrigger(Trigger trigger);

    void deleteByMonitor(String monitorId);

    void deleteByMonitorIds(String[] monitorIds);

    List<Trigger> findTriggerListById(String hostId);

    void insertAlarmForTrigger(List<History> entityList);

    List<Trigger> findLikeTrigger(String hostId, String expression,String triggerId);

    //查询触发器的数量
    Long findTriggerAllNum();

}
