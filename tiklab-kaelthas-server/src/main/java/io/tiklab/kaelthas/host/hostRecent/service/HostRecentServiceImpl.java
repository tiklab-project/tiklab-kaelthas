package io.tiklab.kaelthas.host.hostRecent.service;

import io.tiklab.core.page.Page;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.host.hostRecent.dao.HostRecentDao;
import io.tiklab.kaelthas.host.hostRecent.model.HostRecent;
import io.tiklab.kaelthas.host.hostRecent.entity.HostRecentEntity;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 最近使用的主机
 */
@Service
@Exporter
public class HostRecentServiceImpl implements HostRecentService {

    @Autowired
    HostRecentDao hostRecentDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    AlarmService alarmService;

    /**
     * 查询出最近使用的主机list
     */
    @Override
    public List<HostRecent> findRecentHostList() {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostRecentEntity.class)
                .pagination(new Page(1, 5))
                .get();

        Pagination<HostRecentEntity> pagination = hostRecentDao.findRecentHostList(queryCondition);
        List<HostRecent> hostRecents = BeanMapper.mapList(pagination.getDataList(), HostRecent.class);
        if (!hostRecents.isEmpty()) {
            joinTemplate.joinQuery(hostRecents);
        }
        return hostRecents;
    }

    /**
     * 创建最近主机
     */
    @Override
    public String createHostRecent(HostRecent hostRecent) {
        //先查询一下是否已经有当前数据了,没有的话添加上
        QueryCondition queryCondition = QueryBuilders.createQuery(HostRecentEntity.class)
                .eq("hostId", hostRecent.getHostId())
                .get();
        List<HostRecentEntity> hostRecentEntities = hostRecentDao.findRecentList(queryCondition);
        if (hostRecentEntities.isEmpty()) {
            HostRecentEntity recentEntity = BeanMapper.map(hostRecent, HostRecentEntity.class);
            recentEntity.setUpdateTime(ConversionDateUtil.date(9));
            return hostRecentDao.createHostRecent(recentEntity);
        }else {
            HostRecentEntity hostRecentEntity = hostRecentEntities.get(0);
            hostRecentEntity.setUpdateTime(ConversionDateUtil.date(9));
            hostRecentDao.updateHostRecent(hostRecentEntity);
            return "修改时间成功";
        }
    }

    /**
     * 根据id删除最近主机
     */
    @Override
    public void deleteByHostId(String hostId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(HostRecentEntity.class)
                .eq("hostId", hostId)
                .get();
        hostRecentDao.deleteByHostId(deleteCondition);
    }

    /**
     * 查询出最近使用的四个主机信息
     */
    @Override
    public List<HostRecent> findHostRecentList() {
        List<HostRecent> hostRecentList = hostRecentDao.findHostRecentList();
        return hostRecentList;
    }

    /**
     * 根据id查询最近使用主机
     */
    @Override
    public HostRecent findHostRecentOne(String id) {
        HostRecentEntity hostRecentEntity = hostRecentDao.findHostRecentOne(id);
        return BeanMapper.map(hostRecentEntity,HostRecent.class);
    }

    @Override
    public List<HostRecent> findAllHostRecent() {
        List<HostRecentEntity> recentList = hostRecentDao.findAllHostRecent();
        return BeanMapper.mapList(recentList, HostRecent.class);
    }

    /**
     * 修改最近使用的主机信息
     */
    @Override
    public void updateHostRecent(HostRecent hostRecent) {
        HostRecentEntity recentEntity = BeanMapper.map(hostRecent, HostRecentEntity.class);
        recentEntity.setUpdateTime(ConversionDateUtil.date(9));
        hostRecentDao.updateHostRecent(recentEntity);
    }
}
