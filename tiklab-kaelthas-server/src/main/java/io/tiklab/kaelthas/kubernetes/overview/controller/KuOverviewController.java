package io.tiklab.kaelthas.kubernetes.overview.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.kubernetes.overview.service.KuOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * k8s概况页
 */
@RestController
@RequestMapping("/kuOverview")
public class KuOverviewController {

    @Autowired
    private KuOverviewService kuOverviewService;

    /**
     * 根据id查询k8s概况页展示的信息数据
     */
    @RequestMapping(value = "/findKuOverviewTotal", method = RequestMethod.POST)
    public Result<?> findKuOverviewTotal(@NotNull String kuId) {
        Map<String, Object> mapList = kuOverviewService.findKuOverviewTotal(kuId);
        return Result.ok(mapList);
    }
}
