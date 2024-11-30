package io.tiklab.kaelthas.host.host.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderTypeEnum;
import io.tiklab.core.page.Page;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import io.tiklab.kaelthas.alarm.model.Alarm;
import io.tiklab.kaelthas.alarm.service.AlarmService;
import io.tiklab.kaelthas.host.dynamic.model.Dynamic;
import io.tiklab.kaelthas.host.dynamic.service.DynamicService;
import io.tiklab.kaelthas.host.graphics.model.Graphics;
import io.tiklab.kaelthas.host.graphicsMonitor.service.GraphicsMonitorService;
import io.tiklab.kaelthas.host.hostRecent.model.HostRecent;
import io.tiklab.kaelthas.host.hostRecent.service.HostRecentService;
import io.tiklab.kaelthas.host.monitor.model.HostMonitor;
import io.tiklab.kaelthas.host.monitorItem.service.MonitorItemService;
import io.tiklab.kaelthas.host.templateMonitor.model.TemplateMonitor;
import io.tiklab.kaelthas.host.templateMonitor.service.TemplateMonitorService;
import io.tiklab.kaelthas.host.graphics.service.GraphicsService;
import io.tiklab.kaelthas.host.host.dao.HostDao;
import io.tiklab.kaelthas.host.host.entity.HostEntity;
import io.tiklab.kaelthas.host.host.model.HostTemplate;
import io.tiklab.kaelthas.host.host.model.Host;
import io.tiklab.kaelthas.host.monitor.service.HostMonitorService;
import io.tiklab.kaelthas.host.trigger.model.Trigger;
import io.tiklab.kaelthas.host.triggerExpression.service.TriggerExpressionService;
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
    private TriggerExpressionService triggerExpressionService;

    @Autowired
    private GraphicsService graphicsService;

    @Autowired
    private TemplateMonitorService templateMonitorService;

    @Autowired
    private MonitorItemService monitorItemService;

    @Autowired
    HostRecentService hostRecentService;

    @Autowired
    DmRoleService dmRoleService;

    @Autowired
    DynamicService dynamicService;

    @Autowired
    AlarmService alarmService;

    @Autowired
    GraphicsMonitorService graphicsMonitorService;

    /**
     * 分页查询
     */
    @Override
    public Pagination<Host> findPageHost(Host host) {
        //查询出来分页数据
        Pagination<Host> pageHost = hostDao.findPageHost(host);

        return pageHost;
    }

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
            Dynamic dynamic = new Dynamic();
            dynamic.setName("创建主机" + host.getName());
            dynamic.setUpdateTime(ConversionDateUtil.date(9));
            dynamicService.createDynamic(dynamic);
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

            hostRecentService.deleteByHostId(id);

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

    //根据主机的监控大类查询,前端已经没有这个业务了
    @Override
    public List<HostMonitor> findMonitorForHost(Host host) {
        //根据监控大类查询出监控项id
        List<String> monitorItemIds = monitorItemService.findMonitorByCategories(host.getDataCate());
        //根据监控项id查询主机当中的监控项和模板当中的监控项
        List<HostMonitor> hostMonitors = monitorService.findmonitorByMonitorItemIds(monitorItemIds, host.getId());

        //根据监控项id查询主机当中模板的监控项
        //查询主机下的模板
        List<HostTemplate> templateForHost = hostTemplateService.findTemplateForHost(host.getId());
        List<HostMonitor> monitors = new ArrayList<>();
        if (!templateForHost.isEmpty()) {
            //模板的ids
            List<String> stringList = templateForHost.stream().map(HostTemplate::getTemplateId).toList();
            List<TemplateMonitor> templateMonitors = templateMonitorService.findMonitorByItemIds(monitorItemIds, stringList);
            monitors = JSON.parseArray(JSON.toJSONString(templateMonitors), HostMonitor.class);
        }

        hostMonitors.addAll(monitors);

        return hostMonitors;
    }

    /**
     * 查询最近的四个主机,用于首页的常用主机展示,前端已经没有这个业务了
     */
    @Override
    public List<Host> findRecentHostList(String hostId) {
        List<Host> summaryList = new ArrayList<>();
        //查询当前主机的信息
        HostEntity entity = hostDao.findHostById(hostId);
        Host map = BeanMapper.map(entity, Host.class);

        //先查询最近的主机,如果最近没有主机的话就按照时间的先后顺序进行查询五条
        List<HostRecent> hostRecentList = hostRecentService.findRecentHostList();

        QueryCondition queryCondition = QueryBuilders.createQuery(HostEntity.class)
                .pagination(new Page(1, 5))
                .order(new Order("createTime", OrderTypeEnum.desc))
                .get();

        Pagination<HostEntity> hostPagination = hostDao.findRecentHostList(queryCondition);

        List<Host> list = BeanMapper.mapList(hostPagination.getDataList(), Host.class);

        summaryList.add(map);
        if (hostRecentList.size() >= 5) {
            List<HostRecent> list1 = hostRecentList.stream().filter(hostRecent -> !hostRecent.getHostId().equals(hostId)).limit(4).toList();
            List<Host> hostList = list1.stream().map(HostRecent::getHost).toList();
            summaryList.addAll(hostList);
            return summaryList;
        } else if (hostRecentList.isEmpty()) {
            List<Host> hosts1 = list.stream().filter(host -> !host.getId().equals(hostId)).limit(4).toList();
            summaryList.addAll(hosts1);
            return summaryList;
        } else {
            //将上面的数据进行转换,然后拼接后面的数据
            List<Host> hostList = new ArrayList<>(hostRecentList.stream().filter(hostRecent -> !hostRecent.getHostId().equals(hostId)).map(HostRecent::getHost).toList());

            List<String> stringList = hostList.stream().map(Host::getId).toList();

            List<Host> hosts = list.stream().filter(host -> !stringList.contains(host.getId()) && !host.getId().equals(hostId)).limit(4 - hostList.size()).toList();
            summaryList.addAll(hostList);
            summaryList.addAll(hosts);
            return summaryList;
        }
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
}