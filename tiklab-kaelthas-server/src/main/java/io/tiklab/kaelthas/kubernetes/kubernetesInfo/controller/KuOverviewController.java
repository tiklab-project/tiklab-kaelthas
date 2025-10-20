package io.tiklab.kaelthas.kubernetes.kubernetesInfo.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.KuOverview;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.service.KuOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 网络监控概况页
 */
@RestController
@RequestMapping("/kuOverview")
public class KuOverviewController {

    @Autowired
    KuOverviewService kuOverviewService;

    /**
     * 查询K8s概况
     */
    @RequestMapping(value = "/findKuByKuId",method = RequestMethod.POST)
    public Result<List<KuOverview>> findKuByKuId(@NotNull String kuId){
        List<KuOverview> kuOverview = kuOverviewService.findKuByKuId(kuId);
        return Result.ok(kuOverview);
    }

    /**
     * 根据id查询k8s服务器的概况页展示的信息数据
     */
    @RequestMapping(value = "/findKuOverviewTotal", method = RequestMethod.POST)
    public Result<?> findKuOverviewTotal(@NotNull String kuId) {
        Map<String, Object> mapList = kuOverviewService.findKuOverviewTotal(kuId);
        return Result.ok(mapList);
    }



}
