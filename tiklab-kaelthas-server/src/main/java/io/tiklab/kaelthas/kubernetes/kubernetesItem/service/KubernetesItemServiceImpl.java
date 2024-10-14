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
