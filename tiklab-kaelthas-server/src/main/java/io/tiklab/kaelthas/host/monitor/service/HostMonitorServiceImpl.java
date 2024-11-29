package io.tiklab.kaelthas.host.monitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.host.graphicsMonitor.service.GraphicsMonitorService;
import io.tiklab.kaelthas.host.hostDynamic.model.HostDynamic;
import io.tiklab.kaelthas.host.hostDynamic.service.HostDynamicService;
import io.tiklab.kaelthas.host.monitor.entity.HostMonitorEntity;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.monitor.dao.HostMonitorDao;
import io.tiklab.kaelthas.host.monitorItem.entity.MonitorItemEntity;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;
import io.tiklab.kaelthas.host.monitorItem.service.MonitorItemService;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.host.triggerExpression.service.TriggerExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 主机下的监控项
 */
@Service
@Exporter
public class HostMonitorServiceImpl implements HostMonitorService {

    @Autowired
    private HostMonitorDao hostMonitorDao;

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private MonitorItemService monitorItemService;

    @Autowired
    private TriggerExpressionService triggerExpressionService;

    @Autowired
    private GraphicsMonitorService graphicsMonitorService;

    @Autowired
    HostDynamicService hostDynamicService;

    /**
     * 查询主机下监控项
     */
    @Override
    public Pagination<HostMonitor> findMonitorPage(HostMonitor query) {
        //查询主机下所有监控项
        //需要查询主机创建的监控项和引用模板的监控项,监控项表是一张表,所以需要先查询主机与模板的关联表,查询出模板的ids
        if (Objects.isNull(query.getSource())) {

            QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                    .eq("hostId", query.getHostId())
                    .like("name",query.getName())
                    .pagination(query.getPageParam())
                    .get();

            Pagination<HostMonitorEntity> pagination = hostMonitorDao.findMonitorPage(queryCondition);

            List<HostMonitor> hostMonitors = BeanMapper.mapList(pagination.getDataList(), HostMonitor.class);

            joinTemplate.joinQuery(hostMonitors);

            return PaginationBuilder.build(pagination,hostMonitors);
        }

        if (2 == query.getSource()) {

            //查询引用模板的监控项
            QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                    .eq("hostId", query.getHostId())
                    .like("name",query.getName())
                    .isNotNull("templateId")
                    .pagination(query.getPageParam())
                    .get();

            Pagination<HostMonitorEntity> pagination = hostMonitorDao.findMonitorPage(queryCondition);

            List<HostMonitor> hostMonitors = BeanMapper.mapList(pagination.getDataList(), HostMonitor.class);

            joinTemplate.joinQuery(hostMonitors);

            return PaginationBuilder.build(pagination,hostMonitors);
        }

        Pagination<HostMonitor> monitors = this.findMonitorList(query);

        joinTemplate.joinQuery(monitors.getDataList());

        return monitors;
    }

    /**
     * 查询主机下创建的监控项
     */
    @Override
    public Pagination<HostMonitor> findMonitorList(HostMonitor query) {

        //查询主机下创建的的监控项
        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .eq("hostId", query.getHostId())
                .like("name",query.getName())
                .isNull("templateId")
                .pagination(query.getPageParam())
                .get();

        Pagination<HostMonitorEntity> pagination = hostMonitorDao.findMonitorPage(queryCondition);

        List<HostMonitor> hostMonitors = BeanMapper.mapList(pagination.getDataList(), HostMonitor.class);

        joinTemplate.joinQuery(hostMonitors);

        return PaginationBuilder.build(pagination,hostMonitors);
    }

    /**
     * 查询出主机下所有的监控项
     */
    @Override
    public List<HostMonitor> findAllMonitor(HostMonitor hostMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .eq("hostId", hostMonitor.getHostId())
                .get();

        List<HostMonitorEntity> entityList = hostMonitorDao.findMonitorList(queryCondition);
        List<HostMonitor> hostMonitors = BeanMapper.mapList(entityList, HostMonitor.class);

        joinTemplate.joinQuery(hostMonitors);

        if (Objects.nonNull(hostMonitor.getReportType())) {
            hostMonitors = hostMonitors.stream()
                    .filter(o -> Objects.equals(o.getMonitorItem().getReportType(), 1)
                            || Objects.equals(o.getMonitorItem().getReportType(), 3)
                            || Objects.equals(o.getMonitorItem().getReportType(), 4)).toList();
        }

        return hostMonitors;
    }

    //根据监控类型查询MonitorItem中的监控项
    @Override
    public List<MonitorItem> findMonitorItemByName(String name) {

        List<MonitorItem> monitorList = monitorItemService.findMonitorItemByName(name);

        return monitorList;
    }

    //创建主机监控项
    @Override
    public String createMonitor(HostMonitor monitor) {

        monitor.setHostId(monitor.getHostId());
        monitor.setMonitorItemId(monitor.getMonitorItemId());

        List<HostMonitor> allMonitor = this.findAllMonitor(monitor);

        List<String> stringList = allMonitor.stream().map(HostMonitor::getExpression).toList();

        if (stringList.contains(monitor.getExpression())) {
            return null;
        }

        HostMonitorEntity monitorEntity = BeanMapper.map(monitor, HostMonitorEntity.class);

        try {
            String string = hostMonitorDao.createMonitor(monitorEntity);

            HostDynamic hostDynamic = new HostDynamic();
            hostDynamic.setHostId(monitor.getHostId());
            hostDynamic.setName("创建监控项———"+monitor.getName());
            hostDynamic.setTime(ConversionDateUtil.date(9));

            hostDynamicService.createHostDynamic(hostDynamic);

            return string;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据监控项id删除主机监控项
    @Override
    public void deleteMonitorById(String id) {

        try {

            //删除监控项当中的数据
            hostMonitorDao.deleteMonitorById(id);

            //删除图表关联信息
            graphicsMonitorService.deleteByMonitorId(id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //修改监控项
    @Override
    public void updateMonitor(HostMonitor monitor) {

        HostMonitor hostMonitor = new HostMonitor();
        hostMonitor.setHostId(monitor.getHostId());
        hostMonitor.setMonitorItemId(monitor.getMonitorItemId());

        /*List<HostMonitor> allMonitor = this.findAllMonitor(hostMonitor);

        List<String> stringList = new java.util.ArrayList<>(allMonitor.stream().map(HostMonitor::getMonitorItemId).toList());
        stringList.remove(monitor.getMonitorItemId());

        if (stringList.contains(monitor.getMonitorItemId())) {
            return "主机当中已存在相同类型的监控项了";
        }*/

        HostMonitorEntity monitorEntity = BeanMapper.map(monitor, HostMonitorEntity.class);
        try {
            hostMonitorDao.updateMonitor(monitorEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据监控项id查询监控项信息
     */
    @Override
    public HostMonitor findOneMonitor(String monitorId) {

        HostMonitorEntity monitorEntity = hostMonitorDao.findOneMonitor(monitorId);
        HostMonitor monitor = BeanMapper.map(monitorEntity, HostMonitor.class);
        joinTemplate.joinQuery(monitor);
        return monitor;

    }

    /**
     * 根据监控项的ids查询list数据
     */
    @Override
    public List<HostMonitor> findList(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
        List<HostMonitorEntity> monitorEntityList = hostMonitorDao.findList(idList);
        List<HostMonitor> hostMonitors = BeanMapper.mapList(monitorEntityList, HostMonitor.class);
        joinTemplate.joinQuery(hostMonitors);
        return hostMonitors;
    }

    /**
     * 根据主机ids查询出监控项list
     */
    @Override
    public List<HostMonitor> findHostMonitorListByHostIds(List<String> hostIds) {
        if (hostIds.isEmpty()) {
            return Collections.emptyList();
        }

        String[] strings = hostIds.toArray(String[]::new);

        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .in("hostId", strings)
                .get();

        List<HostMonitorEntity> monitorEntityList = hostMonitorDao.findHostMonitorListByHostIds(queryCondition);

        return BeanMapper.mapList(monitorEntityList, HostMonitor.class);
    }

    /**
     * 根据模板id分页查询监控项
     */
    @Override
    public Pagination<HostMonitor> findMonitorByTemplateId(HostMonitor hostMonitor) {

        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .eq("hostId", hostMonitor.getTemplateId())
                .pagination(hostMonitor.getPageParam())
                .get();

        Pagination<HostMonitorEntity> pagination = hostMonitorDao.findListByQuery(queryCondition);

        List<HostMonitor> monitorList = BeanMapper.mapList(pagination.getDataList(), HostMonitor.class);

        joinTemplate.joinQuery(monitorList);

        return PaginationBuilder.build(pagination,monitorList);
    }

    /**
     * 查询监控项字典项list
     */
    @Override
    public List<MonitorItem> findMonitorItemAll() {
        List<MonitorItemEntity> monitorItemAll = hostMonitorDao.findMonitorItemAll();
        return BeanMapper.mapList(monitorItemAll, MonitorItem.class);
    }

    /**
     * 根据监控项ids删除数据
     */
    @Override
    public void deleteByHostId(String hostId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(HostMonitorEntity.class)
                .eq("hostId", hostId)
                .get();
        hostMonitorDao.deleteByHostId(deleteCondition);
    }

    //根据主机id查询监控项list
    @Override
    public List<HostMonitor> findMonitorByHostId(String hostId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .eq("hostId", hostId)
                .get();
        List<HostMonitorEntity> entityList = hostMonitorDao.findMonitorByHostId(queryCondition);

        return BeanMapper.mapList(entityList, HostMonitor.class);
    }

    //根据监控项字典项ids和主机的id来查询监控项的list
    @Override
    public List<HostMonitor> findmonitorByMonitorItemIds(List<String> monitorItemIds,String hostId) {
        Object[] array = monitorItemIds.toArray();
        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .eq("hostId",hostId)
                .in("monitorItemId", array)
                .get();
        List<HostMonitorEntity> monitorEntities = hostMonitorDao.findMonitorByMonitorItemIds(queryCondition);
        return BeanMapper.mapList(monitorEntities,HostMonitor.class);
    }

    //查询主机下的监控项list
    @Override
    public List<HostMonitor> findByHostId(String id) {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .eq("hostId", id)
                .isNull("templateId")
                .get();
        List<HostMonitorEntity> hostMonitorEntityList = hostMonitorDao.findIdsByTemplateId(queryCondition);
        return BeanMapper.mapList(hostMonitorEntityList,HostMonitor.class);
    }

    //根据条件删除监控项
    @Override
    public void deleteCondition(HostMonitor hostMonitor) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(HostMonitorEntity.class)
                .eq("id", hostMonitor.getId())
                .eq("hostId", hostMonitor.getHostId())
                .eq("templateId", hostMonitor.getTemplateId())
                .eq("monitorItemId",hostMonitor.getMonitorItemId())
                .get();
        hostMonitorDao.deleteCondition(deleteCondition);
    }

    //修改主机引用模板的监控项
    @Override
    public void updateByMonitorId(HostMonitor hostMonitor) {
        hostMonitorDao.updateByMonitorId(hostMonitor);
    }

    //修改模板当中的监控项
    @Override
    public void updateMonitorByTemplate(HostMonitor hostMonitor) {
        HostMonitorEntity monitorEntity = BeanMapper.map(hostMonitor, HostMonitorEntity.class);
        hostMonitorDao.updateMonitorByTemplate(monitorEntity);
    }

    //根据模板的ids删除监控项
    @Override
    public void deleteMonitorByItemIds(List<String> stringList) {
        if (stringList.isEmpty()) {
            return;
        }
        Object[] array = stringList.toArray();

        DeleteCondition deleteCondition = DeleteBuilders.createDelete(HostMonitorEntity.class)
                .in("templateId", array)
                .get();
        hostMonitorDao.deleteMonitorByItemIds(deleteCondition);
    }

    /**
     * 根据模板或者主机的id查询监控项
     */
    @Override
    public List<HostMonitor> findCondition(HostMonitor hostMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .eq("hostId", hostMonitor.getHostId())
                .eq("templateId", hostMonitor.getTemplateId())
                .get();
        List<HostMonitorEntity> entityList = hostMonitorDao.findCondition(queryCondition);
        return BeanMapper.mapList(entityList,HostMonitor.class);
    }

    /**
     * 根据主机id或者模板的ids查询监控项list
     */
    @Override
    public List<HostMonitor> findConditionByHost(HostMonitor hostMonitor) {

        if (hostMonitor.getTemplateIds().isEmpty()) {
            return Collections.emptyList();
        }
        Object[] array = hostMonitor.getTemplateIds().toArray();

        QueryCondition queryCondition = QueryBuilders.createQuery(HostMonitorEntity.class)
                .eq("hostId", hostMonitor.getHostId())
                .in("templateId", array)
                .get();
        List<HostMonitorEntity> entityList = hostMonitorDao.findCondition(queryCondition);
        return BeanMapper.mapList(entityList,HostMonitor.class);
    }

    //查询监控项数量
    @Override
    public Long findMonitorAllNum() {
        return hostMonitorDao.findMonitorAllNum();
    }
}
