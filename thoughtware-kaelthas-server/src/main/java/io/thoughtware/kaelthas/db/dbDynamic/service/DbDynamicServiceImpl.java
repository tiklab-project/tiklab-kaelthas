package io.thoughtware.kaelthas.db.dbDynamic.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.common.util.ConversionDateUtil;
import io.thoughtware.kaelthas.db.dbDynamic.dao.DbDynamicDao;
import io.thoughtware.kaelthas.db.dbDynamic.entity.DbDynamicEntity;
import io.thoughtware.kaelthas.db.dbDynamic.model.DbDynamic;
import io.thoughtware.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbDynamicServiceImpl implements DbDynamicService{

    @Autowired
    private DbDynamicDao dbDynamicDao;

    @Override
    public Pagination<DbDynamic> findDynamicPage(DbDynamic dbDynamic) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbDynamicEntity.class)
                .eq("dbId", dbDynamic.getDbId())
                .pagination(dbDynamic.getPageParam())
                .get();
        Pagination<DbDynamicEntity> pagination = dbDynamicDao.findDynamicPage(queryCondition);

        List<DbDynamic> dbDynamics = BeanMapper.mapList(pagination.getDataList(), DbDynamic.class);

        return PaginationBuilder.build(pagination,dbDynamics);
    }

    @Override
    public void createDbDynamic(DbDynamic dbDynamic) {
        String date = ConversionDateUtil.date(9);
        DbDynamicEntity entity = BeanMapper.map(dbDynamic, DbDynamicEntity.class);
        entity.setTime(date);
        dbDynamicDao.createDbDynamic(entity);
    }
}
