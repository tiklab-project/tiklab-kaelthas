package io.tiklab.kaelthas.timedtask.timer.dao;

import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TimerDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public void updateBySQL(String updateSql) {
        jpaTemplate.getJdbcTemplate().execute(updateSql);
    }

    /*
    * 查询所有数据库的名字
    * */
    public List<String> findDbName(){
        String sql="SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<String> strings = jdbcTemplate.queryForList(sql, String.class);
       return strings;
    }


    /*
     * 创建监控数据库历史的表
     * */
    public void createDataBaseDb(String historyDb){
        String createSql="CREATE TABLE "+historyDb +"(\n" +
                " id VARCHAR(12) PRIMARY KEY,\n" +
                " db_id varchar (12) NOT NULL,\n" +
                " db_monitor_id varchar(12),\n" +
                " report_data text,\n" +
                " gather_time timestamp\n" +
                ")";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();

        jdbcTemplate.execute(createSql);
    }

    /*
     * 创建监控主机历史的表
     * */
    public void createHostDb(String historyDb){
        String createSql="CREATE TABLE "+historyDb +"(\n" +
                " id VARCHAR(12) PRIMARY KEY,\n" +
                " host_id varchar (12) NOT NULL,\n" +
                " host_monitor_id varchar(12),\n" +
                " report_data text,\n" +
                " gather_time timestamp\n" +
                ")";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();

        jdbcTemplate.execute(createSql);
    }

    /*
     * 创建K8s主机历史的表
     * */
    public void createK8sDb(String historyDb){
        String createSql="CREATE TABLE "+historyDb +"(\n" +
                " id VARCHAR(12) PRIMARY KEY,\n" +
                " ku_id varchar (12) NOT NULL,\n" +
                " ku_monitor_id varchar(12),\n" +
                " report_data text,\n" +
                " gather_time timestamp\n" +
                ")";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();

        jdbcTemplate.execute(createSql);
    }


    /*
     * 创建监控网络历史的表
     * */
    public void createInternetDb(String historyDb){
        String createSql="CREATE TABLE "+historyDb +"(\n" +
                " id VARCHAR(12) PRIMARY KEY,\n" +
                " internet_id varchar (12) NOT NULL,\n" +
                " internet_monitor_id varchar(12),\n" +
                " report_data text,\n" +
                " gather_time timestamp\n" +
                ")";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();

        jdbcTemplate.execute(createSql);
    }
}
