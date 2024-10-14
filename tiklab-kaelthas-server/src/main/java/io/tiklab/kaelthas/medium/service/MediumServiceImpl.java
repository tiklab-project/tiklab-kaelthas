package io.tiklab.kaelthas.medium.service;

import io.tiklab.kaelthas.medium.dao.MediumDao;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.medium.entity.MediumEntity;
import io.tiklab.kaelthas.medium.model.Medium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Exporter
public class MediumServiceImpl implements MediumService {

    @Autowired
    private MediumDao mediumDao;

    @Override
    public List<Medium> getMediumAllList() {
        List<MediumEntity> mediumEntities = mediumDao.getMediumAllList();

        return BeanMapper.mapList(mediumEntities, Medium.class);
    }
}
