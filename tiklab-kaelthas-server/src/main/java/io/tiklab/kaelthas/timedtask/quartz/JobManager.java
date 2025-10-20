package io.tiklab.kaelthas.timedtask.quartz;

import io.tiklab.kaelthas.util.CronUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class JobManager {

    private static final Logger logger = LoggerFactory.getLogger(JobManager.class);

    private static final SchedulerFactory schedulerFactory =  new StdSchedulerFactory();

    private Scheduler scheduler = null;

    /**
     * @param jobClass  执行不同的任务
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addJob(Class jobClass,  String group) throws SchedulerException {
        String [] taskTypes={"sql","internetOverview","internetInfo","k8sOverview","k8sInfo","updateUsability",
                "dbTrigger","hostTrigger","internetTrigger","k8sTrigger","createDb"};
        List<String> taskTypeList = Arrays.stream(taskTypes).collect(Collectors.toList());
        for (String taskType:taskTypeList){
            String cron;
            if (("internetOverview").equals(taskType)){
                //定时执行采集网络的指标
                 cron="30 0/5 * * * ?";
                //cron="0 0/1 * * * ?";

            }else if(("internetInfo").equals(taskType)||("k8sInfo").equals(taskType)){
                /*
                * 使用定时任务获取配置信息,使用配置信息获取指标数据
                * 定时拉取配置信息,并采集指定的数据
                * */
                cron="0 0/1 * * * ?";

            }else if(("k8sOverview").equals(taskType)||("updateUsability").equals(taskType)||("dbTrigger").equals(taskType)
            ||("hostTrigger").equals(taskType)||("internetTrigger").equals(taskType)||("k8sTrigger").equals(taskType)){
                /*
                 * 定时采集k8s的信息,用于概况页面展示
                 * 定时扫描主机、数据库、k8s、网络的状态并且修改状态
                 * 数据库下定时拉取触发器,触发之后进行告警
                 * 主机下定时拉取触发器,触发之后进行告警
                 * 网络下定时拉取触发器,触发之后进行告警
                 * k8s下定时拉取触发器,触发之后进行告警
                 * */
                cron="0 0/1 * * * ?";

            }else if(("createDb").equals(taskType)){
                //每天凌晨 2 点执行。
                cron="0 0/1 * * * ?";
                //cron="0 0 2 * * ?";
            }else {
                //sql
                cron="0 0/1 * * * ?";
            }


           // 任务名，任务组，任务执行类
            Scheduler scheduler = schedulerFactory.getScheduler();

            Boolean isNewTrigger = false;

            JobKey jobKey = JobKey.jobKey(taskType);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            int size = scheduler.getTriggersOfJob(jobKey).size();

            if (Objects.isNull(jobDetail)){
                JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
                //添加pipelineId执行信息
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("group",group);
                jobDataMap.put("taskType",taskType);
                jobDataMap.put("cron",cron);
                jobBuilder.setJobData(jobDataMap);

                jobDetail = jobBuilder.withIdentity(taskType).build();

                isNewTrigger = true;

            }

            String jobName=taskType+"_"+group;
            // 添加触发器
            addTrigger(scheduler,jobDetail,group,cron,isNewTrigger,jobName);
        }
    }

    /**
     *  添加触发器
     * @param scheduler 定时器
     * @param jobDetail JobDetail
     * @param state 是否为新建trigger
     * @param cron 时间
     * @throws SchedulerException  添加失败
     */
    private void addTrigger( Scheduler scheduler,JobDetail jobDetail,String taskType,String cron,Boolean state,String jobName) throws SchedulerException {


        // 触发器
        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(jobName, taskType) // 触发器名,触发器组
                .withSchedule(CronScheduleBuilder.cronSchedule(cron));// 触发器时间设定

        // Job存在则指定job
        if (!state){
            triggerBuilder.forJob(jobDetail);
        }

        CronTrigger trigger = triggerBuilder.build();

        // 调度容器设置JobDetail和Trigger
        if (state){
            scheduler.scheduleJob(jobDetail, trigger);
        }else {
            scheduler.scheduleJob(trigger);
        }

        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }

    }


    /**
     * @Description: 修改一个任务的触发时间
     * @param jobName
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param cron   时间设置，参考quartz说明文档
     */
    public  void modifyJobTime(String jobName,String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                logger.warn("任务："+jobName+"被修改");

                /** 方式二：先删除，然后在创建一个新的Job  */
                JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                // addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass,cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void removeJob(String group,String triggerName){

        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(group);

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            logger.warn("移除触发器，jobName:"+ triggerName);

            int size = scheduler.getTriggersOfJob(jobKey).size();

            logger.warn("触发器长度：{}",size);
            if (size <= 1){
                scheduler.deleteJob(jobKey);// 删除任务
                logger.warn("Job触发完成：" + jobKey + "，移除Job" );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void removeJob(String jobName, String jobGroupName,String triggerName, String triggerGroupName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     */
    public   void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前正在执行的任务
     * @return
     */
    public  boolean getCurrentJobs(String name){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            List<JobExecutionContext> jobContexts = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext context : jobContexts) {
                if (name.equals(context.getTrigger().getJobKey().getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


}
