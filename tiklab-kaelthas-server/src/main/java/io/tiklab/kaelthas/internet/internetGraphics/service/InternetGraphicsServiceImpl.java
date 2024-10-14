package io.tiklab.kaelthas.internet.internetGraphics.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.internet.internetGraphics.dao.InternetGraphicsDao;
import io.tiklab.kaelthas.internet.internetGraphics.entity.InternetGraphicsEntity;
import io.tiklab.kaelthas.internet.internetGraphics.model.InternetGraphics;
import io.tiklab.kaelthas.internet.internetGraphicsMonitor.model.InGraphicsMonitor;
import io.tiklab.kaelthas.internet.internetGraphicsMonitor.service.InGraphicsMonitorService;
import io.tiklab.toolkit.beans.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InternetGraphicsServiceImpl implements InternetGraphicsService{

    @Autowired
    private InternetGraphicsDao internetGraphicsDao;

    @Autowired
    private InGraphicsMonitorService inGraphicsMonitorService;

    @Override
    public Pagination<InternetGraphics> findGraphicsPage(InternetGraphics internetGraphics) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InternetGraphicsEntity.class)
                .eq("id", internetGraphics.getId())
                .like("name", internetGraphics.getName())
                .pagination(internetGraphics.getParamPage())
                .get();

        return internetGraphicsDao.findGraphicsPage(internetGraphics);

    }

    @Override
    public String createGraphics(InternetGraphics internetGraphics) {
        try {
            //创建图形的时候顺便将图形下监控项的信息插入到另外一张表中
            if (internetGraphics.getMonitorIds().isEmpty()) {
                return null;
            }

            InternetGraphicsEntity graphics = BeanMapper.map(internetGraphics, InternetGraphicsEntity.class);
            String string = internetGraphicsDao.createGraphics(graphics);
            inGraphicsMonitorService.createGraphicsMonitorList(string,internetGraphics.getMonitorIds());
            return string;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGraphics(String id) {
        try {
            internetGraphicsDao.deleteGraphics(id);
            inGraphicsMonitorService.deleteByGraphics(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGraphics(InternetGraphics internetGraphics) {
        try {

            InternetGraphicsEntity graphics = BeanMapper.map(internetGraphics, InternetGraphicsEntity.class);

            if (internetGraphics.getMonitorIds().isEmpty()) {
                internetGraphicsDao.updateGraphics(graphics);
            }else {
                inGraphicsMonitorService.deleteByGraphics(internetGraphics.getId());
                inGraphicsMonitorService.createGraphicsMonitorList(internetGraphics.getId(),internetGraphics.getMonitorIds());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<InternetGraphics> findGraphicsList(String internetId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InternetGraphicsEntity.class)
                .eq("internetId", internetId)
                .get();
        List<InternetGraphicsEntity> graphicsList = internetGraphicsDao.findGraphicsList(queryCondition);
        return BeanMapper.mapList(graphicsList,InternetGraphics.class);
    }

    @Override
    public void deleteGraphicsByInId(String id) {
        if (StringUtils.isBlank(id)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(InternetGraphicsEntity.class)
                .eq("internetId", id)
                .get();
        internetGraphicsDao.deleteGraphicsByInId(deleteCondition);
    }

    @Override
    public List<InGraphicsMonitor> findGraphicsMonitors(String id) {
        return internetGraphicsDao.findGraphicsMonitors(id);
    }
}
