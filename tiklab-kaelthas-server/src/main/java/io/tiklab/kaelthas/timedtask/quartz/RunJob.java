package io.tiklab.kaelthas.timedtask.quartz;

import io.tiklab.kaelthas.db.agent.service.MysqlService;
import io.tiklab.kaelthas.kubernetes.agent.service.ClusterItemOverview;
import io.tiklab.kaelthas.kubernetes.agent.service.GetKuReportData;
import io.tiklab.kaelthas.db.agent.service.PgsqlService;
import io.tiklab.kaelthas.timedtask.timer.service.CreHistoryTable;
import io.tiklab.kaelthas.timedtask.timer.service.EquipmentTimer;
import io.tiklab.kaelthas.db.trigger.service.DbTriggerServiceImpl;
import io.tiklab.kaelthas.host.trigger.service.TriggerServiceImpl;
import io.tiklab.kaelthas.internet.trigger.service.InTriggerServiceImpl;
import io.tiklab.kaelthas.kubernetes.trigger.service.KuTriggerServiceImpl;
import io.tiklab.kaelthas.internet.agent.service.GetInReportDataService;
import io.tiklab.kaelthas.internet.agent.service.SwitchOverviewService;
import io.tiklab.kaelthas.util.ConversionScriptsUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public  class RunJob implements org.quartz.Job {
    private static final Logger logger = LoggerFactory.getLogger(RunJob.class);

    public static   MysqlService mysqlService;

    public static PgsqlService pgsqlService;

    public static GetInReportDataService inReportDataService;

    public static SwitchOverviewService switchOverviewService;

    public static ClusterItemOverview clusterItemOverview;

    public static GetKuReportData getKuReportData;

    public static EquipmentTimer equipmentTimer;

    public static   JobManager jobManager;

    public static DbTriggerServiceImpl dbTriggerServiceImpl;

    public static TriggerServiceImpl triggerServiceImpl;

    public static InTriggerServiceImpl inTriggerServiceImpl;

    public static KuTriggerServiceImpl kuTriggerServiceImpl;

    public static CreHistoryTable creHistoryTable;



    @Autowired
    public void setMysqlService(MysqlService mysqlService) {
        this.mysqlService = mysqlService;
    }

    @Autowired
    public void setPgsqlService(PgsqlService pgsqlService) {
        this.pgsqlService = pgsqlService;
    }

    @Autowired
    public void setInReportDataService(GetInReportDataService inReportDataService) {
        this.inReportDataService = inReportDataService;
    }

    @Autowired
    public void setClusterItemOverview(ClusterItemOverview clusterItemOverview) {
        this.clusterItemOverview = clusterItemOverview;
    }
    @Autowired
    public void setSwitchOverviewService(SwitchOverviewService switchOverviewService) {
        this.switchOverviewService = switchOverviewService;
    }

    @Autowired
    public void setGetKuReportData(GetKuReportData getKuReportData) {
        this.getKuReportData = getKuReportData;
    }
    @Autowired
    public void setEquipmentTimer(EquipmentTimer equipmentTimer) {
        this.equipmentTimer = equipmentTimer;
    }

    @Autowired
    public void setDbTriggerServiceImpl(DbTriggerServiceImpl dbTriggerServiceImpl) {
        this.dbTriggerServiceImpl = dbTriggerServiceImpl;
    }
    @Autowired
    public void setTriggerServiceImpl(TriggerServiceImpl triggerServiceImpl) {
        this.triggerServiceImpl = triggerServiceImpl;
    }
    @Autowired
    public void setInTriggerServiceImpl(InTriggerServiceImpl inTriggerServiceImpl) {
        this.inTriggerServiceImpl = inTriggerServiceImpl;
    }
    @Autowired
    public void setKuTriggerServiceImpl(KuTriggerServiceImpl kuTriggerServiceImpl) {
        this.kuTriggerServiceImpl = kuTriggerServiceImpl;
    }

    @Autowired
    public void setCreHistoryTable(CreHistoryTable creHistoryTable) {
        this.creHistoryTable = creHistoryTable;
    }



    @Autowired
    public void setJobManager(JobManager jobManager) {
        this.jobManager = jobManager;
    }




    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String taskType = (String)map.get("taskType");

        //执行对应的方法
        execMethod(taskType);
    }

    //执行对应的方法
    public void execMethod(String taskType){
        if (("internetOverview").equals(taskType)){
            //定时执行采集网络的指标
            switchOverviewService.executeOverview();

        }else if(("internetInfo").equals(taskType)){
            //使用定时任务获取配置信息,使用配置信息获取指标数据
            inReportDataService.executeSwitchHost();

        }else if(("k8sOverview").equals(taskType)){
            //定时采集k8s的信息,用于概况页面展示
            clusterItemOverview.getClusterOverview();

        }else if(("k8sInfo").equals(taskType)){
            //定时拉取配置信息,并采集指定的数据
            getKuReportData.getKubernetesInfo();

        }else if(("updateUsability").equals(taskType)){
            //定时扫描主机、数据库、k8s、网络的状态并且修改状态
            equipmentTimer.updateUsability();

        }else if(("dbTrigger").equals(taskType)){
            //数据库下定时拉取触发器,触发之后进行告警
            dbTriggerServiceImpl.timerTrigger();

        }else if(("hostTrigger").equals(taskType)){
            //主机下定时拉取触发器,触发之后进行告警
            triggerServiceImpl.insertAlarmForTrigger();

        }else if(("internetTrigger").equals(taskType)){
            //网络下定时拉取触发器,触发之后进行告警
            inTriggerServiceImpl.TimerInTrigger();

        }else if(("k8sTrigger").equals(taskType)){
            //k8s下定时拉取触发器,触发之后进行告警
            kuTriggerServiceImpl.TimerKuTrigger();

        }else if(("createDb").equals(taskType)){
            //动态创建历史表
            creHistoryTable.createTable();
        }else {
            //定时拉取MySQL的的配置数据,并上报数据
            mysqlService.changeDbMysql();
            //定时拉取pgsql的数据,执行SQL进行数据上报
            pgsqlService.changeDbAim();
        }
    }

}



























