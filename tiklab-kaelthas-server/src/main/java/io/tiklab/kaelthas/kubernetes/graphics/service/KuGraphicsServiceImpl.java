package io.tiklab.kaelthas.kubernetes.graphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.kubernetes.graphics.dao.KuGraphicsDao;
import io.tiklab.kaelthas.kubernetes.graphics.model.KuGraphics;
import io.tiklab.kaelthas.kubernetes.graphics.entity.KuGraphicsEntity;
import io.tiklab.kaelthas.kubernetes.graphicsMonitor.service.KuGraphicsMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * k8s下的图形配置
 */
@Service
public class KuGraphicsServiceImpl implements KuGraphicsService {

    @Autowired
    private KuGraphicsDao kuGraphicsDao;

    @Autowired
    private KuGraphicsMonitorService kuGraphicsMonitorService;

    //分页查询图形
    @Override
    public Pagination<KuGraphics> findKuGraphicsPage(KuGraphics kuGraphics) {
        return kuGraphicsDao.findKuGraphicsPage(kuGraphics);
    }

    //创建图形
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

    //根据id删除图形
    @Override
    public void deleteKuGraphics(String id) {
        try {
            kuGraphicsMonitorService.deleteByCondition(id, null);
            kuGraphicsDao.deleteKuGraphics(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //修改图形
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

    //根据k8s监控的id查询图形信息
    @Override
    public List<KuGraphics> findKuGraphicsList(String kuId) {

        List<KuGraphicsEntity> kuGraphicsEntityList = kuGraphicsDao.findKuGraphicsList(kuId);
        return BeanMapper.mapList(kuGraphicsEntityList,KuGraphics.class);
    }

    //根据k8s监控的id删除图形信息
    @Override
    public void deleteByKuId(String id) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuGraphicsEntity.class)
                .eq("kuId", id)
                .get();
        kuGraphicsDao.deleteByKuId(deleteCondition);
    }
}
