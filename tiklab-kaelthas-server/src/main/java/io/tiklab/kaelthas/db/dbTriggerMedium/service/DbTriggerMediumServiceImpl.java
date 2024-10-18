package io.tiklab.kaelthas.db.dbTriggerMedium.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.kaelthas.db.dbTrigger.model.DbTrigger;
import io.tiklab.kaelthas.db.dbTriggerMedium.dao.DbTriggerMediumDao;
import io.tiklab.kaelthas.db.dbTriggerMedium.entity.DbTriggerMediumEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbTriggerMediumServiceImpl implements DbTriggerMediumService {

    @Autowired
    private DbTriggerMediumDao dbTriggerMediumDao;

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
