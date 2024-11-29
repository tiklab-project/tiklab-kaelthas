package io.tiklab.kaelthas.db.dbItem.service;

import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.db.dbItem.dao.DbItemDao;
import io.tiklab.kaelthas.db.dbItem.entity.DbItemEntity;
import io.tiklab.kaelthas.db.dbItem.model.DbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据库监控项的字典项
 */
@Service
public class DbItemServiceImpl implements DbItemService{

    @Autowired
    private DbItemDao dbItemDao;

    //查询监控数据库所有的字典项
    @Override
    public List<DbItem> findAll() {
        List<DbItemEntity> dbItemEntityList = dbItemDao.findAll();
        return BeanMapper.mapList(dbItemEntityList, DbItem.class);
    }

    //根据数据库类型查询出数据监控项的字典项
    @Override
    public List<DbItem> findItemListByType(DbItem dbItem) {
        QueryCondition queryCondition = QueryBuilders.createQuery(DbItemEntity.class)
                .eq("dbType", dbItem.getDbType())
                .get();
        List<DbItemEntity> entityList = dbItemDao.findItemListByType(queryCondition);

        return BeanMapper.mapList(entityList, DbItem.class);
    }

    //findList方法
    @Override
    public List<DbItem> findList(List<Integer> idList) {
        List<DbItemEntity> list = dbItemDao.findList(idList);
        return BeanMapper.mapList(list,DbItem.class);
    }

    //findOne方法
    @Override
    public DbItem findOne(String id) {
        DbItemEntity dbItemEntity = dbItemDao.findOne(id);
        return BeanMapper.map(dbItemEntity,DbItem.class);
    }
}
