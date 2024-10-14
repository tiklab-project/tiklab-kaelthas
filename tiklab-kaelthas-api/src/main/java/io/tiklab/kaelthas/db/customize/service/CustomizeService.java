package io.tiklab.kaelthas.db.customize.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.customize.model.Customize;

public interface CustomizeService {

    //自定义SQL的分页查询
    Pagination<Customize> findCustomizePage(Customize customize);

    //添加自定义SQL
    String createCustomize(Customize customize);

    //自定义SQL的修改
    void updateCustomize(Customize customize);

    //删除定义的SQL
    void deleteCustomize(String id);
}
