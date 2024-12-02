package io.tiklab.kaelthas.host.templateMonitor.service;

import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.host.hostTemplate.model.HostTemplate;
import io.tiklab.kaelthas.host.hostTemplate.service.HostTemplateService;
import io.tiklab.kaelthas.host.monitor.service.HostMonitorService;
import io.tiklab.kaelthas.host.templateMonitor.dao.TemplateMonitorDao;
import io.tiklab.kaelthas.host.templateMonitor.entity.TemplateMonitorEntity;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.templateMonitor.model.TemplateMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板下监控项
 */
@Service
public class TemplateMonitorServiceImpl implements TemplateMonitorService {

    @Autowired
    private TemplateMonitorDao templateMonitorDao;

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private HostMonitorService hostMonitorService;

    @Autowired
    private HostTemplateService hostTemplateService;

    //模板下创建监控项
    @Override
    public void createTemplateMonitor(HostMonitor hostMonitor) {

        try {
            //添加到监控项表当中
            String monitorId = hostMonitorService.createMonitor(hostMonitor);

            //查询有多少主机引用了模板,将添加的监控项一并添加到这些主机下
            List<HostTemplate> templateList = hostTemplateService.findTemplateByTemplateId(hostMonitor.getHostId());
            //查询出主机的ids
            List<String> stringList = templateList.stream().map(HostTemplate::getHostId).toList();

            //将从模板创建的监控项复制到引用模板的主机当中
            for (String string : stringList) {
                hostMonitor.setTemplateId(monitorId);
                hostMonitor.setHostId(string);
                hostMonitorService.createMonitor(hostMonitor);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //根据模板id查询模板下监控项
    @Override
    public List<TemplateMonitor> findIdsByTemplateId(String templateId) {

        QueryCondition queryCondition = QueryBuilders.createQuery(TemplateMonitorEntity.class)
                .eq("templateId", templateId)
                .get();
        List<TemplateMonitorEntity> templateMonitorEntityList = templateMonitorDao.findIdsByTemplateId(queryCondition);
        List<TemplateMonitor> templateMonitors = BeanMapper.mapList(templateMonitorEntityList, TemplateMonitor.class);
        joinTemplate.joinQuery(templateMonitors);
        return templateMonitors;
    }


    /**
     * 根据模板下监控项ids查询模板下监控项集合
     */
    @Override
    public List<TemplateMonitor> findList(List<String> monitorIds) {
        List<TemplateMonitorEntity> entityList = templateMonitorDao.findList(monitorIds);
        return BeanMapper.mapList(entityList, TemplateMonitor.class);
    }

    //删除模板下监控项
    @Override
    public void deleteTemplateMonitor(TemplateMonitor templateMonitor) {
        //删除模板中的监控项和主机当中的监控项
        HostMonitor hostMonitor = new HostMonitor();
        hostMonitor.setId(templateMonitor.getId());
        hostMonitorService.deleteCondition(hostMonitor);
    }

    //修改模板下的监控项
    @Override
    public void updateTemplateMonitor(HostMonitor hostMonitor) {

        //修改当前模板中的监控项
        hostMonitorService.updateMonitorByTemplate(hostMonitor);
        //修改主机中引用模板的监控项
        hostMonitorService.updateByMonitorId(hostMonitor);
    }

    //根据模板的ids和监控项item的ids查询模板下监控项
    @Override
    public List<TemplateMonitor> findMonitorByItemIds(List<String> monitorItemIds,List<String> templateIds) {
        Object[] array = monitorItemIds.toArray();
        Object[] idsArray = templateIds.toArray();
        QueryCondition queryCondition = QueryBuilders.createQuery(TemplateMonitorEntity.class)
                .in("templateId", idsArray)
                .in("monitorItemId", array)
                .get();
        List<TemplateMonitorEntity> templateMonitorEntities = templateMonitorDao.findMonitorByItemIds(queryCondition);
        return BeanMapper.mapList(templateMonitorEntities,TemplateMonitor.class);
    }

}