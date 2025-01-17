package io.tiklab.kaelthas.db.agent.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.db.agent.model.DbMonitorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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




}
