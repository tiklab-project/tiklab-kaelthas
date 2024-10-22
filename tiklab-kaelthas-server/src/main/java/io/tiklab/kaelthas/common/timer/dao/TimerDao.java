package io.tiklab.kaelthas.common.timer.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TimerDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void updateBySQL(String updateSql) {
        jpaTemplate.getJdbcTemplate().execute(updateSql);
    }
}
