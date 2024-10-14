package io.tiklab.kaelthas.host.graphicsMonitor.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.graphicsMonitor.dao.GraphicsMonitorDao;
import io.tiklab.kaelthas.host.graphicsMonitor.entity.GraphicsMonitorEntity;
import io.tiklab.kaelthas.host.graphicsMonitor.model.GraphicsMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class GraphicsMonitorServiceImpl implements GraphicsMonitorService {

    @Autowired
    private GraphicsMonitorDao graphicsMonitorDao;

    @Override
    public void insertByGraphics(GraphicsMonitor graphicsMonitor) {
        GraphicsMonitorEntity relation = BeanMapper.map(graphicsMonitor, GraphicsMonitorEntity.class);
        graphicsMonitorDao.insertByGraphics(relation);
    }

    @Override
    public void deleteByGraphicsId(String graphicsId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(GraphicsMonitorEntity.class)
                .eq("graphicsId", graphicsId)
                .get();

        graphicsMonitorDao.deleteByGraphicsId(deleteCondition);
    }

    @Override
    public void deleteByMonitorIds(String[] strings) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(GraphicsMonitorEntity.class)
                .in("monitorId", strings)
                .get();
        graphicsMonitorDao.deleteByMonitorIds(deleteCondition);
    }

    @Override
    public void deleteByMonitorId(String id) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(GraphicsMonitorEntity.class)
                .eq("monitorId", id)
                .get();
        graphicsMonitorDao.deleteByMonitorId(deleteCondition);
    }

    @Override
    public List<GraphicsMonitor> findByGraphics(String graphicsId) {
        return graphicsMonitorDao.findByGraphics(graphicsId);
    }

    @Override
    public void deleteByGraphicsIds(List<String> stringList) {
        if (stringList.isEmpty()) {
            return;
        }

        Object[] array = stringList.toArray();
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(GraphicsMonitorEntity.class)
                .in("graphicsId", array)
                .get();
        graphicsMonitorDao.deleteByGraphicsIds(deleteCondition);

    }
}
