package io.tiklab.kaelthas.host.hostGroup.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.host.hostGroup.entity.HostGroupEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HostGroupDao {

    @Resource
    private JpaTemplate jpaTemplate;

    public List<HostGroupEntity> findHostGroupByName() {
        return jpaTemplate.findAll(HostGroupEntity.class);
    }

    /**
     * 根据id进行查询
     */
    public HostGroupEntity findHostGroupById(String id) {

        return jpaTemplate.findOne(HostGroupEntity.class, id);

    }

    public List<HostGroupEntity> findAllHostGroupList() {
        List<HostGroupEntity> groupEntityList = jpaTemplate.findAll(HostGroupEntity.class);

        return groupEntityList;
    }

    public Pagination<HostGroupEntity> findHostGroupPage(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, HostGroupEntity.class);
    }

    public void createHostGroup(HostGroupEntity map) {
        jpaTemplate.save(map, HostGroupEntity.class);
    }

    public void deleteHostGroup(String id) {
        jpaTemplate.delete(HostGroupEntity.class,id);
    }

    public void updateHostGroup(HostGroupEntity hostGroupEntity) {
        jpaTemplate.update(hostGroupEntity);
    }

    public Long findHostGroupAllNum() {

        String sql = """
                SELECT count(*) countNum
                FROM mtc_host_group
                """;

        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return (Long) map.get("countNum");
    }
}
