package io.tiklab.kaelthas.kubernetes.kubernetesInfo.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.kaelthas.db.history.model.DbHistory;
import io.tiklab.kaelthas.kubernetes.history.model.KubernetesHistory;
import io.tiklab.kaelthas.kubernetes.history.service.KubernetesHistoryService;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.dao.KuOverviewDao;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.entity.KuOverviewEntity;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.KuOverview;
import io.tiklab.kaelthas.kubernetes.kubernetesInfo.model.KuOverviewQuery;
import io.tiklab.kaelthas.util.SqlUtil;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KuOverviewServiceImpl implements KuOverviewService {

    @Autowired
    KuOverviewDao kuOverviewDao;


    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public void createKuOverview(List<KuOverview> kuOverview) {
        if (CollectionUtils.isNotEmpty(kuOverview)){
            KuOverview overview = kuOverview.get(0);
            List<KuOverview> kuOverviewList = findKuByKuId(overview.getKuId());

            getMapList(kuOverview,kuOverviewList);
        }

    }

    @Override
    public void updateKuOverview(KuOverview kuOverview) {
        KuOverviewEntity overviewEntity = BeanMapper.map(kuOverview, KuOverviewEntity.class);
        overviewEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        kuOverviewDao.updateKuOverview(overviewEntity);
    }

    @Override
    public  List<KuOverview> findKuByKuId(String kuId) {
        List<KuOverviewEntity> kuOverviewEntities = kuOverviewDao.findKuOverviewList(new KuOverviewQuery().setKuId(kuId));
        List<KuOverview> overviews = BeanMapper.mapList(kuOverviewEntities, KuOverview.class);
        return overviews;
    }

    @Override
    public  List<KuOverview> findKuList(KuOverviewQuery kuOverviewQuery) {
        List<KuOverviewEntity> kuOverviewEntities = kuOverviewDao.findKuOverviewList(kuOverviewQuery);

        List<KuOverview> overviews = BeanMapper.mapList(kuOverviewEntities, KuOverview.class);
        joinTemplate.joinQuery(overviews);
        return overviews;
    }

    /**
     * 根据id查询k8s概况页展示的信息数据
     */
    @Override
    public Map<String, Object> findKuOverviewTotal(String kuId) {
        Map<String, Object> map = new HashMap<>();

        List<KuOverview> kuOverviewList = findKuList(new KuOverviewQuery().setKuId(kuId));
        //1-18获取的是数字
        List<KuOverview> kuOverviews = kuOverviewList.stream().filter(view -> view.getType() == 1).collect(Collectors.toList());
        map.put("mapTotal", kuOverviews);

        List<Object> objectList = new LinkedList<>();
        List<KuOverview> overviews = kuOverviewList.stream().filter(view -> view.getType()== 2).collect(Collectors.toList());
        for (KuOverview kuOverview : overviews) {
            String reportData = kuOverview.getReportData();
            if (StringUtils.isEmpty(reportData)||reportData=="[]"){
                continue;
            }
            Map<String, Object> objectMap = new HashMap<>();
            List<List<String>> listList = new LinkedList<>();
            Set<String> stringSet = new HashSet<>();

            if (ObjectUtils.isNotEmpty(kuOverview)) {
                List<Map<String, String>> mapList = JSON.parseObject(kuOverview.getReportData(), List.class);
                if (mapList.isEmpty()) {
                    objectMap.put("name", kuOverview.getKubernetesItem().getName());
                    objectMap.put("data", listList);
                    objectList.add(objectMap);
                    continue;
                }
                for (Map<String, String> stringMap : mapList) {
                    List<String> list1 = new LinkedList<>();
                    for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                        stringSet.add(entry.getKey());
                        list1.add(entry.getValue());
                    }
                    listList.add(list1);
                }
                listList.add(stringSet.stream().collect(Collectors.toList()));
                Collections.reverse(listList);
            }

            objectMap.put("name", kuOverview.getKubernetesItem().getName());
            objectMap.put("data", listList);

            objectList.add(objectMap);
        }
        map.put("mapStatus", objectList);

        return map;
        //return kubernetesHistoryService.findKuOverviewTotal(kuId);
    }



    /**
     * 定义要插入表的字段,在插入数据的时候使用
     * @param entityList 添加的数据list
     * @param kuOverviewList 查询数据库的list
     */
    public  void getMapList(List<KuOverview> entityList,  List<KuOverview> kuOverviewList) {
        List<Map<String, Object>> createList = new ArrayList<>();
        List<KuOverview> updateList = new ArrayList<>();
        for (KuOverview kuOverview:entityList){
            List<KuOverview> kuOverviews = kuOverviewList.stream().filter(a -> kuOverview.getKubernetesItem().getId().equals(a.getKubernetesItem().getId()))
                    .collect(Collectors.toList());

            //如果数据库存在对应的数据，只需要修改
            if (CollectionUtils.isNotEmpty(kuOverviews)){
                //判断获取的内容是否发生改变。未改变就不需要执行修改操作
                List<KuOverview> kuOverviews1 = kuOverviews.stream().filter(b -> !b.getReportData().equals(b.getReportData())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(kuOverviews1)){
                    kuOverview.setId(kuOverviews.get(0).getId());
                    updateList.add(kuOverview);
                }
            }else {
                String uuid = RandomStringUtils.randomAlphanumeric(12);
                Map<String, Object> createMap = addMap(kuOverview, uuid);
                createList.add(createMap);
            }
        }

        //批量创建数据
        if (CollectionUtils.isNotEmpty(createList)){
            String kuOverviewSql = SqlUtil.getBatchInsertSql("mtc_ku_overview", createList);
            kuOverviewDao.execKuOverviewSql(kuOverviewSql);
        }

        //批量更新数据
        if (CollectionUtils.isNotEmpty(updateList)){
            String kuOverviewSql = SqlUtil.getKuViewBatchUpdateSql("mtc_ku_overview", updateList);
            kuOverviewDao.execKuOverviewSql(kuOverviewSql);
        }

    }

    /**
     * 添加字段和数据到map中
     * @param kuOverview 搜集的数据
     * @param id 添加、更新的ID
     */
    public Map<String, Object> addMap(KuOverview kuOverview,String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("ku_id", kuOverview.getKuId());
        map.put("report_data", kuOverview.getReportData());
        map.put("create_time", kuOverview.getCreateTime());
        if (ObjectUtils.isNotEmpty(kuOverview.getKubernetesItem())){
            map.put("ku_item_id", kuOverview.getKubernetesItem().getId());
        }
        map.put("type", kuOverview.getType());
        return map;
    }

}
