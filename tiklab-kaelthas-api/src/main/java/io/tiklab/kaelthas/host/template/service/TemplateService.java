package io.tiklab.kaelthas.host.template.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.hostTemplate.model.HostTemplate;
import io.tiklab.kaelthas.host.template.model.Template;
import io.tiklab.kaelthas.host.templateMonitor.model.TemplateMonitor;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.monitor.model.MonitorQuery;

import java.util.List;

/**
 * 模板业务
 */
@JoinProvider(model = Template.class)
public interface TemplateService {

    /**
     * 查询所有模板
     */
    @FindAll
    List<Template> findTemplateAll();

    /**
     * 根据查询条件查询出主机当中的模板
     */
    Pagination<Template> findTemplate(MonitorQuery query);

    /**
     * 添加模板到主机当中
     */
    void addTemplate(HostTemplate hostTemplate);

    /**
     * 移除主机当中的模板
     */
    void removeTemplate(HostTemplate hostTemplate);

    /**
     * 创建模板
     */
    String createTemplate(Template template);

    /**
     * 删除模板
     */
    void deleteTemplate(String id);

    /**
     * 修改模板
     */
    void updateTemplate(Template template);

    //创建模板下的监控项
    void createTemplateMonitor(HostMonitor hostMonitor);

    //删除模板下的监控项
    void deleteTemplateMonitor(TemplateMonitor templateMonitor);

    //修改模板下的监控项
    void updateTemplateMonitor(HostMonitor hostMonitor);

    //查询模板数量
    Long findTemplateAllNum();
}
