package io.thoughtware.kaelthas.db.dbMonitor.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.db.dbDynamic.model.DbDynamic;
import io.thoughtware.kaelthas.db.dbDynamic.service.DbDynamicService;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.kaelthas.db.dbGraphicsMonitor.service.DbGraphicsMonitorService;
import io.thoughtware.kaelthas.db.dbMonitor.dao.DbMonitorDao;
import io.thoughtware.kaelthas.db.dbMonitor.entity.DbMonitorEntity;
import io.thoughtware.kaelthas.db.dbMonitor.model.DbMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbMonitorServiceImpl implements DbMonitorService {

    @Autowired
    private DbMonitorDao dbMonitorDao;

    @Autowired
    private DbGraphicsMonitorService dbGraphicsMonitorService;

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private DbDynamicService dbDynamicService;

    @Override
    public Pagination<DbMonitor> findDbMonitorPage(DbMonitor dbMonitor) {

        QueryCondition queryCondition = QueryBuilders.createQuery(DbMonitorEntity.class)
                .eq("dbId", dbMonitor.getDbId())
                .like("name", dbMonitor.getName())
                .pagination(dbMonitor.getPageParam())
                .get();

        Pagination<DbMonitorEntity> pagination = dbMonitorDao.findDbMonitorPage(queryCondition);
        List<DbMonitor> dbMonitors = BeanMapper.mapList(pagination.getDataList(), DbMonitor.class);
        joinTemplate.joinQuery(dbMonitors);
        return PaginationBuilder.build(pagination, dbMonitors);
    }

    @Override
    public String createDbMonitor(DbMonitor dbMonitor) {
        try {
            DbMonitorEntity dbMonitorEntity = BeanMapper.map(dbMonitor, DbMonitorEntity.class);
            String dbId = dbMonitorDao.createDbMonitor(dbMonitorEntity);
            DbDynamic dbDynamic = new DbDynamic();
            dbDynamic.setName("创建监控项———" + dbMonitor.getName());
            dbDynamic.setDbId(dbMonitor.getDbId());
            dbDynamicService.createDbDynamic(dbDynamic);
            return dbId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteDbMonitor(String id) {
        try {
            //删除监控项
            dbMonitorDao.deleteDbMonitor(id);
            //删除图表与监控项关联表的信息
            dbGraphicsMonitorService.deleteByCation(null, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDbMonitor(DbMonitor dbMonitor) {
        DbMonitorEntity dbMonitorEntity = BeanMapper.map(dbMonitor, DbMonitorEntity.class);
        dbMonitorDao.updateDbMonitor(dbMonitorEntity);
    }

    @Override
    public List<DbMonitor> findAllMonitor(DbMonitor dbMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbMonitorEntity.class)
                .eq("dbId", dbMonitor.getDbId())
                .get();
        List<DbMonitorEntity> entityList = dbMonitorDao.findAllMonitor(queryCondition);

        return BeanMapper.mapList(entityList, DbMonitor.class);
    }

    @Override
    public DbMonitor findListById(String monitorId) {
        return dbMonitorDao.findListById(monitorId);
    }

    @Override
    public List<DbMonitor> findMonitorNum(String dbId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbMonitorEntity.class)
                .eq("dbId", dbId)
                .get();
        List<DbMonitorEntity> dbMonitorEntityList = dbMonitorDao.findMonitorNum(queryCondition);
        return BeanMapper.mapList(dbMonitorEntityList, DbMonitor.class);
    }
}
