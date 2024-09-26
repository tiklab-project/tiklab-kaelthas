package io.thoughtware.kaelthas.kubernetes.kubernetesGraphics.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphics;
import io.thoughtware.kaelthas.kubernetes.kubernetesGraphics.entity.KuGraphicsEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KuGraphicsDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<KuGraphics> findKuGraphicsPage(KuGraphics kuGraphics) {
        String sql = """
                SELECT mkg.*,count(mkgm.id) as monitorNum
                FROM "public"."mtc_ku_graphics" mkg
                LEFT JOIN mtc_ku_graphics_monitor mkgm
                ON mkg.id = mkgm.graphics_id
                where 1=1
                """;

        if (StringUtils.isNotBlank(kuGraphics.getKuId())) {
            sql = sql.concat(" and mkg.ku_id = '" + kuGraphics.getKuId() + "'");
        }

        if (StringUtils.isNotBlank(kuGraphics.getName())) {
            sql = sql.concat(" and mkg.name = '" + kuGraphics.getName() + "'");
        }

        sql = sql.concat("\nGROUP BY mkg.id");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, kuGraphics.getPageParam(), new BeanPropertyRowMapper<>(KuGraphics.class));
    }

    public String createKuGraphics(KuGraphicsEntity entity) {
        return jpaTemplate.save(entity,String.class);
    }

    public void deleteKuGraphics(String id) {
        jpaTemplate.delete(KuGraphicsEntity.class,id);
    }

    public void updateKuGraphics(KuGraphicsEntity kuGraphicsEntity) {
        jpaTemplate.update(kuGraphicsEntity);
    }

    public List<KuGraphicsEntity> findKuGraphicsList(QueryCondition queryCondition) {
        return jpaTemplate.findList(queryCondition, KuGraphicsEntity.class);
    }

    public void deleteByKuId(DeleteCondition deleteCondition) {
        jpaTemplate.delete(deleteCondition);
    }
}
