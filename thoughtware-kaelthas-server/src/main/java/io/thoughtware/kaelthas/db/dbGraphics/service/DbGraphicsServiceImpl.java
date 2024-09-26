package io.thoughtware.kaelthas.db.dbGraphics.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.db.dbDynamic.model.DbDynamic;
import io.thoughtware.kaelthas.db.dbDynamic.service.DbDynamicService;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.db.dbGraphics.dao.DbGraphicsDao;
import io.thoughtware.kaelthas.db.dbGraphics.entity.DbGraphicsEntity;
import io.thoughtware.kaelthas.db.dbGraphics.model.DbGraphics;
import io.thoughtware.kaelthas.db.dbGraphicsMonitor.service.DbGraphicsMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbGraphicsServiceImpl implements DbGraphicsService{

    @Autowired
    private DbGraphicsDao dbGraphicsDao;

    @Autowired
    private DbGraphicsMonitorService graphicsMonitorService;

    @Autowired
    private DbDynamicService dbDynamicService;

    @Override
    public Pagination<DbGraphics> findGraphicsPage(DbGraphics dbGraphics) {
        return dbGraphicsDao.findGraphicsPage(dbGraphics);
    }

    @Override
    public String createGraphics(DbGraphics dbGraphics) {

        try {

            DbGraphicsEntity graphicsEntity = BeanMapper.map(dbGraphics, DbGraphicsEntity.class);
            String graphicsId = dbGraphicsDao.createGraphics(graphicsEntity);
            dbGraphics.setId(graphicsId);
            graphicsMonitorService.createGraphicsMonitor(dbGraphics);

            DbDynamic dbDynamic = new DbDynamic();
            dbDynamic.setDbId(dbGraphics.getDbId());
            dbDynamic.setName("创建图形———"+dbGraphics.getName());

            dbDynamicService.createDbDynamic(dbDynamic);
            return graphicsId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGraphics(String id) {
        try {
            //先删除图形表的数据
            dbGraphicsDao.deleteGraphics(id);

            //再去删除关联表
            graphicsMonitorService.deleteByCation(id,null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGraphics(DbGraphics dbGraphics) {
        if (dbGraphics.getMonitorIds().isEmpty()) {
            DbGraphicsEntity entity = BeanMapper.map(dbGraphics, DbGraphicsEntity.class);
            dbGraphicsDao.updateGraphics(entity);
        }else {
            try {
                //首先,将原来的关联关系删除,再添加新的
                graphicsMonitorService.deleteByCation(dbGraphics.getId(),null);
                //添加新的关联关系
                graphicsMonitorService.createGraphicsMonitor(dbGraphics);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<String> findMonitorIds(String graphicsId) {
        return graphicsMonitorService.findMonitorIds(graphicsId);
    }

    @Override
    public List<DbGraphics> findDbGraphicsList(String hostId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbGraphicsEntity.class)
                .eq("dbId", hostId)
                .get();
        List<DbGraphicsEntity> entityList = dbGraphicsDao.findDbGraphicsList(queryCondition);

        return BeanMapper.mapList(entityList,DbGraphics.class);
    }
}
