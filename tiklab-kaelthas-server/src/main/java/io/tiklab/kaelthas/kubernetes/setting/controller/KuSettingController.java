package io.tiklab.kaelthas.kubernetes.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.kaelthas.kubernetes.setting.service.KuSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * k8s的设置
 */
@RestController
@RequestMapping("/KuSetting")
public class KuSettingController {

    @Autowired
    private KuSettingService kuSettingService;

    /**
     * 修改k8s
     */
    @RequestMapping(value = "/updateKbInfo",method = RequestMethod.POST)
    public Result<?> updateKbInfo(@RequestBody Kubernetes kubernetes){
        kuSettingService.updateKbInfo(kubernetes);
        return Result.ok();
    }

    /**
     * 删除k8s
     */
    @RequestMapping(value = "/deleteKuInfo",method = RequestMethod.POST)
    public Result<?> deleteKuInfo(@NotNull String id){
        kuSettingService.deleteKuInfo(id);
        return Result.ok();
    }

    /**
     * 根据id查询k8s的信息
     */
    @RequestMapping(value = "/findKuInfoById",method = RequestMethod.POST)
    public Result<Kubernetes> findKuInfoById(@NotNull String id){
        Kubernetes kubernetes = kuSettingService.findKuInfoById(id);
        return Result.ok(kubernetes);
    }
}
