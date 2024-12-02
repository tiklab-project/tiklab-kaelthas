package io.tiklab.kaelthas.host.host.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.kaelthas.host.hostGroup.service.HostGroupService;
import io.tiklab.kaelthas.host.hostTemplate.service.HostTemplateService;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.host.graphics.model.Graphics;
import io.tiklab.kaelthas.host.graphicsMonitor.service.GraphicsMonitorService;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.graphics.service.GraphicsService;
import io.tiklab.kaelthas.host.host.dao.HostDao;
import io.tiklab.kaelthas.host.host.entity.HostEntity;
import io.tiklab.kaelthas.host.hostTemplate.model.HostTemplate;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.monitor.service.HostMonitorService;
import io.tiklab.kaelthas.host.trigger.model.Trigger;
import io.tiklab.kaelthas.host.trigger.service.TriggerService;
import io.tiklab.kaelthas.util.ConversionDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 主机中的业务
 */
@Service
@Exporter
public class HostServiceImpl implements HostService {

    @Autowired
    private HostDao hostDao;

    @Autowired
    private HostMonitorService monitorService;

    @Autowired
    private JoinTemplate joinTemplate;

    @Autowired
    private TriggerService triggerService;

    @Autowired
    private HostTemplateService hostTemplateService;

    @Autowired
    private GraphicsService graphicsService;


    @Autowired
    DmRoleService dmRoleService;

    @Autowired
    AlarmService alarmService;

    @Autowired
    GraphicsMonitorService graphicsMonitorService;

    @Autowired
    private HostGroupService hostGroupService;


    /**
     * 创建监控主机
     */
    @Override
    public String createHost(Host host) {
        String date = ConversionDateUtil.date(9);
        host.setCreateTime(date);
        host.setColor((int) Math.round(Math.random() * 5));
        HostEntity hostEntity = BeanMapper.map(host, HostEntity.class);

        try {
            //创建主机
            String hostId = hostDao.createHost(hostEntity);
            //附带的有模板的话将模板id和主机id添加到关联表中
            if (StringUtils.isNotBlank(host.getTemplateId())) {
                HostTemplate hostTemplate = new HostTemplate();
                hostTemplate.setHostId(hostId);
                hostTemplate.setTemplateId(host.getTemplateId());
                hostTemplateService.addTemplate(hostTemplate);
                //并且将模板下的监控项复制到主机当中
                List<HostMonitor> monitorList = monitorService.findByHostId(host.getTemplateId());
                for (HostMonitor hostMonitor : monitorList) {
                    hostMonitor.setTemplateId(hostMonitor.getId());
                    hostMonitor.setHostId(hostId);
                    monitorService.createMonitor(hostMonitor);
                }
            }
            dmRoleService.initDmRoles(hostId, "111111", 2);
            return hostId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改主机
     */
    @Override
    public void updateHost(Host host) {
        try {
            HostEntity hostEntity = BeanMapper.map(host, HostEntity.class);
            hostEntity.setUpdateTime(ConversionDateUtil.date(9));
            hostDao.updateHost(hostEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据主机的id进行查询主机下监控项和触发器数量信息
     */
    @Override
    public Host findHostById(String id) {

        HostEntity hostEntity = hostDao.findHostById(id);
        Host host = BeanMapper.map(hostEntity, Host.class);

        //查询主机下监控项
        List<HostMonitor> entityList = monitorService.findMonitorByHostId(id);

        //查询主机下触发器
        List<Trigger> triggerList = triggerService.findTriggerListById(id);

        Alarm alarm = new Alarm();
        alarm.setHostId(id);
        alarm.setStatus(2);
        List<Alarm> alarmList = alarmService.findAlarmList(alarm);

        host.setMonitorNum(entityList.size());

        host.setTriggerNum(triggerList.size());

        host.setAlarmNum(alarmList.size());

        joinTemplate.joinQuery(host);

        return host;

    }

    /**
     * findOne方法
     */
    @Override
    public Host findOneHost(String id) {
        HostEntity hostEntity = hostDao.findOne(id);
        return BeanMapper.map(hostEntity, Host.class);
    }

    /**
     * 根据id删除主机
     */
    @Override
    public void deleteHostById(String id) {

        try {

            //删除主机
            hostDao.deleteHostById(id);

            //删除主机下图形与监控项关联的信息,先根据主机id查询出图形的ids,根据图形的ids删除关联表信息
            List<Graphics> graphicsList = graphicsService.findInformationByGraphics(id);
            List<String> stringList = graphicsList.stream().map(Graphics::getId).toList();
            graphicsMonitorService.deleteByGraphicsIds(stringList);

            //删除图表和删除模板关联
            graphicsService.deleteGraphicsByHostId(id);

            HostTemplate hostTemplate = new HostTemplate();
            hostTemplate.setHostId(id);
            hostTemplateService.deleteByHostId(hostTemplate);

            //删除主机下的触发器
            triggerService.deleteByHostId(id);

            //删除监控项
            monitorService.deleteByHostId(id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据主机ip查询出所有符合ip的主机id
    @Override
    public List<HostMonitor> findMonitorItemListByHostIp(String ip) {

        QueryCondition queryCondition = QueryBuilders.createQuery(HostEntity.class)
                .eq("ip", ip)
                .eq("state", 1)
                .get();

        List<HostEntity> hostEntityList = hostDao.findHostByIp(queryCondition);
        List<String> stringList = hostEntityList.stream().map(HostEntity::getId).toList();

        //根据主机ids查询出主机下的所有监控项
        List<HostMonitor> monitorList = monitorService.findHostMonitorListByHostIds(stringList);
        joinTemplate.joinQuery(monitorList);

        for (HostMonitor monitor : monitorList) {
            monitor.setDataType(monitor.getMonitorItem().getDataType());
            monitor.setCategories(monitor.getMonitorItem().getType());
        }
        return monitorList;
    }

    /**
     * 主机监控的分页查询
     */
    @Override
    public Pagination<Host> findHostPage(Host host) {
        return hostDao.findHostPageForMonitoring(host);
    }

    //findList方法
    @Override
    public List<Host> findList(List<String> ids) {
        List<HostEntity> entityList = hostDao.findList(ids);
        return BeanMapper.mapList(entityList, Host.class);
    }

    //findAll方法
    @Override
    public List<Host> findAllHost() {
        List<HostEntity> all = hostDao.findAll();
        return BeanMapper.mapList(all, Host.class);
    }

    /**
     * 查询主机总数和可用主机数
     */
    @Override
    public Map<String, Long> findHostUsage() {
        //查找主机的总数量
        Map<String, Long> map = new HashMap<>();
        //所有的主机数量
        Long hostCount = hostDao.findHostCount();
        //异常主机数量
        Long hostUsability = hostDao.findHostAbnormal();
        map.put("hostCount", hostCount);
        map.put("hostAbnormal", hostUsability);
        return map;
    }

    /**
     * 查询所有主机组信息
     */
    @Override
    public List<HostGroup> findAllHostGroupList() {
        return hostGroupService.findAllHostGroupList();
    }
}