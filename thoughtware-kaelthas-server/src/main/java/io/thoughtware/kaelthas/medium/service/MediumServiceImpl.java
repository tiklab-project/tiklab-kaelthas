package io.thoughtware.kaelthas.medium.service;

import io.thoughtware.kaelthas.medium.dao.MediumDao;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.kaelthas.medium.entity.MediumEntity;
import io.thoughtware.kaelthas.medium.model.Medium;
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
