package io.thoughtware.kaelthas.host.template.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.host.host.model.HostTemplate;
import io.thoughtware.kaelthas.host.template.model.Template;
import io.thoughtware.kaelthas.host.templateMonitor.model.TemplateMonitor;
import io.thoughtware.kaelthas.host.monitor.model.HostMonitor;
import io.thoughtware.kaelthas.host.monitor.model.MonitorQuery;

import java.util.List;
import java.util.Map;

@JoinProvider(model = Template.class)
public interface TemplateService {

    @FindAll
    List<Template> findTemplateAll();

    Pagination<Template> findTemplate(MonitorQuery query);

    void addTemplate(HostTemplate hostTemplate);

    void removeTemplate(HostTemplate hostTemplate);

    String createTemplate(Template template);

    void deleteTemplate(String id);

    void updateTemplate(Template template);

    Map<String, Integer> findTemplateNum();

    //创建模板下的监控项
    void createTemplateMonitor(HostMonitor hostMonitor);

    //删除模板下的监控项
    void deleteTemplateMonitor(TemplateMonitor templateMonitor);

    //修改模板下的监控项
    void updateTemplateMonitor(HostMonitor hostMonitor);

    //查询模板数量
    Long findTemplateAllNum();
}
