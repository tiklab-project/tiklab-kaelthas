package io.tiklab.kaelthas.setting.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.host.hostGroup.model.HostGroup;
import io.tiklab.kaelthas.host.hostGroup.service.HostGroupService;
import io.tiklab.kaelthas.host.monitor.model.MonitorQuery;
import io.tiklab.kaelthas.host.template.model.Template;
import io.tiklab.licence.appauth.service.ApplyAuthService;
import io.tiklab.licence.licence.service.VersionService;
import io.tiklab.message.message.service.MessageNoticeService;
import io.tiklab.message.setting.service.MessageSendTypeService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.security.backups.service.BackupsDbService;
import io.tiklab.user.orga.service.OrgaService;
import io.tiklab.user.user.service.UserProcessor;
import io.tiklab.user.usergroup.service.UserGroupService;
import io.tiklab.kaelthas.host.host.service.HostService;
import io.tiklab.kaelthas.host.template.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置中的首页
 */
@Service
@Exporter
public class SettingServiceImpl implements SettingService {

    @Autowired
    OrgaService orgaService;

    @Autowired
    UserProcessor userProcessor;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    MessageNoticeService messageNoticeService;

    @Autowired
    MessageSendTypeService messageSendTypeService;

    @Autowired
    BackupsDbService backupsDbService;

    @Autowired
    VersionService versionService;

    @Autowired
    ApplyAuthService applyAuthService;

    @Autowired
    TemplateService templateService;

    @Autowired
    HostService hostService;

    @Autowired
    private HostGroupService hostGroupService;

    /**
     * 设置中的首页查询信息
     */
    @Override
    public Map<String,Object> count() {
        //调用平台的方法查询数据
        //用户与权限
        Map<String, Object> map = new HashMap<>();
        map.put("orgaCount",orgaService.findOrgaNumber());
        map.put("userCount",userProcessor.findUserNumber());
        map.put("userGroupCount",userGroupService.findUserGroupNumber());

        //消息
        map.put("msgNoticeCount",messageNoticeService.findNoticeNumber("kaelthas"));
        map.put("msgSendTypeCount",messageSendTypeService.findSendTypeNumber());


        //应用与安全
        map.put("backupsTime",backupsDbService.findLastBackupsTime());
        map.put("release",versionService.getVersion().getRelease());
        map.put("expired",versionService.getVersion().getExpired());
        map.put("authNumber",applyAuthService.findApplyAuthNumber());

        return map;
    }

    /**
     * 主机组分页查询
     */
    @Override
    public Pagination<HostGroup> findHostGroupPage(HostGroup hostGroup) {
        return hostGroupService.findHostGroupPage(hostGroup);
    }

    /**
     * 根据查询条件查询出主机当中的模板
     */
    @Override
    public Pagination<Template> findTemplate(MonitorQuery query) {
        return templateService.findTemplate(query);
    }

    /**
     * 创建模板
     */
    @Override
    public String createTemplate(Template template) {
        return templateService.createTemplate(template);
    }

    /**
     * 修改模板
     */
    @Override
    public void updateTemplate(Template template) {
        templateService.updateTemplate(template);
    }

    /**
     * 删除模板
     */
    @Override
    public void deleteTemplate(String id) {
        templateService.deleteTemplate(id);
    }

    /**
     * 创建主机组
     */
    @Override
    public void createHostGroup(HostGroup hostGroup) {
        hostGroupService.createHostGroup(hostGroup);
    }

    /**
     * 删除主机组
     */
    @Override
    public void deleteHostGroup(String id) {
        hostGroupService.deleteHostGroup(id);
    }

    /**
     * 修改主机组
     */
    @Override
    public void updateHostGroup(HostGroup hostGroup) {
        hostGroupService.updateHostGroup(hostGroup);
    }

    @Override
    public Template findTemplateById(String id) {
        Template template = templateService.findTemplate(id);
        return template;
    }
}
