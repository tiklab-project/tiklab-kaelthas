package io.tiklab.kaelthas.host.trigger.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.host.trigger.model.Trigger;

import java.util.List;

/**
 * 主机下的触发器
 */
@JoinProvider(model = Trigger.class)
public interface TriggerService {

    /**
     * 分页查询主机下的触发器
     */
    Pagination<Trigger> findTrigger(Trigger trigger);

    /**
     * 查询所有的触发器
     */
    @FindAll
    List<Trigger> findTriggerAll();

    /**
     * 查询单个触发器
     */
    @FindOne
    Trigger findTriggerOne(String triggerId);

    /**
     * 根据ids查询触发器list
     */
    @FindList
    List<Trigger> findTriggerList(List<String> ids);

    /**
     * 添加触发器
     */
    String createTrigger(Trigger trigger);

    /**
     * 删除触发器
     */
    void deleteTriggerById(String id);

    /**
     * 修改触发器
     */
    void updateTrigger(Trigger trigger);

    /**
     * 根据主机id删除触发器
     */
    void deleteByHostId(String hostId);

    /**
     * 根据主机id查询触发器list
     */
    List<Trigger> findTriggerListById(String hostId);

    /**
     * 根据主机的id和表达式进行查询
     */
    List<Trigger> findLikeTrigger(String hostId, String expression);

    //查询触发器的数量
    Long findTriggerAllNum();

}
