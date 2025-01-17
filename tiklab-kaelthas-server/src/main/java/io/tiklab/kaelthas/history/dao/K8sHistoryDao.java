package io.tiklab.kaelthas.history.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class K8sHistoryDao {
    @Autowired
    private JpaTemplate jpaTemplate;
    public void insertHistoryList(String historySql) {
        jpaTemplate.getJdbcTemplate().execute(historySql);
    }
}
