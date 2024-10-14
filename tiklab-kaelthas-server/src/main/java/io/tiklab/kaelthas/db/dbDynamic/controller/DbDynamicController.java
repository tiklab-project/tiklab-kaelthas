package io.tiklab.kaelthas.db.dbDynamic.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.dbDynamic.model.DbDynamic;
import io.tiklab.kaelthas.db.dbDynamic.service.DbDynamicService;
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
