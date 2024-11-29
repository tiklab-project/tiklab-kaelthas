package io.tiklab.kaelthas.db.customize.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.db.customize.dao.CustomizeDao;
import io.tiklab.kaelthas.db.customize.entity.CustomizeEntity;
import io.tiklab.kaelthas.db.customize.model.Customize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义SQL的增删改查,暂未开发使用
 */
@Service
public class CustomizeServiceImpl implements CustomizeService{

    @Autowired
    private CustomizeDao customizeDao;

    @Override
    public Pagination<Customize> findCustomizePage(Customize customize) {
        QueryCondition queryCondition = QueryBuilders.createQuery(CustomizeEntity.class)
                .eq("dbId",customize.getDbId())
                .like("describe", customize.getDescribe())
                .pagination(customize.getPageParam())
                .get();
        Pagination<CustomizeEntity> entityPagination = customizeDao.findCustomizePage(queryCondition);

        List<Customize> customizes = BeanMapper.mapList(entityPagination.getDataList(), Customize.class);
        return PaginationBuilder.build(entityPagination,customizes);
    }

    @Override
    public String createCustomize(Customize customize) {
        CustomizeEntity customizeEntity = BeanMapper.map(customize, CustomizeEntity.class);
        return customizeDao.createCustomize(customizeEntity);
    }

    @Override
    public void updateCustomize(Customize customize) {
        CustomizeEntity customizeEntity = BeanMapper.map(customize, CustomizeEntity.class);
        customizeDao.updateCustomize(customizeEntity);
    }

    @Override
    public void deleteCustomize(String id) {
        customizeDao.deleteCustomize(id);
    }
}
