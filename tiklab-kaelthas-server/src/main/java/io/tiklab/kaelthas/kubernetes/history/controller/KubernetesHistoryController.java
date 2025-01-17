package io.tiklab.kaelthas.kubernetes.history.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistoryQuery;
import io.tiklab.kaelthas.kubernetes.history.service.KubernetesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/KubernetesHistory")
public class KubernetesHistoryController {

    @Autowired
    KubernetesHistoryService kubernetesHistoryService;

    /**
     * 查询k8s中的监控图形
     */
    @RequestMapping(value = "/findKuGraphicsLine", method = RequestMethod.POST)
    public Result<List<List<KubernetesHistory>>> findKuGraphicsLine(@RequestBody KubernetesHistoryQuery kubernetesHistoryQuery) {
        List<List<KubernetesHistory>> list = kubernetesHistoryService.findKuGraphicsLine(kubernetesHistoryQuery);
        return Result.ok(list);
    }
}
