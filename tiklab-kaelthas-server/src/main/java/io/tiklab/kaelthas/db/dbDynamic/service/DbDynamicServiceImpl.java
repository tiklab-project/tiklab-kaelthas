package io.tiklab.kaelthas.db.dbDynamic.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.common.util.ConversionDateUtil;
import io.tiklab.kaelthas.db.dbDynamic.dao.DbDynamicDao;
import io.tiklab.kaelthas.db.dbDynamic.entity.DbDynamicEntity;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;
import io.tiklab.toolkit.beans.BeanMapper;
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
