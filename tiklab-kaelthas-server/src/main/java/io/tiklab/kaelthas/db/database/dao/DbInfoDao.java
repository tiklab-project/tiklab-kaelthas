package io.tiklab.kaelthas.db.database.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.db.database.entity.DbInfoEntity;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.dbMonitor.model.DbMonitor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbInfoDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<DbInfo> findDbInfoPage(DbInfo dbInfo) {

        String sql = """
                SELECT mdi.*,COUNT(ma.id) as alarmNum,MAX(ma.send_message) AS message
                FROM mtc_db_info mdi
                LEFT JOIN mtc_alarm ma
                ON mdi.id = ma.host_id AND ma.status = 2
                where 1=1
                """;

        if (dbInfo.getUsability() != null) {
            sql = sql.concat(" and usability = " + dbInfo.getUsability());
        }

        if (StringUtils.isNotBlank(dbInfo.getName())) {
            sql = sql.concat(" and mdi.name like '%" + dbInfo.getName() + "%'");
        }

        sql = sql.concat("\n GROUP BY mdi.id");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, dbInfo.getPageParam(), new BeanPropertyRowMapper<>(DbInfo.class));
    }

    public String createDbInfo(DbInfoEntity dbInfoEntity) {
        return jpaTemplate.save(dbInfoEntity, String.class);
    }

    public void updateDbInfo(DbInfoEntity dbInfoEntity) {
        jpaTemplate.update(dbInfoEntity);
    }

    public void deleteDbInfo(String id) {
        jpaTemplate.delete(DbInfoEntity.class, id);
    }

    public DbInfoEntity findDbInfoById(String id) {
        return jpaTemplate.findOne(DbInfoEntity.class, id);
    }

    public List<DbMonitor> findUsableDbInfoList() {
        String sql = """
                SELECT mdi.id as dbId,mdm.dat_name as datName,mdi.username,mdi.password,mdi.ip,mdm.id,mdi.db_port as port,mdm.db_item_id as monitorItemId
                FROM mtc_db_info mdi
                JOIN mtc_db_monitor mdm
                ON mdi.id = mdm.db_id
                WHERE mdi.state = 1 and status = 1 and mdi.db_type = 'PostgreSQL'
                """;

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbMonitor.class));
    }

    public List<DbInfo> findDropDown() {
        String sql = """
                SELECT *
                FROM mtc_db_info mdi
                ORDER BY update_time DESC
                LIMIT 4
                """;
        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbInfo.class));
    }

    public List<DbInfoEntity> findAll() {
        return jpaTemplate.findAll(DbInfoEntity.class);
    }

    public List<DbMonitor> findMysqlItemList() {
        String sql = """
                SELECT mdi.id as dbId,mdm.dat_name as datName,mdi.username,mdi.password,mdi.ip,mdm.id,mdi.db_port as port,mdm.db_item_id as monitorItemId
                FROM mtc_db_info mdi
                JOIN mtc_db_monitor mdm
                ON mdi.id = mdm.db_id
                WHERE mdi.state = 1 and status = 1 and mdi.db_type = 'MYSQL'
                """;

        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DbMonitor.class));
    }
}
