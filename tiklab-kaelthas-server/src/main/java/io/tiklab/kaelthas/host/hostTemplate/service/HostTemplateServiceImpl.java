package io.tiklab.kaelthas.host.hostTemplate.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.hostTemplate.dao.HostTemplateDao;
import io.tiklab.kaelthas.host.hostTemplate.entity.HostTemplateEntity;
import io.tiklab.kaelthas.host.hostTemplate.model.HostTemplate;
import io.tiklab.kaelthas.host.monitor.model.MonitorQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 主机与模板的关联表
 */
@Service
public class HostTemplateServiceImpl implements HostTemplateService {

    @Autowired
    private HostTemplateDao hostTemplateDao;

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

    /**
     * findList方法,根据ids查询list数据
     */
    @Override
    public List<HostTemplate> findHostTemplateList(List<String> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<HostTemplateEntity> hostTemplates = hostTemplateDao.findHostTemplateList(ids);

        return BeanMapper.mapList(hostTemplates, HostTemplate.class);
    }

    /**
     * 查询主机和模板中间表所有信息findAll方法
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

    /**
     * 根据主机或者模板id删除数据
     */
    @Override
    public void deleteByHostId(HostTemplate hostTemplate) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(HostTemplateEntity.class)
                .eq("hostId", hostTemplate.getHostId())
                .eq("templateId", hostTemplate.getTemplateId())
                .get();
        hostTemplateDao.deleteByHostId(deleteCondition);
    }

    /**
     * 根据模板id查询list数据
     */
    @Override
    public List<HostTemplate> findTemplateByTemplateId(String templateId) {

        QueryCondition queryCondition = QueryBuilders.createQuery(HostTemplateEntity.class)
                .eq("templateId", templateId)
                .get();
        List<HostTemplateEntity> entityList = hostTemplateDao.findTemplateByTemplateId(queryCondition);

        return BeanMapper.mapList(entityList, HostTemplate.class);
    }

    /**
     * 根据主机id查询list数据
     */
    @Override
    public List<HostTemplate> findHostTemplateListByCondition(MonitorQuery query) {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostTemplateEntity.class)
                .eq("hostId", query.getHostId())
                .get();
        List<HostTemplateEntity> entityList = hostTemplateDao.findHostTemplateListByCondition(queryCondition);
        return BeanMapper.mapList(entityList, HostTemplate.class);
    }

    /**
     * 根据主机id或者模板id删除数据
     */
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
