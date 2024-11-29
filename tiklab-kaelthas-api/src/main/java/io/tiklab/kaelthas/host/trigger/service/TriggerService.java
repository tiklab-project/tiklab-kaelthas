package io.tiklab.kaelthas.host.trigger.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.host.trigger.model.Trigger;

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

    void deleteByHostId(String hostId);

    List<Trigger> findTriggerListById(String hostId);


    List<Trigger> findLikeTrigger(String hostId, String expression);

    //查询触发器的数量
    Long findTriggerAllNum();

}
