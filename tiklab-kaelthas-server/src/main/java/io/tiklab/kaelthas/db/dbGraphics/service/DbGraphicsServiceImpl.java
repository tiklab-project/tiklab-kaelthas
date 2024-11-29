package io.tiklab.kaelthas.db.dbGraphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;
import io.tiklab.kaelthas.db.dbDynamic.service.DbDynamicService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.db.dbGraphics.dao.DbGraphicsDao;
import io.tiklab.kaelthas.db.dbGraphics.entity.DbGraphicsEntity;
import io.tiklab.kaelthas.db.dbGraphics.model.DbGraphics;
import io.tiklab.kaelthas.db.dbGraphicsMonitor.service.DbGraphicsMonitorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据库监控的图形配置
 */
@Service
public class DbGraphicsServiceImpl implements DbGraphicsService {

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

    //创建图形
    @Override
    public String createGraphics(DbGraphics dbGraphics) {

        try {

            DbGraphicsEntity graphicsEntity = BeanMapper.map(dbGraphics, DbGraphicsEntity.class);
            String graphicsId = dbGraphicsDao.createGraphics(graphicsEntity);
            dbGraphics.setId(graphicsId);
            graphicsMonitorService.createGraphicsMonitor(dbGraphics);

            DbDynamic dbDynamic = new DbDynamic();
            dbDynamic.setDbId(dbGraphics.getDbId());
            dbDynamic.setName("创建图形———" + dbGraphics.getName());

            dbDynamicService.createDbDynamic(dbDynamic);
            return graphicsId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //删除图形
    @Override
    public void deleteGraphics(String id) {
        try {
            //先删除图形表的数据
            dbGraphicsDao.deleteGraphics(id);

            //再去删除关联表
            graphicsMonitorService.deleteByCation(id, null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //修改图形
    @Override
    public void updateGraphics(DbGraphics dbGraphics) {
        if (dbGraphics.getMonitorIds().isEmpty()) {
            DbGraphicsEntity entity = BeanMapper.map(dbGraphics, DbGraphicsEntity.class);
            dbGraphicsDao.updateGraphics(entity);
        } else {
            try {
                //首先,将原来的关联关系删除,再添加新的
                graphicsMonitorService.deleteByCation(dbGraphics.getId(), null, null);
                //添加新的关联关系
                graphicsMonitorService.createGraphicsMonitor(dbGraphics);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //查询出图形的ids
    @Override
    public List<String> findMonitorIds(String graphicsId) {
        return graphicsMonitorService.findMonitorIds(graphicsId);
    }

    //根据监控数据库的id查询出图形的列表信息
    @Override
    public List<DbGraphics> findDbGraphicsList(String hostId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbGraphicsEntity.class)
                .eq("dbId", hostId)
                .get();
        List<DbGraphicsEntity> entityList = dbGraphicsDao.findDbGraphicsList(queryCondition);

        return BeanMapper.mapList(entityList, DbGraphics.class);
    }

    //根据监控数据库的id删除
    @Override
    public void deleteByDbId(String dbId) {
        if (StringUtils.isBlank(dbId)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders
                .createDelete(DbGraphicsEntity.class)
                .eq("dbId", dbId)
                .get();
        dbGraphicsDao.deleteByDbId(deleteCondition);
    }
}
