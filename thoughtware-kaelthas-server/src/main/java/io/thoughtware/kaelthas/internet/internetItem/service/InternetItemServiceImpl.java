package io.thoughtware.kaelthas.internet.internetItem.service;

import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.kaelthas.internet.internetItem.dao.InternetItemDao;
import io.thoughtware.kaelthas.internet.internetItem.entity.InternetItemEntity;
import io.thoughtware.kaelthas.internet.internetItem.model.InternetItem;
import io.thoughtware.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternetItemServiceImpl implements InternetItemService {

    @Autowired
    private InternetItemDao internetItemDao;

    @Override
    public List<InternetItem> findItemList(InternetItem internetItem) {
        QueryCondition queryCondition = QueryBuilders.createQuery(InternetItemEntity.class)
                .eq("internetType", internetItem.getInternetType())
                .eq("isOptional",internetItem.getIsOptional())
                .get();
        List<InternetItemEntity> entityList = internetItemDao.findItemList(queryCondition);

        return BeanMapper.mapList(entityList, InternetItem.class);
    }
}
