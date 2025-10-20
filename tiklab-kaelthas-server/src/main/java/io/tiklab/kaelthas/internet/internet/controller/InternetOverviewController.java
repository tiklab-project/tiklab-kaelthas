package io.tiklab.kaelthas.internet.internet.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.internet.model.InternetOverview;
import io.tiklab.kaelthas.internet.internet.service.InternetOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;
/**
 * 网络监控概况页
 */
@RestController
@RequestMapping("/inOverview")
public class InternetOverviewController {

    @Autowired
    InternetOverviewService internetOverviewService;

    /**
     * 查询网络列表
     */
    @RequestMapping(value = "/findInternetByInId",method = RequestMethod.POST)
    public Result<Map<String, Object>> findInternetByInId(@NotNull String internetId){
        Map<String, Object> internetOverview = internetOverviewService.findInternetByInId(internetId);
        return Result.ok(internetOverview);
    }


}
