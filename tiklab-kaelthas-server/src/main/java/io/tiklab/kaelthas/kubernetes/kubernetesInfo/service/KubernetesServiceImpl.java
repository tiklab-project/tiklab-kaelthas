package io.tiklab.kaelthas.kubernetes.kubernetesInfo.service;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderTypeEnum;
import io.tiklab.core.page.Page;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.service.KuGraphicsService;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphicsMonitor.service.KuGraphicsMonitorService;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.dao.KubernetesDao;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.entity.KubernetesEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.service.KuMonitorService;
import io.tiklab.kaelthas.kubernetes.kubernetesTrigger.service.KuTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * k8s监控的信息表
 */
@Service
public class KubernetesServiceImpl implements KubernetesService {

    @Autowired
    private KubernetesDao kubernetesDao;

    @Autowired
    private KuGraphicsService kuGraphicsService;

    @Autowired
    private KuGraphicsMonitorService kuGraphicsMonitorService;

    @Autowired
    private KuMonitorService kuMonitorService;

    @Autowired
    private KuTriggerService kuTriggerService;

    //k8s的分页查询
    @Override
    public Pagination<Kubernetes> findKbInfoPage(Kubernetes kubernetes) {
        return kubernetesDao.findKbInfoPage(kubernetes);
    }

    //创建k8s监控监控
    @Override
    public String createKbInfo(Kubernetes kubernetes) {
        KubernetesEntity kubernetesEntity = BeanMapper.map(kubernetes, KubernetesEntity.class);
        String date = ConversionDateUtil.date(9);
        kubernetesEntity.setCreateTime(date);
        kubernetesEntity.setColor((int) Math.round(Math.random() * 5));
        return kubernetesDao.createKbInfo(kubernetesEntity);
    }

    //修改k8s监控信息
    @Override
    public void updateKbInfo(Kubernetes kubernetes) {
        KubernetesEntity kubernetesEntity = BeanMapper.map(kubernetes, KubernetesEntity.class);
        String date = ConversionDateUtil.date(9);
        kubernetesEntity.setUpdateTime(date);
        kubernetesDao.updateKbInfo(kubernetesEntity);
    }

    //根据id删除监控的k8s信息
    @Override
    public void deleteKuInfo(String id) {
        try {
            //1.查找集群下的图形有多少,现将图形下与监控项关联表的数据进行删除
            List<KuGraphics> kuGraphicsList = kuGraphicsService.findKuGraphicsList(id);
            List<String> stringList = kuGraphicsList.stream().map(KuGraphics::getId).toList();
            kuGraphicsMonitorService.deleteByGraphicsIds(stringList);
            //2.然后删除集群下的图形表信息
            kuGraphicsService.deleteByKuId(id);
            //3.删除集群下的监控项和触发器
            kuMonitorService.deleteByKuId(id);
            kuTriggerService.deleteKuTriggerByKuId(id);
            //4.删除集群表的信息
            kubernetesDao.deleteKuInfo(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据时间倒排,查询四个k8s监控信息
    @Override
    public List<Kubernetes> findKuDropDown() {
        QueryCondition queryCondition = QueryBuilders.createQuery(KubernetesEntity.class)
                .order(new Order("updateTime", OrderTypeEnum.desc))
                .pagination(new Page(1, 4))
                .get();
        Pagination<KubernetesEntity> kuDropDown = kubernetesDao.findKuDropDown(queryCondition);

        return BeanMapper.mapList(kuDropDown.getDataList(),Kubernetes.class);
    }

    //根据id查询k8s的监控信息
    @Override
    public Kubernetes findKuInfoById(String id) {
        KubernetesEntity kubernetesEntity = kubernetesDao.findKuInfoById(id);
        return BeanMapper.map(kubernetesEntity,Kubernetes.class);
    }

    //查询所有k8s监控信息
    @Override
    public List<Kubernetes> findAll() {
        List<KubernetesEntity> kubernetesEntities = kubernetesDao.findAll();
        return BeanMapper.mapList(kubernetesEntities,Kubernetes.class);
    }
}
