package io.tiklab.kaelthas.internet.internetItem.service;

import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.internet.internetItem.dao.InternetItemDao;
import io.tiklab.kaelthas.internet.internetItem.entity.InternetItemEntity;
import io.tiklab.kaelthas.internet.internetItem.model.InternetItem;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网络监控项的字典项
 */
@Service
public class InternetItemServiceImpl implements InternetItemService {

    @Autowired
    private InternetItemDao internetItemDao;

    /**
     * 根据网络设备的类型查询网络的监控项字典项
     */
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
