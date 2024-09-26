package io.thoughtware.kaelthas.host.host.service;

import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.host.host.dao.HostTemplateDao;
import io.thoughtware.kaelthas.host.host.entity.HostTemplateEntity;
import io.thoughtware.kaelthas.host.host.model.HostTemplate;
import io.thoughtware.kaelthas.host.monitor.model.MonitorQuery;
import io.thoughtware.kaelthas.host.monitor.service.HostMonitorService;
import io.thoughtware.kaelthas.host.templateMonitor.service.TemplateMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HostTemplateServiceImpl implements HostTemplateService {

    @Autowired
    private HostTemplateDao hostTemplateDao;

    @Autowired
    private HostMonitorService hostMonitorService;

    @Autowired
    private TemplateMonitorService templateMonitorService;

    /**
     * 添加模板到主机当中,将模板id和主机id添加到中间表中
     */
    @Override
    public String addTemplate(HostTemplate hostTemplate) {
        try {
            HostTemplateEntity hostTemplateEntity = BeanMapper.map(hostTemplate, HostTemplateEntity.class);
            return hostTemplateDao.save(hostTemplateEntity);
        } catch (Exception e) {
            throw new RuntimeException("添加模板失败", e);
        }

    }

    @Override
    public List<HostTemplate> findHostTemplateList(List<String> ids) {
        if (ids.size() == 0) {
            return Collections.emptyList();
        }

        List<HostTemplateEntity> hostTemplates = hostTemplateDao.findHostTemplateList(ids);

        return BeanMapper.mapList(hostTemplates, HostTemplate.class);
    }

    /**
     * 查询主机和模板中间表所有信息
     */
    @Override
    public List<HostTemplate> findHostTemplateAll() {
        List<HostTemplateEntity> hostTemplateAll = hostTemplateDao.findHostTemplateAll();
        return BeanMapper.mapList(hostTemplateAll, HostTemplate.class);
    }

    /**
     * 根据主机id查询出主机与模板关联表中模板的id
     */
    @Override
    public List<HostTemplate> findTemplateForHost(String hostId) {

        QueryCondition queryCondition = QueryBuilders.createQuery(HostTemplateEntity.class)
                .eq("hostId", hostId)
                .get();

        List<HostTemplateEntity> hostTemplateEntities = hostTemplateDao.findTemplateForHost(queryCondition);

        return BeanMapper.mapList(hostTemplateEntities, HostTemplate.class);
    }

    @Override
    public void deleteByHostId(HostTemplate hostTemplate) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(HostTemplateEntity.class)
                .eq("hostId", hostTemplate.getHostId())
                .eq("templateId", hostTemplate.getTemplateId())
                .get();
        hostTemplateDao.deleteByHostId(deleteCondition);
    }

    @Override
    public List<HostTemplate> findTemplateByTemplateId(String templateId) {

        QueryCondition queryCondition = QueryBuilders.createQuery(HostTemplateEntity.class)
                .eq("templateId", templateId)
                .get();
        List<HostTemplateEntity> entityList = hostTemplateDao.findTemplateByTemplateId(queryCondition);

        return BeanMapper.mapList(entityList, HostTemplate.class);
    }

    /**
     * 根据主机ids查询模板ids
     */
    @Override
    public List<HostTemplate> findHostTemplateListByHostIds(List<String> hostIds) {

        String[] strings = hostIds.toArray(String[]::new);

        QueryCondition queryCondition = QueryBuilders.createQuery(HostTemplateEntity.class)
                .in("hostId", strings)
                .get();

        List<HostTemplateEntity> templateEntityList = hostTemplateDao.findHostTemplateListByHostIds(queryCondition);
        return BeanMapper.mapList(templateEntityList, HostTemplate.class);
    }

    @Override
    public List<HostTemplate> findHostTemplateListByCondition(MonitorQuery query) {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostTemplateEntity.class)
                .eq("hostId", query.getHostId())
                .get();
        List<HostTemplateEntity> entityList = hostTemplateDao.findHostTemplateListByCondition(queryCondition);
        return BeanMapper.mapList(entityList, HostTemplate.class);
    }

    @Override
    public void removeTemplate(HostTemplate hostTemplate) {
        HostTemplateEntity hostTemplateEntity = BeanMapper.map(hostTemplate, HostTemplateEntity.class);
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(HostTemplateEntity.class)
                .eq("hostId", hostTemplateEntity.getHostId())
                .eq("templateId", hostTemplateEntity.getTemplateId())
                .get();

        hostTemplateDao.removeTemplate(deleteCondition);
    }
}
