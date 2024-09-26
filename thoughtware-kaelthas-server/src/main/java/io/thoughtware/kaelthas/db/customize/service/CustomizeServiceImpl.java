package io.thoughtware.kaelthas.db.customize.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.db.customize.dao.CustomizeDao;
import io.thoughtware.kaelthas.db.customize.entity.CustomizeEntity;
import io.thoughtware.kaelthas.db.customize.model.Customize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
