package io.tiklab.kaelthas.db.dbTriggerMedium.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.kaelthas.db.dbTrigger.model.DbTrigger;
import io.tiklab.kaelthas.db.dbTriggerMedium.dao.DbTriggerMediumDao;
import io.tiklab.kaelthas.db.dbTriggerMedium.entity.DbTriggerMediumEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 触发器和消息渠道的关联业务(关联表)
 */
@Service
public class DbTriggerMediumServiceImpl implements DbTriggerMediumService {

    @Autowired
    private DbTriggerMediumDao dbTriggerMediumDao;

    /**
     * 创建触发器和消息渠道的关联信息
     */
    @Override
    public void createTriggerMedium(DbTrigger dbTrigger) {
        if (dbTrigger.getMediumType().isEmpty()) {
            return;
        }
        for (String mediumId : dbTrigger.getMediumType()) {
            DbTriggerMediumEntity medium = new DbTriggerMediumEntity();
            medium.setTriggerId(dbTrigger.getId());
            medium.setDbId(dbTrigger.getDbId());
            medium.setMediumId(mediumId);
            dbTriggerMediumDao.createTriggerMedium(medium);
        }
    }

    /**
     * 根据触发器的id删除信息
     */
    @Override
    public void deleteByTriggerId(String id) {
        if (StringUtils.isBlank(id)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DbTriggerMediumEntity.class)
                .eq("triggerId", id)
                .get();
        dbTriggerMediumDao.deleteByTriggerId(deleteCondition);
    }

    /**
     * 根据监控数据库的id删除关联表
     */
    @Override
    public void deleteByDbId(String dbId) {
        if (StringUtils.isBlank(dbId)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders
                .createDelete(DbTriggerMediumEntity.class)
                .eq("dbId", dbId)
                .get();
        dbTriggerMediumDao.deleteByDbId(deleteCondition);
    }
}
