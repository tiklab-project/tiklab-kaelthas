package io.tiklab.kaelthas.internet.graphicsMonitor.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.util.SqlUtil;
import io.tiklab.kaelthas.internet.graphicsMonitor.dao.InGraphicsMonitorDao;
import io.tiklab.kaelthas.internet.graphicsMonitor.entity.InGraphicsMonitorEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 图形和监控项的关联表
 */
@Service
public class InGraphicsMonitorServiceImpl implements InGraphicsMonitorService {

    @Autowired
    private InGraphicsMonitorDao inGraphicsMonitorDao;

    //创建图形和监控项的关联表
    @Override
    public void createGraphicsMonitorList(String string, List<String> monitorIds) {
        //将传递过来的数据进行拼装SQL,插入表当中
        List<Map<String, Object>> list = monitorIds.stream().map(i -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", RandomStringUtils.randomAlphabetic(12));
            map.put("graphics_id", string);
            map.put("monitor_id", i);
            return map;
        }).collect(Collectors.toList());
        String sql = SqlUtil.getBatchInsertSql("mtc_internet_graphics_monitor", list);
        inGraphicsMonitorDao.createGraphicsMonitorList(sql);
    }

    //根据图形id删除关联表
    @Override
    public void deleteByGraphics(String graphicsId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(InGraphicsMonitorEntity.class)
                .eq("graphicsId", graphicsId)
                .get();
        inGraphicsMonitorDao.deleteByGraphics(deleteCondition);
    }

    //根据图形id查询出监控项的ids
    @Override
    public List<String> findMonitorIds(String id) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InGraphicsMonitorEntity.class)
                .eq("graphicsId", id)
                .get();
        List<InGraphicsMonitorEntity> entityList = inGraphicsMonitorDao.findMonitorIds(queryCondition);

        return entityList.stream().map(InGraphicsMonitorEntity::getMonitorId).collect(Collectors.toList());
    }

    //根据图形的ids删除图形
    @Override
    public void deleteByGraphicsIds(List<String> stringList) {
        if (stringList.isEmpty()) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(InGraphicsMonitorEntity.class)
                .in("graphicsId", stringList.toArray())
                .get();
        inGraphicsMonitorDao.deleteByGraphicsIds(deleteCondition);
    }

}
