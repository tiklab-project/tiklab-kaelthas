package io.tiklab.kaelthas.kubernetes.kubernetesGraphics.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;
import io.tiklab.kaelthas.kubernetes.kubernetesGraphics.service.KuGraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/kuGraphics")
public class KuGraphicsController {

    @Autowired
    private KuGraphicsService kuGraphicsService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "/findKuGraphicsPage",method = RequestMethod.POST)
    public Result<?> findKuGraphicsPage(@RequestBody KuGraphics kuGraphics){
        Pagination<KuGraphics> pagination = kuGraphicsService.findKuGraphicsPage(kuGraphics);
        return Result.ok(pagination);
    }

    /**
     * 添加图形
     */
    @RequestMapping(value = "/createKuGraphics",method = RequestMethod.POST)
    public Result<?> createKuGraphics(@RequestBody KuGraphics kuGraphics){
        String string = kuGraphicsService.createKuGraphics(kuGraphics);
        return Result.ok(string);
    }

    /**
     * 删除图形
     */
    @RequestMapping(value = "/deleteKuGraphics",method = RequestMethod.POST)
    public Result<?> deleteKuGraphics(@NotNull String id){
        kuGraphicsService.deleteKuGraphics(id);
        return Result.ok();
    }

    /**
     * 修改图形
     */
    @RequestMapping(value = "/updateKuGraphics",method = RequestMethod.POST)
    public Result<?> updateKuGraphics(@RequestBody KuGraphics kuGraphics){
        kuGraphicsService.updateKuGraphics(kuGraphics);
        return Result.ok();
    }
}
