package io.tiklab.kaelthas.collection.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.kubernetes.kubernetesMonitor.model.KuMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KuCollectionDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    //查询k8s的设备信息,之后填充监控项的信息
    public List<KuMonitor> findKuMonitor(){
        String sql = """
                SELECT *
                FROM mtc_ku_info mk
                """;

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(KuMonitor.class));
    }
}
