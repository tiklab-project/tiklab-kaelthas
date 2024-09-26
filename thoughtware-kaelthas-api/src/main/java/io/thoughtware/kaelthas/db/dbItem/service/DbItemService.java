package io.thoughtware.kaelthas.db.dbItem.service;

import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.db.dbItem.model.DbItem;

import java.util.List;

@JoinProvider(model = DbItem.class)
public interface DbItemService {

    //获取所有的数据库类型监控指标信息
    @FindAll
    List<DbItem> findAll();

    List<DbItem> findItemListByType(DbItem dbItem);

    @FindList
    List<DbItem> findList(List<Integer> idList);

    @FindOne
    DbItem findOne(String id);
}
