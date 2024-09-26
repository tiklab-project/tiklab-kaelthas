package io.thoughtware.kealthas.switchs.dao;


import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.kealthas.switchs.model.SwitchHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SwitchHostDao {

    @Autowired
    private JpaTemplate jpaTemplate;


    public List<SwitchHost> findSwitchList() {
        String sql = """
                
                """;
        return null;
    }
}
