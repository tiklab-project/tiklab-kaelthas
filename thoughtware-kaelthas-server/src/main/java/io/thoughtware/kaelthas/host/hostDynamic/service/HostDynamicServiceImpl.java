package io.thoughtware.kaelthas.host.hostDynamic.service;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderTypeEnum;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.host.hostDynamic.dao.HostDynamicDao;
import io.thoughtware.kaelthas.host.hostDynamic.entity.HostDynamicEntity;
import io.thoughtware.kaelthas.host.hostDynamic.model.HostDynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class HostDynamicServiceImpl implements HostDynamicService {

    @Autowired
    HostDynamicDao hostDynamicDao;

    @Override
    public Pagination<HostDynamic> findPage(HostDynamic hostDynamic) {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostDynamicEntity.class)
                .eq("hostId", hostDynamic.getHostId())
                .pagination(hostDynamic.getPageParam())
                .order(new Order("time", OrderTypeEnum.desc))
                .get();
        Pagination<HostDynamicEntity> hostDynamicDaoPage = hostDynamicDao.findPage(queryCondition);

        List<HostDynamic> hostDynamics = BeanMapper.mapList(hostDynamicDaoPage.getDataList(), HostDynamic.class);

        return PaginationBuilder.build(hostDynamicDaoPage,hostDynamics);
    }

    @Override
    public void createHostDynamic(HostDynamic hostDynamic) {
        HostDynamicEntity entity = BeanMapper.map(hostDynamic, HostDynamicEntity.class);
        hostDynamicDao.createHostDynamic(entity);
    }
}
