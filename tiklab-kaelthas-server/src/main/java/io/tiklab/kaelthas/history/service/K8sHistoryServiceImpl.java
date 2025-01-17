package io.tiklab.kaelthas.history.service;

import io.tiklab.kaelthas.history.dao.K8sHistoryDao;
import io.tiklab.kaelthas.history.model.History;
import io.tiklab.kaelthas.util.SqlUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class K8sHistoryServiceImpl implements K8sHistoryService {

    @Autowired
    K8sHistoryDao k8sHistoryDao;
    @Override
    public void insertForList(List<History> entityList) {

        // 获取当前年、月
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        // 获取当前月份
        int month = currentDate.getMonthValue();
        String value = String.valueOf(month);
        if (value.length()<2){
            value="0"+month;
        }
        String hostDbName="mtc_ku_history_"+year+value;

        List<Map<String, Object>> mapList = getMapList(entityList);
        String historySql = SqlUtil.getBatchInsertSql(hostDbName, mapList);
        k8sHistoryDao.insertHistoryList(historySql);


    }


    /**
     * 定义要插入表的字段,在插入数据的时候使用
     */
    private static List<Map<String, Object>> getMapList(List<History> entityList) {
        return entityList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            String uuid = RandomStringUtils.randomAlphanumeric(12);
            map.put("id", uuid);
            map.put("ku_id", history.getHostId());
            map.put("ku_monitor_id", history.getMonitorId());
            map.put("report_data", history.getReportData());
            map.put("gather_time", history.getGatherTime());
            return map;
        }).toList();
    }
}
