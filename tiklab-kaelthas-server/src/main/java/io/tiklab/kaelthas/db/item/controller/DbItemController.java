package io.tiklab.kaelthas.db.item.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.db.item.model.DbItem;
import io.tiklab.kaelthas.db.item.service.DbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dbItem")
public class DbItemController {

    @Autowired
    private DbItemService dbItemService;

    @RequestMapping(value = "/findItemListByType",method = RequestMethod.POST)
    public Result<?> findItemListByType(@RequestBody DbItem dbItem){
        List<DbItem> dbItemList = dbItemService.findItemListByType(dbItem);
        return Result.ok(dbItemList);
    }

}
