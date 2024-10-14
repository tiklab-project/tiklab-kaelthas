package io.tiklab.kaelthas.internet.internet.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.kaelthas.internet.internet.entity.InternetEntity;
import io.tiklab.kaelthas.internet.internet.model.Internet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InternetDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public Pagination<Internet> findInternetPage(Internet internet) {
        String sql = """
                SELECT mi.*,count(ma.id) as alarmNum,MAX(ma.send_message) AS message
                FROM mtc_internet mi
                LEFT JOIN mtc_alarm ma
                ON mi.id = ma.host_id and ma.status = 2
                where 1=1
                """;

        if (internet.getUsability() != null) {
            sql = sql.concat(" and mi.usability = " + internet.getUsability());
        }

        if (StringUtils.isNotBlank(internet.getName())) {
            sql = sql.concat(" and mi.name like '" + internet.getName() + "'");
        }

        sql = sql.concat(" GROUP BY mi.id");

        return jpaTemplate.getJdbcTemplate().findPage(sql, null, internet.getParamPage(), new BeanPropertyRowMapper<>(Internet.class));
    }

    public String createInternet(InternetEntity internetEntity) {
        return jpaTemplate.save(internetEntity, String.class);
    }

    public void deleteInternet(String id) {
        jpaTemplate.delete(InternetEntity.class, id);
    }

    public void updateInternet(InternetEntity internetEntity) {
        jpaTemplate.update(internetEntity);
    }

    public InternetEntity findInternetById(String id) {
        return jpaTemplate.findOne(InternetEntity.class, id);
    }

    public List<InternetEntity> findAll() {
        return jpaTemplate.findAll(InternetEntity.class);
    }
}
