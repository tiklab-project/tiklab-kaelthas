package io.thoughtware.kaelthas.internet.internetTrigger.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.internet.internetTrigger.dao.InTriggerDao;
import io.thoughtware.kaelthas.internet.internetTrigger.entity.InTriggerEntity;
import io.thoughtware.kaelthas.internet.internetTrigger.model.InTrigger;
import io.thoughtware.kaelthas.internet.internetTriggerMedium.service.InTriggerMediumService;
import io.thoughtware.toolkit.beans.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InTriggerServiceImpl implements InTriggerService {

    @Autowired
    private InTriggerDao inTriggerDao;

    @Autowired
    private InTriggerMediumService inTriggerMediumService;

    @Override
    public Pagination<InTrigger> findTriggerPage(InTrigger inTrigger) {
        return inTriggerDao.findTriggerPage(inTrigger);
    }

    @Override
    public String createInTrigger(InTrigger inTrigger) {
        try {
            //创建触发器的时候,将创建的消息通知方案添加到与消息通知方案的关联表中
            InTriggerEntity entity = BeanMapper.map(inTrigger, InTriggerEntity.class);
            String string = inTriggerDao.createInTrigger(entity);

            //将传递过来的消息方案添加到触发器与消息方案中间表当中
            if (!inTrigger.getMediumIds().isEmpty()) {
                inTriggerMediumService.createTriggerMedium(string, inTrigger.getMediumIds());
            }
            return string;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTrigger(String id) {
        try {
            //根据触发器id删除触发器
            inTriggerDao.deleteTrigger(id);
            //根据触发器id删除触发器和消息方案的中间表
            inTriggerMediumService.deleteByTrigger(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTrigger(InTrigger inTrigger) {
        //如果没有修改消息通知方案
        if (inTrigger.getMediumIds().isEmpty()) {
            InTriggerEntity entity = BeanMapper.map(inTrigger, InTriggerEntity.class);
            inTriggerDao.updateTrigger(entity);
            //如果修改了消息通知方案
        } else {
            try {
                //先删除中间表的数据,然后再将数据插入
                inTriggerMediumService.deleteByTrigger(inTrigger.getId());
                inTriggerMediumService.createTriggerMedium(inTrigger.getId(),inTrigger.getMediumIds());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteByInId(String internetId) {
        if (StringUtils.isBlank(internetId)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(InTriggerEntity.class)
                .eq("internetId", internetId)
                .get();
        inTriggerDao.deleteByInId(deleteCondition);
    }

    @Override
    public List<String> findTriggerByInId(String internetId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InTriggerEntity.class)
                .eq("internetId", internetId)
                .get();

        List<InTriggerEntity> entityList = inTriggerDao.findTriggerByInId(queryCondition);

        return entityList.stream().map(InTriggerEntity::getId).toList();
    }
}
