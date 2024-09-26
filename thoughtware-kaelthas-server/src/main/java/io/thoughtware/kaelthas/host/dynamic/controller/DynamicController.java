package io.thoughtware.kaelthas.host.dynamic.controller;

import io.thoughtware.core.Result;
import io.thoughtware.kaelthas.host.dynamic.model.Dynamic;
import io.thoughtware.kaelthas.host.dynamic.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {

    @Autowired
    DynamicService dynamicService;

    @RequestMapping(value = "/findDynamicList",method = RequestMethod.POST)
    public Result<List<Dynamic>> findDynamicList(){
        List<Dynamic> dynamicList = dynamicService.findDynamicList();
        return Result.ok(dynamicList);
    }
}
