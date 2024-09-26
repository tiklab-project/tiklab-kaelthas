package io.thoughtware.kaelthas.host.dynamic.service;


import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.host.dynamic.dao.DynamicDao;
import io.thoughtware.kaelthas.host.dynamic.entity.DynamicEntity;
import io.thoughtware.kaelthas.host.dynamic.model.Dynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    DynamicDao dynamicDao;

    @Override
    public List<Dynamic> findList(List<String> idList) {
        List<DynamicEntity> dynamicEntityList = dynamicDao.findList(idList);
        return BeanMapper.mapList(dynamicEntityList, Dynamic.class);
    }

    @Override
    public List<Dynamic> findDynamicList() {
        return dynamicDao.findDynamicList();
    }

    @Override
    public void createDynamic(Dynamic dynamic) {
        //插入之前先查询表的数据是否大于9,如果是大于9的话删除后再添加
        List<Dynamic> dynamicList = this.findDynamicList();
        if (dynamicList.size() > 9) {
            Object[] array = dynamicList.subList(9, dynamicList.size()).stream().map(Dynamic::getId).toArray();
            DeleteCondition deleteCondition = DeleteBuilders.createDelete(DynamicEntity.class)
                    .in("id", array)
                    .get();
            try {
                dynamicDao.deleteByIds(deleteCondition);
                DynamicEntity dynamicEntity = BeanMapper.map(dynamic, DynamicEntity.class);
                dynamicDao.createDynamic(dynamicEntity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            DynamicEntity dynamicEntity = BeanMapper.map(dynamic, DynamicEntity.class);
            dynamicDao.createDynamic(dynamicEntity);
        }
    }

    @Override
    public void deleteByHostId(String hostId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DynamicEntity.class)
                .eq("hostId", hostId)
                .get();

        dynamicDao.deleteByHostId(deleteCondition);
    }
}
