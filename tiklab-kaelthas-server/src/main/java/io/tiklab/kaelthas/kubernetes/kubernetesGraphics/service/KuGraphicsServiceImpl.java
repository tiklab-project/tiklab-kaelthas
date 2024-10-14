package io.tiklab.kaelthas.kubernetes.kubernetesGraphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.dao.KuGraphicsDao;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphicsEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.service.KuGraphicsMonitorService;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.entity.KubernetesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuGraphicsServiceImpl implements KuGraphicsService {

    @Autowired
    private KuGraphicsDao kuGraphicsDao;

    @Autowired
    private KuGraphicsMonitorService kuGraphicsMonitorService;

    @Override
    public Pagination<KuGraphics> findKuGraphicsPage(KuGraphics kuGraphics) {
        return kuGraphicsDao.findKuGraphicsPage(kuGraphics);
    }

    @Override
    public String createKuGraphics(KuGraphics kuGraphics) {
        try {
            //将选中的监控项添加到关联表当中
            KuGraphicsEntity entity = BeanMapper.map(kuGraphics, KuGraphicsEntity.class);
            String graphicsId = kuGraphicsDao.createKuGraphics(entity);
            kuGraphics.setId(graphicsId);
            kuGraphicsMonitorService.createKuGraphicsMonitor(kuGraphics);
            return graphicsId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteKuGraphics(String id) {
        try {
            kuGraphicsMonitorService.deleteByCondition(id, null);
            kuGraphicsDao.deleteKuGraphics(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateKuGraphics(KuGraphics kuGraphics) {
        try {
            KuGraphicsEntity kuGraphicsEntity = BeanMapper.map(kuGraphics, KuGraphicsEntity.class);
            if (kuGraphics.getMonitorIds().isEmpty()) {
                kuGraphicsDao.updateKuGraphics(kuGraphicsEntity);
            }else {
                kuGraphicsMonitorService.deleteByCondition(kuGraphics.getId(), null);
                kuGraphicsMonitorService.createKuGraphicsMonitor(kuGraphics);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<KuGraphics> findKuGraphicsList(String hostId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(KuGraphicsEntity.class)
                .eq("kuId", hostId)
                .get();
        List<KuGraphicsEntity> kuGraphicsEntityList = kuGraphicsDao.findKuGraphicsList(queryCondition);
        return BeanMapper.mapList(kuGraphicsEntityList,KuGraphics.class);
    }

    @Override
    public void deleteByKuId(String id) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KubernetesEntity.class)
                .eq("kuId", id)
                .get();
        kuGraphicsDao.deleteByKuId(deleteCondition);
    }
}
