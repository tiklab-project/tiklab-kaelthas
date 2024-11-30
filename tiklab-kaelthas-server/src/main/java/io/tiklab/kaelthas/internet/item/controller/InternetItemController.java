package io.tiklab.kaelthas.internet.item.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.internet.item.model.InternetItem;
import io.tiklab.kaelthas.internet.item.service.InternetItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/internetItem")
@RestController
public class InternetItemController {

    @Autowired
    private InternetItemService internetItemService;

    /**
     * 根据类型查询对应的item
     */
    @RequestMapping(value = "/findItemList",method = RequestMethod.POST)
    public Result<?> findItemList(@RequestBody InternetItem internetItem){
        List<InternetItem> itemList = internetItemService.findItemList(internetItem);
        return Result.ok(itemList);
    }

}
