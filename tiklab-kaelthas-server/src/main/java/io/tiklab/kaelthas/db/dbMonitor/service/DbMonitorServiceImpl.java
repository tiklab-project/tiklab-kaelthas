package io.tiklab.kaelthas.db.dbMonitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;
import io.tiklab.kaelthas.db.dbDynamic.service.DbDynamicService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.db.dbGraphicsMonitor.service.DbGraphicsMonitorService;
import io.tiklab.kaelthas.db.dbMonitor.dao.DbMonitorDao;
import io.tiklab.kaelthas.db.dbMonitor.entity.DbMonitorEntity;
import io.tiklab.kaelthas.db.dbMonitor.model.DbMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据库监控项
 */

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

    /**
     * 数据库监控项的分页查询
     */
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

    /**
     * 创建监控项
     */
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

    /**
     * 删除监控项
     */
    @Override
    public void deleteDbMonitor(String id) {
        try {
            //删除监控项
            dbMonitorDao.deleteDbMonitor(id);
            //删除图表与监控项关联表的信息
            dbGraphicsMonitorService.deleteByCation(null, id,null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改监控项
     */
    @Override
    public void updateDbMonitor(DbMonitor dbMonitor) {
        DbMonitorEntity dbMonitorEntity = BeanMapper.map(dbMonitor, DbMonitorEntity.class);
        dbMonitorDao.updateDbMonitor(dbMonitorEntity);
    }

    /**
     * 根据监控数据库id查询监控项list
     */
    @Override
    public List<DbMonitor> findAllMonitor(DbMonitor dbMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbMonitorEntity.class)
                .eq("dbId", dbMonitor.getDbId())
                .get();
        List<DbMonitorEntity> entityList = dbMonitorDao.findAllMonitor(queryCondition);

        return BeanMapper.mapList(entityList, DbMonitor.class);
    }

    /**
     * 根据监控数据库id查询监控项list
     */
    @Override
    public List<DbMonitor> findMonitorNum(String dbId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbMonitorEntity.class)
                .eq("dbId", dbId)
                .get();
        List<DbMonitorEntity> dbMonitorEntityList = dbMonitorDao.findMonitorNum(queryCondition);
        return BeanMapper.mapList(dbMonitorEntityList, DbMonitor.class);
    }

    /**
     * 根据监控数据库id删除监控项信息
     */
    @Override
    public void deleteByDbId(String id) {
        if (StringUtils.isBlank(id)) {
            return;
        }

        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DbMonitorEntity.class)
                .eq("dbId", id)
                .get();

        dbMonitorDao.deleteByDbId(deleteCondition);
    }
}
