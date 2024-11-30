package io.tiklab.kaelthas.db.item.service;

import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.db.item.model.DbItem;

import java.util.List;

/**
 * 数据库监控项的字典项
 */
@JoinProvider(model = DbItem.class)
public interface DbItemService {

    //获取所有的数据库类型监控指标信息
    @FindAll
    List<DbItem> findAll();

    //根据数据库类型查询出数据监控项的字典项
    List<DbItem> findItemListByType(DbItem dbItem);

    //根据ids查询list
    @FindList
    List<DbItem> findList(List<Integer> idList);

    //根据id查询单条信息
    @FindOne
    DbItem findOne(String id);
}
