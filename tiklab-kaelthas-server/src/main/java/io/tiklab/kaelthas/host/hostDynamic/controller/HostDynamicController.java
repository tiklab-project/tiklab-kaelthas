package io.tiklab.kaelthas.host.hostDynamic.controller;


import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;
import io.tiklab.kaelthas.host.hostDynamic.service.HostDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hostDynamic")
public class HostDynamicController {

    @Autowired
    HostDynamicService hostDynamicService;

    /**
     * 根据主机的id进行查询主机的动态信息
     *
     * @return
     */
    @RequestMapping(value = "/findHostDynamicPage",method = RequestMethod.POST)
    public Result<Pagination<HostDynamic>> findPage(@RequestBody HostDynamic hostDynamic){
        Pagination<HostDynamic> pagination = hostDynamicService.findPage(hostDynamic);
        return Result.ok(pagination);
    }


    @RequestMapping(value = "/createHostDynamic", method = RequestMethod.POST)
    public void createHostDynamic(@RequestBody HostDynamic hostDynamic) {
        hostDynamicService.createHostDynamic(hostDynamic);
    }
}
