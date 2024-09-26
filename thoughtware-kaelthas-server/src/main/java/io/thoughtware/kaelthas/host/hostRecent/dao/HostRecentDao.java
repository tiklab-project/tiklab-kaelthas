package io.thoughtware.kaelthas.host.hostRecent.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.host.hostRecent.entity.HostRecentEntity;
import io.thoughtware.kaelthas.host.hostRecent.model.HostRecent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HostRecentDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public Pagination<HostRecentEntity> findRecentHostList(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition,HostRecentEntity.class);
    }

    public String createHostRecent(HostRecentEntity recentEntity) {
        return jpaTemplate.save(recentEntity,String.class);
    }

    public List<HostRecentEntity> findRecentList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, HostRecentEntity.class);
    }

    public void updateHostRecent(HostRecentEntity hostRecentEntity) {
        jpaTemplate.update(hostRecentEntity);
    }

    public void deleteByHostId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public List<HostRecent> findHostRecentList() {
        String sql = """
                select a.*,mh.name hostName,mh.state,mh.color
                FROM(		
                select mhr.*,COALESCE(ma.alarmNum,0) alarmNum
                    from mtc_host_recent mhr
                    LEFT JOIN (
                    SELECT host_id,count(id) alarmNum
                    FROM mtc_alarm ma
                    WHERE ma.status =2
                    GROUP BY host_id
                    )ma
                    ON mhr.host_id = ma.host_id
                    limit 4) a
                    LEFT JOIN mtc_host mh
                    ON a.host_id = mh.id
                    order by a.update_time desc
                """;
        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(HostRecent.class));
    }

    public List<HostRecentEntity> findAllHostRecent() {
        return jpaTemplate.findAll(HostRecentEntity.class);
    }

    public HostRecentEntity findHostRecentOne(String id) {
        return jpaTemplate.findOne(HostRecentEntity.class,id);
    }
}
