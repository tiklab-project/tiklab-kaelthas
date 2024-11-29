package io.tiklab.kaelthas.host.hostDynamic.service;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderTypeEnum;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.hostDynamic.dao.HostDynamicDao;
import io.tiklab.kaelthas.host.hostDynamic.entity.HostDynamicEntity;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主机下的动态
 */
@Service
@Exporter
public class HostDynamicServiceImpl implements HostDynamicService {

    @Autowired
    HostDynamicDao hostDynamicDao;

    /**
     * 主机下的动态信息,分页查询
     */
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

    /**
     * 创建主机下的动态信息
     */
    @Override
    public void createHostDynamic(HostDynamic hostDynamic) {
        HostDynamicEntity entity = BeanMapper.map(hostDynamic, HostDynamicEntity.class);
        hostDynamicDao.createHostDynamic(entity);
    }
}
