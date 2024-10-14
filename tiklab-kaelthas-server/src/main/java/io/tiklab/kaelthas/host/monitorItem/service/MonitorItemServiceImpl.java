package io.tiklab.kaelthas.host.monitorItem.service;

import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.host.monitorItem.dao.MonitorItemDao;
import io.tiklab.kaelthas.host.monitorItem.entity.MonitorItemEntity;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Exporter
public class MonitorItemServiceImpl implements MonitorItemService {

    @Resource
    private JoinTemplate joinTemplate;

    @Resource
    private MonitorItemDao monitorItemDao;

    @Override
    public MonitorItem findOneMonitorItem(String itemId) {
        MonitorItem monitorItem = BeanMapper.map(monitorItemDao.findOneMonitorItem(itemId), MonitorItem.class);
        joinTemplate.joinQuery(monitorItem);
        return monitorItem;
    }

    @Override
    public List<MonitorItem> findMonitorItemList(List<String> idList) {

        List<MonitorItemEntity> monitorItemEntities = monitorItemDao.findMonitorItemList(idList);

        return BeanMapper.mapList(monitorItemEntities, MonitorItem.class);

    }

    @Override
    public List<String> findMonitorByCategories(String dataCategories) {
        QueryCondition queryCondition = QueryBuilders.createQuery(MonitorItemEntity.class)
                .eq("type",dataCategories).get();

        List<MonitorItemEntity> monitorItemEntities = monitorItemDao.findMonitorByCategories(queryCondition);
        return monitorItemEntities.stream().map(MonitorItemEntity::getId).toList();
    }

    @Override
    public List<MonitorItem> findMonitorItemByName(String name) {
        QueryCondition queryCondition = QueryBuilders.createQuery(MonitorItemEntity.class)
                .eq("type", name)
                .get();
        List<MonitorItemEntity> monitorEntityList = monitorItemDao.findMonitorItemByName(queryCondition);

        return BeanMapper.mapList(monitorEntityList, MonitorItem.class);
    }
}
