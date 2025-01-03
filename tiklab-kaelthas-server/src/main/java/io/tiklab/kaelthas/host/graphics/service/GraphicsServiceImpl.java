package io.tiklab.kaelthas.host.graphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.graphics.dao.GraphicsDao;
import io.tiklab.kaelthas.host.graphics.entity.GraphicsEntity;
import io.tiklab.kaelthas.host.graphics.model.Graphics;
import io.tiklab.kaelthas.host.graphicsMonitor.model.GraphicsMonitor;
import io.tiklab.kaelthas.host.graphicsMonitor.service.GraphicsMonitorService;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.monitor.service.HostMonitorService;
import io.tiklab.kaelthas.host.monitorItem.model.MonitorItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Exporter
public class GraphicsServiceImpl implements GraphicsService {

    @Autowired
    private GraphicsDao graphicsDao;

    @Autowired
    private GraphicsMonitorService graphicsMonitorService;

    @Autowired
    HostMonitorService hostMonitorService;

    @Override
    public Pagination<Graphics> findGraphicsPage(Graphics graphics) {
        return graphicsDao.findGraphics(graphics);
    }

    @Override
    public String createGraphics(Graphics graphics) {

        try {
            //将要添加到图表的监控项查询出来，如果数据不是同一种类型的话则添加不了
            List<HostMonitor> list = hostMonitorService.findList(graphics.getMonitorIds());
            //将类型遍历出来，如果是只有一种类型说明可以添加
            Set<Integer> collect = list.stream().map(HostMonitor::getMonitorItem).map(MonitorItem::getReportType).collect(Collectors.toSet());

            //创建之前查询图表当中是否存在同名的图表,如果存在则提示已经存在了
            List<Graphics> graphicsList = this.findGraphicsList(graphics);

            if (collect.size() >1 || !graphicsList.isEmpty()){
                return null;
            }

            GraphicsEntity graphicsEntity = BeanMapper.map(graphics, GraphicsEntity.class);

            String graphicsId = graphicsDao.createGraphics(graphicsEntity);

            //插入图形与监控项关联表中
            for (String monitorId : graphics.getMonitorIds()) {
                GraphicsMonitor graphicsMonitor = new GraphicsMonitor();
                graphicsMonitor.setGraphicsId(graphicsId);
                graphicsMonitor.setMonitorId(monitorId);
                graphicsMonitorService.insertByGraphics(graphicsMonitor);
            }
            return graphicsId;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGraphics(String id) {
        try {
            graphicsDao.deleteGraphics(id);
            //根据图表的id删除关联表当中的数据
            graphicsMonitorService.deleteByGraphicsId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGraphics(Graphics graphics) {
        //将要添加到图表的监控项查询出来，如果数据不是同一种类型的话则添加不了
        List<HostMonitor> list = hostMonitorService.findList(graphics.getMonitorIds());
        //将类型遍历出来，如果是只有一种类型说明可以添加
        Set<Integer> collect = list.stream().map(HostMonitor::getMonitorItem).map(MonitorItem::getReportType).collect(Collectors.toSet());
        if (collect.size() >1){
            return;
        }

        if (graphics.getMonitorIds() != null && !graphics.getMonitorIds().isEmpty()) {
            //删除中间表后添加新的数据
            graphicsMonitorService.deleteByGraphicsId(graphics.getId());

            //添加新数据
            for (String monitorId : graphics.getMonitorIds()) {
                GraphicsMonitor graphicsMonitor = new GraphicsMonitor();
                graphicsMonitor.setGraphicsId(graphics.getId());
                graphicsMonitor.setMonitorId(monitorId);
                graphicsMonitorService.insertByGraphics(graphicsMonitor);
            }
            return;
        }

        GraphicsEntity graphicsEntity = BeanMapper.map(graphics, GraphicsEntity.class);
        graphicsDao.updateGraphics(graphicsEntity);
    }

    @Override
    public void deleteGraphicsByMonitorId(String monitorId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(GraphicsEntity.class)
                .eq("monitorId", monitorId)
                .get();
        graphicsDao.deleteGraphicsByMonitorId(deleteCondition);
    }

    @Override
    public void deleteGraphicsByHostId(String id) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(GraphicsEntity.class)
                .eq("hostId", id)
                .get();
        graphicsDao.deleteGraphicsByHostId(deleteCondition);
    }

    @Override
    public List<Graphics> findGraphicsList(Graphics graphics) {
        QueryCondition queryCondition = QueryBuilders.createQuery(GraphicsEntity.class)
                .eq("hostId",graphics.getHostId())
                .eq("name", graphics.getName())
                .get();
        List<GraphicsEntity> graphicsEntityList = graphicsDao.findGraphicsList(queryCondition);
        return BeanMapper.mapList(graphicsEntityList, Graphics.class);
    }

    @Override
    public void deleteGraphicsByIds(String[] monitorIds) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(GraphicsEntity.class)
                .in("id", monitorIds)
                .get();
        graphicsDao.deleteGraphicsByMonitorIds(deleteCondition);
    }

    @Override
    public List<Graphics> findInformationByGraphics(String hostId) {
        //查询出主机下的配置信息
        QueryCondition queryCondition = QueryBuilders.createQuery(GraphicsEntity.class)
                .eq("hostId", hostId)
                .get();

        List<GraphicsEntity> entityList = graphicsDao.findGraphicsByHostId(queryCondition);

        return BeanMapper.mapList(entityList, Graphics.class);
    }

    @Override
    public List<Graphics> findGraphicsByHisInformation(Graphics graphics) {
        QueryCondition queryCondition = QueryBuilders.createQuery(GraphicsEntity.class)
                .eq("hostId", graphics.getHostId())
                .eq("monitorId", graphics.getMonitorId())
                .eq("source", graphics.getSource())
                .get();
        List<GraphicsEntity> entityList = graphicsDao.findGraphicsByHisInformation(queryCondition);

        return BeanMapper.mapList(entityList,Graphics.class);
    }

    @Override
    public Long findGraphicsAllNum() {
        return graphicsDao.findGraphicsAllNum();
    }

    @Override
    public List<String> findMonitorIds(Graphics graphics) {
        List<GraphicsMonitor> list = graphicsMonitorService.findByGraphics(graphics.getId());
        return list.stream().map(GraphicsMonitor::getMonitorId).toList();
    }
}
