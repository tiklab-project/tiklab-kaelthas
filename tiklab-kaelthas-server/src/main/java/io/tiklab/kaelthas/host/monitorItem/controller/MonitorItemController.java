package io.tiklab.kaelthas.host.monitorItem.controller;

import io.tiklab.kaelthas.host.monitorItem.service.MonitorItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitorItem")
public class MonitorItemController {

    @Autowired
    private MonitorItemService monitorItemService;


}
