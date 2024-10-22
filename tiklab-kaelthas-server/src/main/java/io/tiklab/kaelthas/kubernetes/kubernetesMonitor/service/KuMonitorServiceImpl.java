package io.tiklab.kaelthas.kubernetes.kubernetesMonitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.service.KuGraphicsMonitorService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.dao.KuMonitorDao;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.entity.KuMonitorEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.model.KuMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuMonitorServiceImpl implements KuMonitorService{

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private KuMonitorDao kuMonitorDao;

    @Autowired
    private KuGraphicsMonitorService kuGraphicsMonitorService;

    @Override
    public Pagination<KuMonitor> findKuMonitorPage(KuMonitor kuMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(KuMonitorEntity.class)
                .eq("kuId", kuMonitor.getKuId())
                .like("name", kuMonitor.getName())
                .pagination(kuMonitor.getPageParam())
                .get();

        Pagination<KuMonitorEntity> pagination = kuMonitorDao.findKuMonitorPage(queryCondition);

        List<KuMonitor> kuMonitorList = BeanMapper.mapList(pagination.getDataList(), KuMonitor.class);

        joinTemplate.joinQuery(kuMonitorList);

        return PaginationBuilder.build(pagination, kuMonitorList);
    }

    @Override
    public String createKuMonitor(KuMonitor kuMonitor) {
        KuMonitorEntity entity = BeanMapper.map(kuMonitor, KuMonitorEntity.class);
        return kuMonitorDao.createKuMonitor(entity);
    }

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

    @Override
    public void updateKuMonitor(KuMonitor kuMonitor) {
        KuMonitorEntity entity = BeanMapper.map(kuMonitor, KuMonitorEntity.class);
        kuMonitorDao.updateKuMonitor(entity);
    }

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

    @Override
    public void deleteByKuId(String id) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KuMonitorEntity.class)
                .eq("kuId", id)
                .get();
        kuMonitorDao.deleteByKuId(deleteCondition);
    }
}
