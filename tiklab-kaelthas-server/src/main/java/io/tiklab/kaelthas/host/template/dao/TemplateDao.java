package io.tiklab.kaelthas.host.template.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.kaelthas.host.monitor.entity.HostMonitorEntity;
import io.tiklab.kaelthas.host.template.entity.TemplateEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TemplateDao {

    @Resource
    private JpaTemplate jpaTemplate;

    public List<TemplateEntity> getTemplateAll(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, TemplateEntity.class);
    }

    public TemplateEntity findOneTemplate(String id) {

        return jpaTemplate.findOne(TemplateEntity.class, id);
    }
    public Pagination<TemplateEntity> findTemplateForCondition(QueryCondition queryCondition) {
        return jpaTemplate.findPage(queryCondition, TemplateEntity.class);
    }

    public String createTemplate(TemplateEntity templateEntity) {
        return jpaTemplate.save(templateEntity, String.class);
    }

    public void deleteTemplate(String id) {
        jpaTemplate.delete(TemplateEntity.class, id);
    }

    public void updateTemplate(TemplateEntity templateEntity) {
        jpaTemplate.update(templateEntity);
    }


    public Integer findTemplateNum() {
        return jpaTemplate.findAll(TemplateEntity.class).size();
    }

    public Long findTemplateAllNum() {
        String sql = """
                SELECT count(*) countNum
                FROM mtc_template
                """;
        Map<String, Object> map = jpaTemplate.getNamedParameterJdbcTemplate().queryForMap(sql, new HashMap<>());
        return (Long) map.get("countNum");
    }
}
