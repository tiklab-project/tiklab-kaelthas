package io.tiklab.kaelthas.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.kaelthas.host.monitor.model.MonitorQuery;
import io.tiklab.kaelthas.host.template.model.Template;

import java.util.Map;

/**
 * 设置中的首页
 */
public interface SettingService {

    /**
     * 设置中的首页查询信息
     */
    Map<String,Object> count();

    /**
     * 主机组分页查询
     */
    Pagination<HostGroup> findHostGroupPage(HostGroup hostGroup);

    /**
     * 根据查询条件查询出主机当中的模板
     */
    Pagination<Template> findTemplate(MonitorQuery query);

    /**
     * 创建模板
     */
    String createTemplate(Template template);

    /**
     * 修改模板
     */
    void updateTemplate(Template template);

    /**
     * 删除模板
     */
    void deleteTemplate(String id);

    void createHostGroup(HostGroup hostGroup);

    /**
     * 删除主机组
     */
    void deleteHostGroup(String id);

    /**
     * 修改主机组
     */
    void updateHostGroup(HostGroup hostGroup);
}
