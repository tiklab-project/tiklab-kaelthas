package io.tiklab.kaelthas.db.graphicsMonitor.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.db.graphics.model.DbGraphics;
import io.tiklab.kaelthas.db.graphicsMonitor.dao.DbGraphicsMonitorDao;
import io.tiklab.kaelthas.db.graphicsMonitor.entity.DbGraphicsMonitorEntity;
import io.tiklab.kaelthas.db.graphicsMonitor.model.DbGraphicsMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 监控数据库的监控项和图形的中间表
 */
@Service
public class DbGraphicsMonitorServiceImpl implements DbGraphicsMonitorService{

    @Autowired
    private DbGraphicsMonitorDao dbGraphicsMonitorDao;

    /**
     * 根据图形id或者监控项id或者是监控数据库id删除关联表
     */
    @Override
    public void deleteByCation(String graphicsId, String monitorId,String dbId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DbGraphicsMonitorEntity.class)
                .eq("graphicsId", graphicsId)
                .eq("monitorId",monitorId)
                .eq("dbId",dbId)
                .get();
        dbGraphicsMonitorDao.deleteByGraphicsId(deleteCondition);
    }

    /**
     * 创建图形和监控项的关联信息
     */
    @Override
    public void createGraphicsMonitor(DbGraphics dbGraphics) {
        if (dbGraphics.getMonitorIds().isEmpty()) {
            return;
        }
        for (String monitorId : dbGraphics.getMonitorIds()) {
            DbGraphicsMonitorEntity dbGraphicsMonitorEntity = new DbGraphicsMonitorEntity();
            dbGraphicsMonitorEntity.setGraphicsId(dbGraphics.getId());
            dbGraphicsMonitorEntity.setMonitorId(monitorId);
            dbGraphicsMonitorEntity.setDbId(dbGraphics.getDbId());
            dbGraphicsMonitorDao.createGraphicsMonitor(dbGraphicsMonitorEntity);
        }
    }

    /**
     * 根据图形的id查询出关联的监控项ids
     */
    @Override
    public List<String> findMonitorIds(String graphicsId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbGraphicsMonitorEntity.class)
                .eq("graphicsId", graphicsId)
                .get();
        List<DbGraphicsMonitorEntity> list = dbGraphicsMonitorDao.findMonitorIds(queryCondition);

        return list.stream().map(DbGraphicsMonitorEntity::getMonitorId).toList();
    }

    /**
     * 根据图形的id查询关联表的信息
     */
    @Override
    public List<DbGraphicsMonitor> findMonitors(String graphicsId) {
        return dbGraphicsMonitorDao.findMonitors(graphicsId);
    }
}
