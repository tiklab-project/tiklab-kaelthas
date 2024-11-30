package io.tiklab.kaelthas.internet.internet.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import io.tiklab.kaelthas.internet.internet.dao.InternetDao;
import io.tiklab.kaelthas.internet.internet.entity.InternetEntity;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.graphics.model.InternetGraphics;
import io.tiklab.kaelthas.internet.graphics.service.InternetGraphicsService;
import io.tiklab.kaelthas.internet.graphicsMonitor.service.InGraphicsMonitorService;
import io.tiklab.kaelthas.internet.monitor.service.InternetMonitorService;
import io.tiklab.kaelthas.internet.trigger.service.InTriggerService;
import io.tiklab.kaelthas.internet.triggerMedium.service.InTriggerMediumService;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网络监控信息存储表
 */
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

    //网络监控的分页查询
    @Override
    public Pagination<Internet> findInternetPage(Internet internet) {
        return internetDao.findInternetPage(internet);
    }

    //创建网络监控
    @Override
    public String createInternet(Internet internet) {
        InternetEntity internetEntity = BeanMapper.map(internet, InternetEntity.class);
        String date = ConversionDateUtil.date(9);
        internetEntity.setCreateTime(date);
        internetEntity.setUsability(1);
        internetEntity.setColor((int) Math.round(Math.random() * 5));
        return internetDao.createInternet(internetEntity);
    }

    //根据监控网络的id删除监控的网络信息
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

    //修改网络信息
    @Override
    public void updateInternet(Internet internet) {
        InternetEntity internetEntity = BeanMapper.map(internet, InternetEntity.class);
        String date = ConversionDateUtil.date(9);
        internetEntity.setUpdateTime(date);
        internetDao.updateInternet(internetEntity);
    }

    //根据监控网络的id查询监控网络的信息
    @Override
    public Internet findInternetById(String id) {
        InternetEntity internetEntity = internetDao.findInternetById(id);
        return BeanMapper.map(internetEntity,Internet.class);
    }

    //查询所有的网络信息
    @Override
    public List<Internet> findAll() {
        List<InternetEntity> entityList = internetDao.findAll();
        return BeanMapper.mapList(entityList,Internet.class);
    }
}
