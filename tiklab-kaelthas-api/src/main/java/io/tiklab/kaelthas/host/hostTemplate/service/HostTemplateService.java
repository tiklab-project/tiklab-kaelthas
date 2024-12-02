package io.tiklab.kaelthas.host.hostTemplate.service;

import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.JoinProvider;
import io.tiklab.kaelthas.host.hostTemplate.model.HostTemplate;
import io.tiklab.kaelthas.host.monitor.model.MonitorQuery;

import java.util.List;

/**
 * 主机与模板的关联表
 */
@JoinProvider(model = HostTemplate.class)
public interface HostTemplateService {

    /**
     * 添加模板到主机当中,将模板id和主机id添加到中间表中
     */
    String addTemplate(HostTemplate hostTemplate);

    /**
     * findList方法,根据ids查询list数据
     */
    @FindList
    List<HostTemplate> findHostTemplateList(List<String> ids);

    /**
     * 查询主机和模板中间表所有信息findAll方法
     */
    @FindAll
    List<HostTemplate> findHostTemplateAll();

    /**
     * 根据主机或者模板id删除数据
     */
    void deleteByHostId(HostTemplate hostTemplate);

    /**
     * 根据模板id查询list数据
     */
    List<HostTemplate> findTemplateByTemplateId(String templateId);

    /**
     * 根据主机id查询list数据
     */
    List<HostTemplate> findHostTemplateListByCondition(MonitorQuery query);

    /**
     * 根据主机id或者模板id删除数据
     */
    void removeTemplate(HostTemplate hostTemplate);
}
