package io.thoughtware.kaelthas.medium.controller;

import io.thoughtware.core.Result;
import io.thoughtware.kaelthas.medium.model.Medium;
import io.thoughtware.kaelthas.medium.service.MediumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medium")
public class MediumController {

    @Autowired
    private MediumService mediumService;

    @RequestMapping(value = "/getMediumAllList",method = RequestMethod.POST)
    public Result<List<Medium>> getMediumAllList(){
        List<Medium> mediumList = mediumService.getMediumAllList();
        return Result.ok(mediumList);
    }
}
