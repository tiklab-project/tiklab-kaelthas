package io.tiklab.kaelthas.collection.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.collection.model.vo.DbMonitorVo;
import io.tiklab.kaelthas.collection.model.vo.DbHistoryVo;
import io.tiklab.kaelthas.collection.utils.AgentSqlUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DbSqlDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    /**
     * 查询MYSQL或者是Pgsql的配置信息
     */
    public List<DbMonitorVo> findSqlItemList(String dbType) {
        String sql = """
                SELECT mdi.id as dbId,mdm.dat_name as datName,mdi.username,mdi.password,mdi.ip,mdm.id,mdi.db_port as port,mdm.db_item_id as monitorItemId
                FROM mtc_db_info mdi
                JOIN mtc_db_monitor mdm
                ON mdi.id = mdm.db_id
                WHERE mdi.state = 1 and status = 1 
                """;

        sql = sql.concat(" and mdi.db_type = '" + dbType + "'");

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbMonitorVo.class));
    }

    //将收集的数据插入到mtc_history
    public void insertForList(List<DbHistoryVo> entityList) {
        List<Map<String, Object>> mapList = getMapList(entityList);
        String historySql = AgentSqlUtil.getBatchInsertSql("mtc_history", mapList);

        jpaTemplate.getJdbcTemplate().execute(historySql);
        List<DbHistoryVo> list1 = new ArrayList<>();
        List<DbHistoryVo> list5 = new ArrayList<>();
        List<DbHistoryVo> list15 = new ArrayList<>();

        for (DbHistoryVo information : entityList) {
            String gatherTime = information.getGatherTime();

            //判断是否能插入到一分钟的表当中
            boolean divisible = AgentSqlUtil.isDivisible(gatherTime, 1);
            if (divisible) {
                list1.add(information);
            }

            boolean divisible1 = AgentSqlUtil.isDivisible(gatherTime, 5);
            if (divisible1) {
                list5.add(information);
            }

            boolean divisible2 = AgentSqlUtil.isDivisible(gatherTime, 15);
            if (divisible2) {
                list15.add(information);
            }
        }

        if (!list1.isEmpty()) {
            List<Map<String, Object>> mapList1 = getMapList(list1);
            String historySql1 = AgentSqlUtil.getBatchInsertSql("mtc_history_one_minute", mapList1);
            jpaTemplate.getJdbcTemplate().execute(historySql1);
        }

        if (!list5.isEmpty()) {
            List<Map<String, Object>> mapList5 = getMapList(list5);
            String historySql5 = AgentSqlUtil.getBatchInsertSql("mtc_history_five_minute", mapList5);
            jpaTemplate.getJdbcTemplate().execute(historySql5);
        }

        if (!list15.isEmpty()) {
            List<Map<String, Object>> mapList15 = getMapList(list15);
            String historySql15 = AgentSqlUtil.getBatchInsertSql("mtc_history_fifteen_minute", mapList15);
            jpaTemplate.getJdbcTemplate().execute(historySql15);
        }

    }

    private static List<Map<String, Object>> getMapList(List<DbHistoryVo> entityList) {
        return entityList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            String uuid = RandomStringUtils.randomAlphanumeric(12);
            map.put("id", uuid);
            map.put("host_id", history.getHostId());
            map.put("monitor_id", history.getMonitorId());
            map.put("report_data", history.getReportData());
            map.put("gather_time", history.getGatherTime());
            return map;
        }).toList();
    }

}
