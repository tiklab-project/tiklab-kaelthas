package io.tiklab.kaelthas.db.setting.service;

import io.tiklab.kaelthas.db.database.model.DbInfo;

/**
 * 监控数据库的设置
 */
public interface DbSettingService {

    /**
     * 根据id查询数据源
     */
    DbInfo findDbInfoById(String id);

    /**
     * 修改数据源
     */
    void updateDbInfo(DbInfo dbInfo);

    /**
     * 删除数据源
     */
    void deleteDbInfo(String id);
}
