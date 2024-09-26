package io.thoughtware.kaelthas.host.templateMonitor.service;

import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.host.monitor.model.HostMonitor;
import io.thoughtware.kaelthas.host.templateMonitor.model.TemplateMonitor;

import java.util.List;

@JoinProvider(model = TemplateMonitor.class)
public interface TemplateMonitorService {
    void deleteByMonitorId(String id);

    void createTemplateMonitor(HostMonitor hostMonitor);

    List<TemplateMonitor> findIdsByTemplateId(String templateId);

    void deleteByTemplateId(String templateId);

    @FindList
    List<TemplateMonitor> findList(List<String> monitorIds);

    void deleteTemplateMonitor(TemplateMonitor templateMonitor);

    void updateTemplateMonitor(HostMonitor hostMonitor);

    List<TemplateMonitor> findTemplateMonitorByIds(String[] templateIds, HostMonitor hostMonitor);

    TemplateMonitor findOneMonitor(String id);

    void deleteMonitorByIds(String[] ids);

    List<TemplateMonitor> findMonitorByItemIds(List<String> monitorItemIds,List<String> templateIds);

    Integer findMonitorNumber();

}
