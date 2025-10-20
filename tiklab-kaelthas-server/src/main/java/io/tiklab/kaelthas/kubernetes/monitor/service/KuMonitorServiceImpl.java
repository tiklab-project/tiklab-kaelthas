package io.tiklab.kaelthas.kubernetes.monitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.kubernetes.graphicsMonitor.service.KuGraphicsMonitorService;
import io.tiklab.kaelthas.kubernetes.item.model.KubernetesItem;
import io.tiklab.kaelthas.kubernetes.item.service.KubernetesItemService;
import io.tiklab.kaelthas.kubernetes.monitor.model.KuMonitorQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.kubernetes.monitor.dao.KuMonitorDao;
import io.tiklab.kaelthas.kubernetes.monitor.entity.KuMonitorEntity;
import io.tiklab.kaelthas.kubernetes.monitor.model.KuMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * k8s监控中的监控项
 */
@Service
public class KuMonitorServiceImpl implements KuMonitorService{

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private KuMonitorDao kuMonitorDao;

    @Autowired
    private KuGraphicsMonitorService kuGraphicsMonitorService;

    @Autowired
    private KubernetesItemService kubernetesItemService;


    @Override
    public KuMonitor findOne(String id) {
        KuMonitorEntity kuMonitorEntity = kuMonitorDao.findKuMonitor(id);
        KuMonitor kuMonitor = BeanMapper.map(kuMonitorEntity, KuMonitor.class);

        return kuMonitor;
    }

    //根据名称和id进行分页查询
    @Override
    public Pagination<KuMonitor> findKuMonitorPage(KuMonitor kuMonitor) {

        Pagination<KuMonitorEntity> pagination = kuMonitorDao.findKuMonitorPage(kuMonitor);

        List<KuMonitor> kuMonitorList = BeanMapper.mapList(pagination.getDataList(), KuMonitor.class);

        joinTemplate.joinQuery(kuMonitorList);

        return PaginationBuilder.build(pagination, kuMonitorList);
    }

    //创建k8s下的监控项
    @Override
    public String createKuMonitor(KuMonitor kuMonitor) {
        KuMonitorEntity entity = BeanMapper.map(kuMonitor, KuMonitorEntity.class);
        return kuMonitorDao.createKuMonitor(entity);
    }

    //根据监控项id删除监控项
    @Override
    public void deleteKuMonitor(String id) {
        try {
            //删除监控项的同时将关联表的信息删除
            kuMonitorDao.deleteKuMonitor(id);
            kuGraphicsMonitorService.deleteByCondition(null,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //修改监控项
    @Override
    public void updateKuMonitor(KuMonitor kuMonitor) {
        KuMonitorEntity entity = BeanMapper.map(kuMonitor, KuMonitorEntity.class);
        kuMonitorDao.updateKuMonitor(entity);
    }

    //根据k8sid查询所有的监控项
    @Override
    public List<KuMonitor> findAllKuMonitor(String kuId) {
        return kuMonitorDao.findAllKuMonitor(kuId);
    }

    /**
     * 查询k8s下的集群和监控项信息
     */
    @Override
    public List<KuMonitor> findKuAndMonitor() {
        return kuMonitorDao.findKuAndMonitor();
    }

    //根据k8s监控的id删除监控项
    @Override
    public void deleteByKuId(String id) {
        kuMonitorDao.deleteByKuId(id);
    }

    /**
     * 根据监控项类型查询监控项字典表
     */
    @Override
    public List<KubernetesItem> findItemList(KubernetesItem kubernetesItem) {
        return kubernetesItemService.findItemList(kubernetesItem);
    }

    @Override
    public List<KuMonitor> findKuMonitorList(KuMonitorQuery kuMonitorQuery) {
        List<KuMonitorEntity> kuMonitorEntities = kuMonitorDao.findKuMonitorList(kuMonitorQuery);


        List<KuMonitor> kuMonitorList = BeanMapper.mapList(kuMonitorEntities, KuMonitor.class);

        joinTemplate.joinQuery(kuMonitorList);

        return kuMonitorList;
    }
}
