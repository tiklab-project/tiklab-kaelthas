package io.tiklab.kaelthas.host.platform.model;

public class PlatForm {

    //部门数量
    private Integer orgaCount;

    //用户数量
    private Integer userCount;

    //用户组数量
    private Integer userGroupCount;

    //用户目录数量
    private Integer userDirCount;

    //权限数量
    private Integer roleCount;



    //消息通知方案数量
    private Integer msgNoticeCount;

    //消息发送方式数量
    private Integer msgSendTypeCount;

    private Integer templateNum;
    private Integer templateMonitorNum;
    private Integer hostNum;
    private Integer hostMonitorNum;


    //上次备份时间
    private String backupsTime;

    //是否是企业版(1.社区版 2.企业版)
    private Integer release;

    //是否缴费(false为已缴费,true为未缴费)
    private Boolean expired;

    //应用访问权限数量
    private Integer authNumber;


    public Integer getOrgaCount() {
        return orgaCount;
    }

    public void setOrgaCount(Integer orgaCount) {
        this.orgaCount = orgaCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getUserGroupCount() {
        return userGroupCount;
    }

    public void setUserGroupCount(Integer userGroupCount) {
        this.userGroupCount = userGroupCount;
    }

    public Integer getUserDirCount() {
        return userDirCount;
    }

    public void setUserDirCount(Integer userDirCount) {
        this.userDirCount = userDirCount;
    }

    public Integer getRoleCount() {
        return roleCount;
    }

    public void setRoleCount(Integer roleCount) {
        this.roleCount = roleCount;
    }

    public Integer getMsgNoticeCount() {
        return msgNoticeCount;
    }

    public void setMsgNoticeCount(Integer msgNoticeCount) {
        this.msgNoticeCount = msgNoticeCount;
    }

    public Integer getMsgSendTypeCount() {
        return msgSendTypeCount;
    }

    public void setMsgSendTypeCount(Integer msgSendTypeCount) {
        this.msgSendTypeCount = msgSendTypeCount;
    }

    public Integer getTemplateNum() {
        return templateNum;
    }

    public void setTemplateNum(Integer templateNum) {
        this.templateNum = templateNum;
    }

    public Integer getTemplateMonitorNum() {
        return templateMonitorNum;
    }

    public void setTemplateMonitorNum(Integer templateMonitorNum) {
        this.templateMonitorNum = templateMonitorNum;
    }

    public Integer getHostNum() {
        return hostNum;
    }

    public void setHostNum(Integer hostNum) {
        this.hostNum = hostNum;
    }

    public Integer getHostMonitorNum() {
        return hostMonitorNum;
    }

    public void setHostMonitorNum(Integer hostMonitorNum) {
        this.hostMonitorNum = hostMonitorNum;
    }

    public String getBackupsTime() {
        return backupsTime;
    }

    public void setBackupsTime(String backupsTime) {
        this.backupsTime = backupsTime;
    }

    public Integer getRelease() {
        return release;
    }

    public void setRelease(Integer release) {
        this.release = release;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Integer getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(Integer authNumber) {
        this.authNumber = authNumber;
    }
}