package io.tiklab.kaelthas.host.dynamic.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.kaelthas.host.dynamic.entity.DynamicEntity;
import io.tiklab.kaelthas.host.dynamic.model.Dynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DynamicDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public List<DynamicEntity> findList(List<String> idList) {
        return jpaTemplate.findList(DynamicEntity.class,idList);
    }

    public List<Dynamic> findDynamicList() {
        String sql = """
                select * 
                from mtc_dynamic
                order by update_time desc 
                limit 10
                """;

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper<>(Dynamic.class));
    }

    public void createDynamic(DynamicEntity dynamicEntity) {
        jpaTemplate.save(dynamicEntity, DynamicEntity.class);
    }

    public void deleteByIds(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }

    public void deleteByHostId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
