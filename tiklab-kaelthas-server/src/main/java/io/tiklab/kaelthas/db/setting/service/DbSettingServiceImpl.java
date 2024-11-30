package io.tiklab.kaelthas.db.setting.service;

import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 监控数据库的设置
 */
@Service
public class DbSettingServiceImpl implements DbSettingService{

    @Autowired
    private DbInfoService dbInfoService;

    /**
     * 根据id查询数据源
     */
    @Override
    public DbInfo findDbInfoById(String id) {
        return dbInfoService.findDbInfoById(id);
    }

    /**
     * 修改数据源
     */
    @Override
    public void updateDbInfo(DbInfo dbInfo) {
        dbInfoService.updateDbInfo(dbInfo);
    }

    /**
     * 删除数据源
     */
    @Override
    public void deleteDbInfo(String id) {
        dbInfoService.deleteDbInfo(id);
    }
}
