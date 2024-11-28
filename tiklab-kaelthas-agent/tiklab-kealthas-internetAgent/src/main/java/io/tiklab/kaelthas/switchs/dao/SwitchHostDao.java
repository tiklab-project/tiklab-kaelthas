package io.tiklab.kaelthas.switchs.dao;


import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.switchs.model.SwitchMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SwitchHostDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    //查询网络的监控项信息
    public List<SwitchMonitor> findSwitchList() {
        String sql = """
                SELECT mim.id as monitorId,mim.internet_item_id,mi.*
                FROM mtc_internet_monitor mim
                LEFT JOIN mtc_internet mi
                ON mim.internet_id = mi.id
                LIMIT 100
                """;
        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(SwitchMonitor.class));
    }

    //查询100个网络的信息
    public List<SwitchMonitor> findAllInternetList(){
        String sql = """
                SELECT *
                FROM mtc_internet
                LIMIT 100
                """;
        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(SwitchMonitor.class));
    }
}
