package io.tiklab.kaelthas.host.template.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.graphics.service.GraphicsService;
import io.tiklab.kaelthas.host.graphicsMonitor.service.GraphicsMonitorService;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.monitor.service.HostMonitorService;
import io.tiklab.kaelthas.host.template.entity.TemplateEntity;
import io.tiklab.kaelthas.host.template.dao.TemplateDao;
import io.tiklab.kaelthas.host.templateMonitor.model.TemplateMonitor;
import io.tiklab.kaelthas.host.templateMonitor.service.TemplateMonitorService;
import io.tiklab.kaelthas.host.host.model.HostTemplate;
import io.tiklab.kaelthas.host.host.service.HostTemplateService;
import io.tiklab.kaelthas.host.monitor.model.MonitorQuery;
import io.tiklab.kaelthas.host.triggerExpression.service.TriggerExpressionService;
import io.tiklab.kaelthas.host.trigger.service.TriggerService;
import io.tiklab.kaelthas.host.template.model.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Exporter
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private HostTemplateService hostTemplateService;

    @Autowired
    private TemplateMonitorService templateMonitorService;

    @Autowired
    private TriggerService triggerService;

    @Autowired
    private TriggerExpressionService triggerExpressionService;

    @Autowired
    private GraphicsService graphicsService;

    @Autowired
    private GraphicsMonitorService graphicsMonitorService;

    @Autowired
    private HostMonitorService hostMonitorService;


    @Override
    public List<Template> findTemplateAll() {
        QueryCondition queryCondition = QueryBuilders.createQuery(TemplateEntity.class)
                .eq("isOpen", 1)
                .get();

        List<TemplateEntity> templateAll = templateDao.getTemplateAll(queryCondition);

        return BeanMapper.mapList(templateAll, Template.class);
    }


    /**
     * 根据查询条件查询出主机当中的模板
     */
    @Override
    public Pagination<Template> findTemplate(MonitorQuery query) {

        if (StringUtils.isNotBlank(query.getHostId())) {

            //查询主机和模版关联表的信息,查询出模板的id
            List<HostTemplate> templateList = hostTemplateService.findHostTemplateListByCondition(query);
            if (templateList.isEmpty()) {
                return new Pagination<>();
            } else {
                Object[] array = templateList.stream().map(HostTemplate::getTemplateId).toArray();
                QueryCondition queryCondition = QueryBuilders.createQuery(TemplateEntity.class)
                        .like("name", query.getName())
                        .in("id", array)
                        .pagination(query.getPageParam())
                        .get();
                Pagination<TemplateEntity> entityPagination = templateDao.findTemplateForCondition(queryCondition);

                List<Template> templates = BeanMapper.mapList(entityPagination.getDataList(), Template.class);

                if (!templates.isEmpty()) {
                    for (Template template : templates) {
                        List<HostMonitor> idsByTemplateId = hostMonitorService.findByHostId(template.getId());
                        template.setMonitorNum(idsByTemplateId.size());
                    }
                }

                return PaginationBuilder.build(entityPagination, templates);
            }
        } else {
            QueryCondition queryCondition = QueryBuilders.createQuery(TemplateEntity.class)
                    .like("name", query.getName())
                    .pagination(query.getPageParam())
                    .get();
            Pagination<TemplateEntity> entityPagination = templateDao.findTemplateForCondition(queryCondition);

            List<Template> templates = BeanMapper.mapList(entityPagination.getDataList(), Template.class);

            if (!templates.isEmpty()) {
                for (Template template : templates) {
                    List<HostMonitor> idsByTemplateId = hostMonitorService.findByHostId(template.getId());
                    template.setMonitorNum(idsByTemplateId.size());
                }
            }

            return PaginationBuilder.build(entityPagination, templates);
        }
    }

    @Override
    public void addTemplate(HostTemplate hostTemplate) {
        try {
            hostTemplateService.addTemplate(hostTemplate);

            //将模板当中的监控项复制到主机当中
            //查询模板下的监控项信息
            //查询模板下所有的监控项id
            List<HostMonitor> templateMonitors = hostMonitorService.findByHostId(hostTemplate.getTemplateId());

            for (HostMonitor monitor : templateMonitors) {
                monitor.setTemplateId(monitor.getId());
                monitor.setHostId(hostTemplate.getHostId());
                hostMonitorService.createMonitor(monitor);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeTemplate(HostTemplate hostTemplate) {

        try {
            //删除主机中引用的监控项
            //hostId,templateId,查询模板中监控项ids,然后delete from host_monitor where host_id = 1001 and template_id in ('2001','2002');

            HostMonitor hostMonitor = new HostMonitor();
            hostMonitor.setHostId(hostTemplate.getTemplateId());

            //根据模板id查询出模板下监控项的ids
            List<HostMonitor> monitorByTemplateId = hostMonitorService.findCondition(hostMonitor);
            List<String> stringList = monitorByTemplateId.stream().map(HostMonitor::getId).toList();
            //根据模板下的ids和主机id查询出主机下引用的监控项ids,将主机下的ids进行删除
            HostMonitor hostMonitor2 = new HostMonitor();
            hostMonitor2.setHostId(hostTemplate.getHostId());
            hostMonitor2.setTemplateIds(stringList);
            List<HostMonitor> condition = hostMonitorService.findConditionByHost(hostMonitor2);
            String[] strings = condition.stream().map(HostMonitor::getId).toList().toArray(new String[0]);
            //删除图表与监控项关联信息
            graphicsMonitorService.deleteByMonitorIds(strings);

            String deleteSql = "";
            //拼接SQL
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < stringList.size(); i++) {
                builder.append("'").append(stringList.get(i)).append("'");
                if (i != stringList.size() - 1) {
                    builder.append(",");
                }
            }

            deleteSql = "("+builder+")";

            for (String string : stringList) {
                HostMonitor hostMonitor1 = new HostMonitor();
                hostMonitor1.setHostId(hostTemplate.getHostId());
                hostMonitor1.setTemplateId(string);
                hostMonitorService.deleteCondition(hostMonitor1);
            }

            //删除关联表信息
            hostTemplateService.removeTemplate(hostTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String createTemplate(Template template) {
        TemplateEntity templateEntity = BeanMapper.map(template, TemplateEntity.class);
        return templateDao.createTemplate(templateEntity);
    }

    //删除模板
    @Override
    public void deleteTemplate(String id) {

        try {
            //根据模板id删除
            templateDao.deleteTemplate(id);

            //删除模板与主机的关联表
            HostTemplate hostTemplate = new HostTemplate();
            hostTemplate.setTemplateId(id);
            hostTemplateService.deleteByHostId(hostTemplate);

            //删除引用模板的监控项
            //查询模板下的监控项信息
            List<HostMonitor> hostMonitorList = hostMonitorService.findByHostId(id);
            List<String> stringList = hostMonitorList.stream().map(HostMonitor::getId).toList();
            hostMonitorService.deleteMonitorByItemIds(stringList);

            //删除模板下的监控项
            HostMonitor hostMonitor1 = new HostMonitor();
            hostMonitor1.setHostId(id);
            hostMonitorService.deleteCondition(hostMonitor1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //修改模板
    @Override
    public void updateTemplate(Template template) {
        TemplateEntity templateEntity = BeanMapper.map(template, TemplateEntity.class);
        templateDao.updateTemplate(templateEntity);
    }

    @Override
    public Map<String, Integer> findTemplateNum() {
        Map<String, Integer> map = new HashMap<>();
        Integer tempNum = templateDao.findTemplateNum();
        Integer monitorNum = templateMonitorService.findMonitorNumber();
        map.put("tempNum", tempNum);
        map.put("monitorNum", monitorNum);
        return map;
    }

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

    @Override
    public void deleteTemplateMonitor(TemplateMonitor templateMonitor) {
        //删除模板中的监控项和主机当中的监控项
        HostMonitor hostMonitor = new HostMonitor();
        hostMonitor.setId(templateMonitor.getId());
        hostMonitorService.deleteCondition(hostMonitor);

        //删除监控项关联的图表,触发器
        HostMonitor hostMonitor1 = new HostMonitor();
        hostMonitor1.setTemplateId(templateMonitor.getId());
        hostMonitorService.deleteCondition(hostMonitor1);

        //删除图形和监控项的关联表
        List<HostMonitor> hostMonitorList = hostMonitorService.findCondition(hostMonitor1);
        //求出被主机引用的监控项ids
        String[] strings = hostMonitorList.stream().map(HostMonitor::getId).toList().toArray(new String[0]);
        graphicsMonitorService.deleteByMonitorIds(strings);
    }

    @Override
    public void updateTemplateMonitor(HostMonitor hostMonitor) {

        //修改当前模板中的监控项
        hostMonitorService.updateMonitorByTemplate(hostMonitor);
        //修改主机中引用模板的监控项
        hostMonitorService.updateByMonitorId(hostMonitor);
    }

    @Override
    public Long findTemplateAllNum() {
        return templateDao.findTemplateAllNum();
    }
}
