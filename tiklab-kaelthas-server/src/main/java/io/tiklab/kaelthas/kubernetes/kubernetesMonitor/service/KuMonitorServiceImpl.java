package io.tiklab.kaelthas.kubernetes.kubernetesMonitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.entity.KubernetesEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.dao.KuMonitorDao;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.entity.KubernetesMonitorEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.model.KubernetesMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuMonitorServiceImpl implements KuMonitorService{

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private KuMonitorDao kuMonitorDao;

    @Override
    public Pagination<KubernetesMonitor> findKuMonitorPage(KubernetesMonitor kubernetesMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(KubernetesMonitorEntity.class)
                .eq("kuId", kubernetesMonitor.getKuId())
                .like("name", kubernetesMonitor.getName())
                .pagination(kubernetesMonitor.getPageParam())
                .get();

        Pagination<KubernetesMonitorEntity> pagination = kuMonitorDao.findKuMonitorPage(queryCondition);

        List<KubernetesMonitor> kubernetesMonitorList = BeanMapper.mapList(pagination.getDataList(), KubernetesMonitor.class);

        joinTemplate.joinQuery(kubernetesMonitorList);

        return PaginationBuilder.build(pagination, kubernetesMonitorList);
    }

    @Override
    public String createKuMonitor(KubernetesMonitor kubernetesMonitor) {
        KubernetesMonitorEntity entity = BeanMapper.map(kubernetesMonitor, KubernetesMonitorEntity.class);
        return kuMonitorDao.createKuMonitor(entity);
    }

    @Override
    public void deleteKuMonitor(String id) {
        kuMonitorDao.deleteKuMonitor(id);
    }

    @Override
    public void updateKuMonitor(KubernetesMonitor kubernetesMonitor) {
        KubernetesMonitorEntity entity = BeanMapper.map(kubernetesMonitor, KubernetesMonitorEntity.class);
        kuMonitorDao.updateKuMonitor(entity);
    }

    @Override
    public List<KubernetesMonitor> findAllKuMonitor() {
        return kuMonitorDao.findAllKuMonitor();
    }

    /**
     * 查询k8s下的集群和监控项信息
     */
    @Override
    public List<KubernetesMonitor> findKuAndMonitor() {
        return kuMonitorDao.findKuAndMonitor();
    }

    @Override
    public void deleteByKuId(String id) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(KubernetesEntity.class)
                .eq("kuId", id)
                .get();
        kuMonitorDao.deleteByKuId(deleteCondition);
    }
}
