package io.thoughtware.kaelthas.host.platform.service;

import io.thoughtware.licence.appauth.service.ApplyAuthService;
import io.thoughtware.licence.licence.service.VersionService;
import io.thoughtware.message.message.service.MessageNoticeService;
import io.thoughtware.message.setting.service.MessageSendTypeService;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.security.backups.service.BackupsDbService;
import io.thoughtware.user.orga.service.OrgaService;
import io.thoughtware.user.user.service.UserService;
import io.thoughtware.user.usergroup.service.UserGroupService;
import io.thoughtware.kaelthas.host.host.service.HostService;
import io.thoughtware.kaelthas.host.template.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Exporter
public class PlatFormServiceImpl implements PlatFormService {

    @Autowired
    OrgaService orgaService;

    @Autowired
    UserService userService;

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

    @Override
    public Map<String,Object> count() {
        //调用平台的方法查询数据
        //用户与权限
        Map<String, Object> map = new HashMap<>();
        map.put("orgaCount",orgaService.findOrgaNumber());
        map.put("userCount",userService.findUserNumber());
        map.put("userGroupCount",userGroupService.findUserGroupNumber());

        //消息
        map.put("msgNoticeCount",messageNoticeService.findNoticeNumber("kaelthas"));
        map.put("msgSendTypeCount",messageSendTypeService.findSendTypeNumber());


        //应用与安全
        map.put("backupsTime",backupsDbService.findLastBackupsTime());
        map.put("release",versionService.getVersion().getRelease());
        map.put("expired",versionService.getVersion().getExpired());
        map.put("authNumber",applyAuthService.findApplyAuthNumber());

        //项目需要查询的数据
        /*Map<String,Integer> templateMap = templateService.findTemplateNum();

        //查询主机当中的数据
        Map<String,Integer> hostMap = hostService.findHostNumber();

        platForm.setTemplateNum(templateMap.get("tempNum"));
        platForm.setTemplateMonitorNum(templateMap.get("monitorNum"));

        platForm.setHostNum(hostMap.get("hostNum"));
        platForm.setHostMonitorNum(hostMap.get("hostNum"));*/

        return map;
    }
}
