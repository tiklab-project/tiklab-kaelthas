package io.thoughtware.kaelthas.db.dbDynamic.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.kaelthas.db.dbDynamic.model.DbDynamic;
import io.thoughtware.kaelthas.db.dbDynamic.service.DbDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbDynamic")
public class DbDynamicController {

    @Autowired
    private DbDynamicService dbDynamicService;

    @RequestMapping(value = "/findDynamicPage",method = RequestMethod.POST)
    public Result<?> findDynamicPage(@RequestBody DbDynamic dbDynamic){
        Pagination<DbDynamic> pagination = dbDynamicService.findDynamicPage(dbDynamic);
        return Result.ok(pagination);
    }

}
