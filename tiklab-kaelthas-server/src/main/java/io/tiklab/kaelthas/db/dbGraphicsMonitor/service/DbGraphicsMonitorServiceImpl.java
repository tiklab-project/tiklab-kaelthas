package io.tiklab.kaelthas.db.dbGraphicsMonitor.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.db.dbGraphics.model.DbGraphics;
import io.tiklab.kaelthas.db.dbGraphicsMonitor.dao.DbGraphicsMonitorDao;
import io.tiklab.kaelthas.db.dbGraphicsMonitor.entity.DbGraphicsMonitorEntity;
import io.tiklab.kaelthas.db.dbGraphicsMonitor.model.DbGraphicsMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbGraphicsMonitorServiceImpl implements DbGraphicsMonitorService{

    @Autowired
    private DbGraphicsMonitorDao dbGraphicsMonitorDao;

    @Override
    public void deleteByCation(String graphicsId, String monitorId,String dbId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DbGraphicsMonitorEntity.class)
                .eq("graphicsId", graphicsId)
                .eq("monitorId",monitorId)
                .eq("dbId",dbId)
                .get();
        dbGraphicsMonitorDao.deleteByGraphicsId(deleteCondition);
    }

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

    @Override
    public List<String> findMonitorIds(String graphicsId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbGraphicsMonitorEntity.class)
                .eq("graphicsId", graphicsId)
                .get();
        List<DbGraphicsMonitorEntity> list = dbGraphicsMonitorDao.findMonitorIds(queryCondition);

        return list.stream().map(DbGraphicsMonitorEntity::getMonitorId).toList();
    }

    @Override
    public List<DbGraphicsMonitor> findMonitors(String graphicsId) {
        return dbGraphicsMonitorDao.findMonitors(graphicsId);
    }
}
