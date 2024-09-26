package io.thoughtware.kaelthas.host.monitorItem.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.host.monitorItem.entity.MonitorItemEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MonitorItemDao {

    @Resource
    private JpaTemplate jpaTemplate;

    public MonitorItemEntity findOneMonitorItem(String itemId) {
        return jpaTemplate.findOne(MonitorItemEntity.class, itemId);

    }

    public List<MonitorItemEntity> findMonitorItemByName(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,MonitorItemEntity.class);
    }

    public List<MonitorItemEntity> findMonitorItemList(List<String> idList) {

        return jpaTemplate.findList(MonitorItemEntity.class, idList);

    }

    public List<MonitorItemEntity> findMonitorByCategories(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition,MonitorItemEntity.class);
    }
}
