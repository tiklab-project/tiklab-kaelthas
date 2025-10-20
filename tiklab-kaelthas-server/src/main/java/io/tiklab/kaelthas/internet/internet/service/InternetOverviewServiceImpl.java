package io.tiklab.kaelthas.internet.internet.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.kaelthas.internet.internet.dao.InternetOverviewDao;
import io.tiklab.kaelthas.internet.internet.entity.InternetOverviewEntity;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import io.tiklab.kaelthas.internet.internet.model.InternetOverview;
import io.tiklab.kaelthas.internet.internet.model.InternetOverviewQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InternetOverviewServiceImpl implements InternetOverviewService{

    @Autowired
    InternetOverviewDao internetOverviewDao;

    @Override
    public String createInternetOverview(InternetOverview internetOverview) {
        String overviewId;
        InternetOverviewQuery overviewQuery = new InternetOverviewQuery();
        overviewQuery.setInternetId(internetOverview.getInternetId());
        overviewQuery.setType(internetOverview.getType());
        List<InternetOverview> internetList = this.findInternetList(overviewQuery);
        if (CollectionUtils.isNotEmpty(internetList)){
            InternetOverview overview = internetList.get(0);
            internetOverview.setId(overview.getId());
            this.updateInternetOverview(internetOverview);
            overviewId=overview.getId();

        }else {
            InternetOverviewEntity overviewEntity = BeanMapper.map(internetOverview, InternetOverviewEntity.class);
             overviewId = internetOverviewDao.createInternetOverview(overviewEntity);
        }

        return overviewId;
    }

    @Override
    public void updateInternetOverview(InternetOverview internetOverview) {
        InternetOverviewEntity overviewEntity = BeanMapper.map(internetOverview, InternetOverviewEntity.class);
        overviewEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        internetOverviewDao.updateInternetOverview(overviewEntity);
    }

    @Override
    public Map<String, Object> findInternetByInId(String internetId) {
        Map<String, Object> map = new HashMap<>();
        List<InternetOverview> internetList = findInternetList(new InternetOverviewQuery().setInternetId(internetId));

        //系统信息
        List<InternetOverview> systemOverviews = internetList.stream().filter(a -> ("system").equals(a.getType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(systemOverviews)){

            String reportData = systemOverviews.get(0).getReportData();
            Map map1 = JSON.parseObject(reportData, Map.class);
            map.put("systemInfo", map1);
        }

        List<InternetOverview> podOverviews = internetList.stream().filter(a -> ("pod").equals(a.getType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(podOverviews)){

            String reportData = podOverviews.get(0).getReportData();
          if (!("[]").equals(reportData)){
              Map map1 = JSON.parseObject(reportData, Map.class);
              map.put("podInfo", map1);
          }
        }
        return map;
    }

    @Override
    public  List<InternetOverview> findInternetList(InternetOverviewQuery internetOverviewQuery) {
        List<InternetOverviewEntity> libraryEntityList = internetOverviewDao.findLibraryList(internetOverviewQuery);

        List<InternetOverview> overviews = BeanMapper.mapList(libraryEntityList, InternetOverview.class);

        return overviews;
    }
}
