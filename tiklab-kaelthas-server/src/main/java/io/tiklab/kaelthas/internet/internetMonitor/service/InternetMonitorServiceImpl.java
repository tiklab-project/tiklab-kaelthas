package io.tiklab.kaelthas.internet.internetMonitor.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.internet.internet.entity.InternetEntity;
import io.tiklab.kaelthas.internet.internetMonitor.dao.InternetMonitorDao;
import io.tiklab.kaelthas.internet.internetMonitor.entity.InternetMonitorEntity;
import io.tiklab.kaelthas.internet.internetMonitor.model.InternetMonitor;
import io.tiklab.toolkit.beans.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternetMonitorServiceImpl implements InternetMonitorService{

    @Autowired
    private InternetMonitorDao internetMonitorDao;

    @Override
    public Pagination<InternetMonitor> findMonitorPage(InternetMonitor internetMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InternetMonitorEntity.class)
                .eq("internetId", internetMonitor.getInternetId())
                .like("name", internetMonitor.getName())
                .pagination(internetMonitor.getParamPage())
                .get();
        Pagination<InternetMonitorEntity> pagination = internetMonitorDao.findMonitorPage(queryCondition);
        List<InternetMonitor> internetMonitors = BeanMapper.mapList(pagination.getDataList(), InternetMonitor.class);

        return PaginationBuilder.build(pagination,internetMonitors);
    }

    @Override
    public String createMonitor(InternetMonitor internetMonitor) {
        InternetMonitorEntity entity = BeanMapper.map(internetMonitor, InternetMonitorEntity.class);
        return internetMonitorDao.createMonitor(entity);
    }

    @Override
    public void deleteMonitor(String id) {
        internetMonitorDao.deleteMonitor(id);
    }

    @Override
    public void updateMonitor(InternetMonitor internetMonitor) {
        InternetMonitorEntity entity = BeanMapper.map(internetMonitor, InternetMonitorEntity.class);
        internetMonitorDao.updateMonitor(entity);
    }

    @Override
    public List<InternetMonitor> findMonitorByInId(InternetMonitor internetMonitor) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InternetMonitorEntity.class)
                .eq("internetId", internetMonitor.getInternetId())
                .get();
        List<InternetMonitorEntity> entityList = internetMonitorDao.findMonitorByInId(queryCondition);

        return BeanMapper.mapList(entityList,InternetMonitor.class);
    }

    @Override
    public void deleteByInternet(String id) {
        if (StringUtils.isBlank(id)) {
            return;
        }
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(InternetMonitorEntity.class)
                .eq("internetId", id)
                .get();
        internetMonitorDao.deleteGraphicsByInId(deleteCondition);
    }
}
