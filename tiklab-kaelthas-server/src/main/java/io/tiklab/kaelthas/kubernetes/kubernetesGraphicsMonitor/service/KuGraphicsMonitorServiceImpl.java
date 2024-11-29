package io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.dao.KuGraphicsMonitorDao;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.entity.KuGraphicsMonitorEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.model.KuGraphicsMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图形和监控项的关联表
 */
@Service
public class KuGraphicsMonitorServiceImpl implements KuGraphicsMonitorService {

    @Autowired
    private KuGraphicsMonitorDao kuGraphicsMonitorDao;

    //创建关联表
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

    //根据图形id和监控项id删除关联表
    @Override
    public void deleteByCondition(String graphicsId, String monitorId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuGraphicsMonitorEntity.class)
                .eq("graphicsId", graphicsId)
                .eq("monitorId", monitorId)
                .get();
        kuGraphicsMonitorDao.deleteByKuCondition(deleteCondition);
    }

    //根据图形id查询出监控项ids
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

    //根据图形的ids删除关联表
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
