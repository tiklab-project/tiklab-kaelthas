package io.tiklab.kaelthas.kubernetes.kubernetesInfo.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.Kubernetes;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/kubernetes")
public class KubernetesController {

    @Autowired
    private KubernetesService kubernetesService;

    /**
     * 分页查询k8s
     */
    @RequestMapping(value = "/findKbInfoPage",method = RequestMethod.POST)
    public Result<Pagination<Kubernetes>> findKbInfoPage(@RequestBody Kubernetes kubernetes){
        return Result.ok(kubernetesService.findKbInfoPage(kubernetes));
    }

    /**
     * 添加k8s
     */
    @RequestMapping(value = "/createKbInfo",method = RequestMethod.POST)
    public Result<String> createKbInfo(@RequestBody Kubernetes kubernetes){
        return Result.ok(kubernetesService.createKbInfo(kubernetes));
    }

    /**
     * 修改k8s
     */
    @RequestMapping(value = "/updateKbInfo",method = RequestMethod.POST)
    public Result<?> updateKbInfo(@RequestBody Kubernetes kubernetes){
        kubernetesService.updateKbInfo(kubernetes);
        return Result.ok();
    }

    /**
     * 删除k8s
     */
    @RequestMapping(value = "/deleteKuInfo",method = RequestMethod.POST)
    public Result<?> deleteKuInfo(@NotNull String id){
        kubernetesService.deleteKuInfo(id);
        return Result.ok();
    }

    /**
     * 根据id查询k8s的信息
     */
    @RequestMapping(value = "/findKuInfoById",method = RequestMethod.POST)
    public Result<Kubernetes> findKuInfoById(@NotNull String id){
        Kubernetes kubernetes = kubernetesService.findKuInfoById(id);
        return Result.ok(kubernetes);
    }

    /**
     * 按照修改时间获取后四个k8s集群信息
     */
    @RequestMapping(value = "/findKuDropDown",method = RequestMethod.POST)
    public Result<?> findKuDropDown(){
        List<Kubernetes> kubernetesList = kubernetesService.findKuDropDown();
        return Result.ok(kubernetesList);
    }


    /**
     * 根据id查询k8s信息的概括（告警、监控项、触发器、图形）
     */
    @RequestMapping(value = "/findKuGeneralize", method = RequestMethod.POST)
    public Result<Kubernetes> findKuGeneralize(@NotNull String kuId) {
        Kubernetes kuOverview = kubernetesService.findKuGeneralize(kuId);
        return Result.ok(kuOverview);
    }

}
