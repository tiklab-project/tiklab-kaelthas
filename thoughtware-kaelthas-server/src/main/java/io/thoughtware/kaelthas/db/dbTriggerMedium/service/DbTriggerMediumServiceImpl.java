package io.thoughtware.kaelthas.db.dbTriggerMedium.service;

import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.kaelthas.db.dbTriggerMedium.dao.DbTriggerMediumDao;
import io.thoughtware.kaelthas.db.dbTriggerMedium.entity.DbTriggerMediumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbTriggerMediumServiceImpl implements DbTriggerMediumService{

    @Autowired
    private DbTriggerMediumDao dbTriggerMediumDao;

    @Override
    public void createTriggerMedium(String triggerId, List<String> mediumIds) {
        for (String mediumId : mediumIds) {
            DbTriggerMediumEntity medium = new DbTriggerMediumEntity();
            medium.setTriggerId(triggerId);
            medium.setMediumId(mediumId);
            dbTriggerMediumDao.createTriggerMedium(medium);
        }
    }

    @Override
    public void deleteByTriggerId(String id) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DbTriggerMediumEntity.class)
                .eq("triggerId", id)
                .get();
        dbTriggerMediumDao.deleteByTriggerId(deleteCondition);
    }
}
