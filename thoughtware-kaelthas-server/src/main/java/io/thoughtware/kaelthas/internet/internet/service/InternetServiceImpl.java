package io.thoughtware.kaelthas.internet.internet.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.common.util.ConversionDateUtil;
import io.thoughtware.kaelthas.internet.internet.dao.InternetDao;
import io.thoughtware.kaelthas.internet.internet.entity.InternetEntity;
import io.thoughtware.kaelthas.internet.internet.model.Internet;
import io.thoughtware.kaelthas.internet.internetGraphics.model.InternetGraphics;
import io.thoughtware.kaelthas.internet.internetGraphics.service.InternetGraphicsService;
import io.thoughtware.kaelthas.internet.internetGraphicsMonitor.service.InGraphicsMonitorService;
import io.thoughtware.kaelthas.internet.internetMonitor.service.InternetMonitorService;
import io.thoughtware.kaelthas.internet.internetTrigger.service.InTriggerService;
import io.thoughtware.kaelthas.internet.internetTriggerMedium.service.InTriggerMediumService;
import io.thoughtware.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternetServiceImpl implements InternetService{

    @Autowired
    private InternetDao internetDao;

    @Autowired
    private InternetMonitorService internetMonitorService;

    @Autowired
    private InternetGraphicsService internetGraphicsService;

    @Autowired
    private InGraphicsMonitorService inGraphicsMonitorService;

    @Autowired
    private InTriggerService inTriggerService;

    @Autowired
    private InTriggerMediumService inTriggerMediumService;

    @Override
    public Pagination<Internet> findInternetPage(Internet internet) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InternetEntity.class)
                .eq("id", internet.getId())
                .like("name", internet.getName())
                .pagination(internet.getParamPage())
                .get();

        Pagination<InternetEntity> pagination = internetDao.findInternetPage(queryCondition);

        List<Internet> internets = BeanMapper.mapList(pagination.getDataList(), Internet.class);
        return PaginationBuilder.build(pagination,internets);
    }

    @Override
    public String createInternet(Internet internet) {
        InternetEntity internetEntity = BeanMapper.map(internet, InternetEntity.class);
        String date = ConversionDateUtil.date(9);
        internetEntity.setCreateTime(date);
        internetEntity.setUsability(1);
        internetEntity.setColor((int) Math.round(Math.random() * 5));
        return internetDao.createInternet(internetEntity);
    }

    @Override
    public void deleteInternet(String id) {
        //删除图形,先查询出图形的ids,删除图形与监控项关联的表
        //查询出图形的ids
        try {
            List<InternetGraphics> graphicsList = internetGraphicsService.findGraphicsList(id);
            List<String> stringList = graphicsList.stream().map(InternetGraphics::getId).toList();

            //根据图形的ids删除关联表中的信息
            inGraphicsMonitorService.deleteByGraphicsIds(stringList);

            //根据网络id删除图形
            internetGraphicsService.deleteGraphicsByInId(id);

            //删除网络下监控项
            internetMonitorService.deleteByInternet(id);

            //删除触发器
            inTriggerService.deleteByInId(id);

            //查找出触发器下的关联表,根据查询出来的触发器id删除关联表数据
            List<String> triggerIds = inTriggerService.findTriggerByInId(id);

            inTriggerMediumService.deleteByTriggerIds(triggerIds);

            //删除网络
            internetDao.deleteInternet(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateInternet(Internet internet) {
        InternetEntity internetEntity = BeanMapper.map(internet, InternetEntity.class);
        String date = ConversionDateUtil.date(9);
        internetEntity.setUpdateTime(date);
        internetDao.updateInternet(internetEntity);
    }

    @Override
    public Internet findInternetById(String id) {
        InternetEntity internetEntity = internetDao.findInternetById(id);
        return BeanMapper.map(internetEntity,Internet.class);
    }
}
