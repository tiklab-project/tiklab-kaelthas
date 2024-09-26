package io.thoughtware.kaelthas.medium.dao;

import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.kaelthas.medium.entity.MediumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MediumDao {

    @Autowired
    private JpaTemplate jpaTemplate;

    public List<MediumEntity> getMediumAllList() {
        return jpaTemplate.findAll(MediumEntity.class);
    }
}
