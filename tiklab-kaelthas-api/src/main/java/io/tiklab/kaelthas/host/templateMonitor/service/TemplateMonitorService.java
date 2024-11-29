package io.tiklab.kaelthas.host.templateMonitor.service;

import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.templateMonitor.model.TemplateMonitor;

import java.util.List;

@JoinProvider(model = TemplateMonitor.class)
public interface TemplateMonitorService {

    void createTemplateMonitor(HostMonitor hostMonitor);

    List<TemplateMonitor> findIdsByTemplateId(String templateId);

    @FindList
    List<TemplateMonitor> findList(List<String> monitorIds);

    void deleteTemplateMonitor(TemplateMonitor templateMonitor);

    void updateTemplateMonitor(HostMonitor hostMonitor);

    List<TemplateMonitor> findMonitorByItemIds(List<String> monitorItemIds,List<String> templateIds);


}
