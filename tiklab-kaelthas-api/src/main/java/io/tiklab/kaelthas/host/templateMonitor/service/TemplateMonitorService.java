package io.tiklab.kaelthas.host.templateMonitor.service;

import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.templateMonitor.model.TemplateMonitor;

import java.util.List;

/**
 * 模板下监控项
 */
@JoinProvider(model = TemplateMonitor.class)
public interface TemplateMonitorService {

    //模板下创建监控项
    void createTemplateMonitor(HostMonitor hostMonitor);

    //根据模板id查询模板下监控项
    List<TemplateMonitor> findIdsByTemplateId(String templateId);

    /**
     * 根据模板下监控项ids查询模板下监控项集合
     */
    @FindList
    List<TemplateMonitor> findList(List<String> monitorIds);

    /**
     * 删除模板下监控项
     */
    void deleteTemplateMonitor(TemplateMonitor templateMonitor);

    /**
     * 修改模板下的监控项
     */
    void updateTemplateMonitor(HostMonitor hostMonitor);
}
