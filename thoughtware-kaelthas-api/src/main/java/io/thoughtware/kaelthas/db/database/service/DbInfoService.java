package io.thoughtware.kaelthas.db.database.service;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.db.dbMonitor.model.DbMonitor;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.db.database.model.DbInfo;

import java.util.List;

@JoinProvider(model = DbInfo.class)
public interface DbInfoService {

    //dbInfo表的分页查询
    Pagination<DbInfo> findDbInfoPage(DbInfo dbInfo);

    //创建数据源
    String createDbInfo(DbInfo dbInfo);

    //修改数据源信息
    void updateDbInfo(DbInfo dbInfo);

    //删除数据源信息
    void deleteDbInfo(String id);

    //根据id查询数据源
    DbInfo findDbInfoById(String id);

    //测试数据源是否可用
    Result<?> testSql(DbInfo dbInfo);

    //查询所有数据库的监控项信息
    List<DbMonitor> findUsableDbInfoList();

    //查询出最后修改的四个数据库
    List<DbInfo> findDropDown();

    @FindAll
    List<DbInfo> findAll();

    //查找MySQL的监控项信息
    List<DbMonitor> findMysqlItemList();

}
