package io.thoughtware.kaelthas.kubernetes.kubernetesTriggerMedium.service;

import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.kaelthas.kubernetes.kubernetesTriggerMedium.dao.KuTriggerMediumDao;
import io.thoughtware.kaelthas.kubernetes.kubernetesTriggerMedium.entity.KuTriggerMediumEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuTriggerMediumServiceImpl implements KuTriggerMediumService{

    @Autowired
    private KuTriggerMediumDao kuTriggerMediumDao;

    @Override
    public void deleteByTriggerIds(List<String> stringList) {
        if (stringList.isEmpty()) {
            return;
        }
        Object[] array = stringList.toArray();
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuTriggerMediumEntity.class)
                .eq("triggerId", array)
                .get();
        kuTriggerMediumDao.deleteByTriggerIds(deleteCondition);
    }

    @Override
    public void createKuTriggerMedium(String triggerId, List<String> mediumType) {
        if (mediumType.isEmpty()) {
            return;
        }
        for (String string : mediumType) {
            KuTriggerMediumEntity entity = new KuTriggerMediumEntity();
            entity.setTriggerId(triggerId);
            entity.setMediumId(string);
            kuTriggerMediumDao.createKuTriggerMedium(entity);
        }
    }

    @Override
    public void deleteByTriggerId(String triggerId) {
        if (StringUtils.isBlank(triggerId)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuTriggerMediumEntity.class)
                .eq("triggerId", triggerId)
                .get();
        kuTriggerMediumDao.deleteByTriggerId(deleteCondition);
    }
}
