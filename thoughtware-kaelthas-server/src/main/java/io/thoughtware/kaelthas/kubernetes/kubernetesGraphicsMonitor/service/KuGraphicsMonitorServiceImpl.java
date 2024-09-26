package io.thoughtware.kaelthas.kubernetes.kubernetesGraphicsMonitor.service;

import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;
import io.thoughtware.kaelthas.kubernetes.kubernetesGraphicsMonitor.dao.KuGraphicsMonitorDao;
import io.thoughtware.kaelthas.kubernetes.kubernetesGraphicsMonitor.entity.KuGraphicsMonitorEntity;
import io.thoughtware.kaelthas.kubernetes.kubernetesGraphicsMonitor.model.KuGraphicsMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuGraphicsMonitorServiceImpl implements KuGraphicsMonitorService {

    @Autowired
    private KuGraphicsMonitorDao kuGraphicsMonitorDao;

    @Override
    public void createKuGraphicsMonitor(KuGraphics kuGraphics) {
        List<String> monitorIds = kuGraphics.getMonitorIds();
        if (monitorIds.isEmpty()) {
            return;
        }
        for (String monitorId : monitorIds) {
            KuGraphicsMonitorEntity entity = new KuGraphicsMonitorEntity();
            entity.setGraphicsId(kuGraphics.getId());
            entity.setMonitorId(monitorId);
            kuGraphicsMonitorDao.save(entity);
        }
    }

    @Override
    public void deleteByCondition(String graphicsId, String monitorId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuGraphicsMonitorEntity.class)
                .eq("graphicsId", graphicsId)
                .eq("monitorId", monitorId)
                .get();
        kuGraphicsMonitorDao.deleteByKuCondition(deleteCondition);
    }

    @Override
    public List<String> findMonitorIds(KuGraphicsMonitor kuGraphicsMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(KuGraphicsMonitorEntity.class)
                .eq("graphicsId", kuGraphicsMonitor.getGraphicsId())
                .get();
        List<KuGraphicsMonitorEntity> entityList = kuGraphicsMonitorDao.findMonitorIds(queryCondition);

        return entityList.stream().map(KuGraphicsMonitorEntity::getMonitorId).toList();
    }

    /**
     * 返回 monitorIds
     */
    @Override
    public List<KuGraphicsMonitor> findGraphicsMonitors(String id) {
        return kuGraphicsMonitorDao.findGraphicsMonitors(id);
    }

    @Override
    public void deleteByGraphicsIds(List<String> stringList) {
        if (stringList.isEmpty()) {
            return;
        }
        Object[] array = stringList.toArray();
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuGraphicsMonitorEntity.class)
                .in("graphicsId", array)
                .get();
        kuGraphicsMonitorDao.deleteByKuGraphicsIds(deleteCondition);
    }
}
