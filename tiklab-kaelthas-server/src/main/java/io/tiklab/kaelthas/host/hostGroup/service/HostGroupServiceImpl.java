package io.tiklab.kaelthas.host.hostGroup.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.kaelthas.host.hostGroup.dao.HostGroupDao;
import io.tiklab.kaelthas.host.hostGroup.entity.HostGroupEntity;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 主机组
 */
@Service
@Exporter
public class HostGroupServiceImpl implements HostGroupService {


    @Autowired
    private HostGroupDao hostGroupDao;

    //查询所有主机组信息
    @Override
    public List<HostGroup> findHostGroupByName() {

        List<HostGroupEntity> resData = hostGroupDao.findHostGroupByName();

        if (resData.isEmpty()){
            return Collections.emptyList();
        }

        return BeanMapper.mapList(resData, HostGroup.class);
    }

    /**
     * 根据id查询主机组信息
     */
    @Override
    public HostGroup findHostGroupById(String id) {
        HostGroupEntity hostGroupEntity = hostGroupDao.findHostGroupById(id);

        return BeanMapper.map(hostGroupEntity, HostGroup.class);
    }

    /**
     * 查询所有主机组的list信息
     */
    @Override
    public List<HostGroup> findAllHostGroupList() {
        List<HostGroupEntity> allHostGroupList = hostGroupDao.findAllHostGroupList();
        return BeanMapper.mapList(allHostGroupList,HostGroup.class);
    }

    /**
     * 主机组分页查询
     */
    @Override
    public Pagination<HostGroup> findHostGroupPage(HostGroup hostGroup) {
        QueryCondition queryCondition = QueryBuilders.createQuery(HostGroupEntity.class)
                .like("name", hostGroup.getName())
                .pagination(hostGroup.getPageParam())
                .get();

        Pagination<HostGroupEntity> pagination = hostGroupDao.findHostGroupPage(queryCondition);

        List<HostGroup> hostGroupList = BeanMapper.mapList(pagination.getDataList(), HostGroup.class);
        return PaginationBuilder.build(pagination,hostGroupList);
    }

    /**
     * 创建主机组
     */
    @Override
    public void createHostGroup(HostGroup hostGroup) {
        HostGroupEntity map = BeanMapper.map(hostGroup, HostGroupEntity.class);
        hostGroupDao.createHostGroup(map);
    }

    /**
     * 删除主机组
     */
    @Override
    public void deleteHostGroup(String id) {
        hostGroupDao.deleteHostGroup(id);
    }

    /**
     * 修改主机组
     */
    @Override
    public void updateHostGroup(HostGroup hostGroup) {
        HostGroupEntity hostGroupEntity = BeanMapper.map(hostGroup, HostGroupEntity.class);
        hostGroupDao.updateHostGroup(hostGroupEntity);
    }

    /**
     * 查询主机组数量
     */
    @Override
    public Long findHostGroupAllNum() {
        return hostGroupDao.findHostGroupAllNum();
    }
}
