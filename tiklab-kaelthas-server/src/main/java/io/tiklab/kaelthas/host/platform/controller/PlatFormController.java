package io.tiklab.kaelthas.host.platform.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.host.platform.service.PlatFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/system")
@RestController
public class PlatFormController {

    @Autowired
    PlatFormService platFormService;

    @RequestMapping(value = "/count",method = RequestMethod.POST)
    public Result<Map<String,Object>> count(){
        Map<String,Object> map = platFormService.count();
        return Result.ok(map);
    }
}

