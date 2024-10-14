package io.tiklab.kaelthas.kubernetes.kubernetesItem.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.kubernetes.kubernetesItem.model.KubernetesItem;
import io.tiklab.kaelthas.kubernetes.kubernetesItem.service.KubernetesItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kuItem")
public class KubernetesItemController {

    @Autowired
    private KubernetesItemService kubernetesItemService;

    /**
     * 根据监控项类型查询监控项字典表
     */
    @RequestMapping(value = "/findItemList",method = RequestMethod.POST)
    public Result<?> findItemList(@RequestBody KubernetesItem kubernetesItem){
        List<KubernetesItem> itemList = kubernetesItemService.findItemList(kubernetesItem);
        return Result.ok(itemList);
    }
}
