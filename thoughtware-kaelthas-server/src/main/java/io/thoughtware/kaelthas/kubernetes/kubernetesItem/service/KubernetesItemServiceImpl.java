package io.thoughtware.kaelthas.kubernetes.kubernetesItem.service;

import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.kubernetes.kubernetesItem.dao.KubernetesItemDao;
import io.thoughtware.kaelthas.kubernetes.kubernetesItem.entity.KubernetesItemEntity;
import io.thoughtware.kaelthas.kubernetes.kubernetesItem.model.KubernetesItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KubernetesItemServiceImpl implements KubernetesItemService{

    @Autowired
    private KubernetesItemDao kubernetesItemDao;

    @Override
    public List<KubernetesItem> findAll() {
        List<KubernetesItemEntity> entityList = kubernetesItemDao.findAll();
        return BeanMapper.mapList(entityList,KubernetesItem.class);
    }

    @Override
    public List<KubernetesItem> findList(List<String> ids) {
        List<KubernetesItemEntity> entityList = kubernetesItemDao.findList(ids);
        return BeanMapper.mapList(entityList,KubernetesItem.class);
    }

    @Override
    public KubernetesItem findOne(String id) {
        KubernetesItemEntity kubernetesItemEntity = kubernetesItemDao.findOne(id);
        return BeanMapper.map(kubernetesItemEntity, KubernetesItem.class);
    }

    @Override
    public List<KubernetesItem> findItemList(KubernetesItem kubernetesItem) {
        QueryCondition queryCondition = QueryBuilders.createQuery(KubernetesItemEntity.class)
                .eq("kubernetesType", kubernetesItem.getKubernetesType())
                .get();
        List<KubernetesItemEntity> list = kubernetesItemDao.findItemList(queryCondition);
        return BeanMapper.mapList(list, KubernetesItem.class);
    }
}
