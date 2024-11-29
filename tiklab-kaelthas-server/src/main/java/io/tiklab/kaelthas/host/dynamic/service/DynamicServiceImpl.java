package io.tiklab.kaelthas.host.dynamic.service;


import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.dynamic.dao.DynamicDao;
import io.tiklab.kaelthas.host.dynamic.entity.DynamicEntity;
import io.tiklab.kaelthas.host.dynamic.model.Dynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页上展示的动态信息,已经废弃
 */
@Service
@Exporter
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    DynamicDao dynamicDao;

    //查询动态list
    @Override
    public List<Dynamic> findList(List<String> idList) {
        List<DynamicEntity> dynamicEntityList = dynamicDao.findList(idList);
        return BeanMapper.mapList(dynamicEntityList, Dynamic.class);
    }

    //查询10条动态信息
    @Override
    public List<Dynamic> findDynamicList() {
        return dynamicDao.findDynamicList();
    }

    //创建动态信息
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

    //根据主机的id删除动态信息
    @Override
    public void deleteByHostId(String hostId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DynamicEntity.class)
                .eq("hostId", hostId)
                .get();

        dynamicDao.deleteByHostId(deleteCondition);
    }
}
