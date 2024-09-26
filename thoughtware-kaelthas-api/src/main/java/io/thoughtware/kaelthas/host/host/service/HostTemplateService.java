package io.thoughtware.kaelthas.host.host.service;

import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.kaelthas.host.host.model.HostTemplate;
import io.thoughtware.kaelthas.host.monitor.model.MonitorQuery;

import java.util.List;

@JoinProvider(model = HostTemplate.class)
public interface HostTemplateService {
    String addTemplate(HostTemplate hostTemplate);

    @FindList
    List<HostTemplate> findHostTemplateList(List<String> ids);

    @FindAll
    List<HostTemplate> findHostTemplateAll();

    List<HostTemplate> findTemplateForHost(String hostId);

    void deleteByHostId(HostTemplate hostTemplate);

    List<HostTemplate> findTemplateByTemplateId(String templateId);

    List<HostTemplate> findHostTemplateListByHostIds(List<String> hostIds);

    List<HostTemplate> findHostTemplateListByCondition(MonitorQuery query);

    void removeTemplate(HostTemplate hostTemplate);
}
