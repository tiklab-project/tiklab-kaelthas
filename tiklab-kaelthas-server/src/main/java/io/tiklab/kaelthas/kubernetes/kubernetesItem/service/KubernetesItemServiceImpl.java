package io.tiklab.kaelthas.kubernetes.kubernetesItem.service;

import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.kubernetes.kubernetesItem.dao.KubernetesItemDao;
import io.tiklab.kaelthas.kubernetes.kubernetesItem.entity.KubernetesItemEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesItem.model.KubernetesItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * k8s监控的监控项字典表
 */
@Service
public class KubernetesItemServiceImpl implements KubernetesItemService{

    @Autowired
    private KubernetesItemDao kubernetesItemDao;

    //查询所有监控字典项
    @Override
    public List<KubernetesItem> findAll() {
        List<KubernetesItemEntity> entityList = kubernetesItemDao.findAll();
        return BeanMapper.mapList(entityList,KubernetesItem.class);
    }

    //根据ids查询监控字典项
    @Override
    public List<KubernetesItem> findList(List<String> ids) {
        List<KubernetesItemEntity> entityList = kubernetesItemDao.findList(ids);
        return BeanMapper.mapList(entityList,KubernetesItem.class);
    }

    //根据id查询监控字典项
    @Override
    public KubernetesItem findOne(String id) {
        KubernetesItemEntity kubernetesItemEntity = kubernetesItemDao.findOne(id);
        return BeanMapper.map(kubernetesItemEntity, KubernetesItem.class);
    }

    //根据k8s中的类型进行查询字典项
    @Override
    public List<KubernetesItem> findItemList(KubernetesItem kubernetesItem) {
        QueryCondition queryCondition = QueryBuilders.createQuery(KubernetesItemEntity.class)
                .eq("kubernetesType", kubernetesItem.getKubernetesType())
                .get();
        List<KubernetesItemEntity> list = kubernetesItemDao.findItemList(queryCondition);
        return BeanMapper.mapList(list, KubernetesItem.class);
    }
}
