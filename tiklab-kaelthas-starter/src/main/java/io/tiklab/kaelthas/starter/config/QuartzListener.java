package io.tiklab.kaelthas.starter.config;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import io.tiklab.kaelthas.timedtask.quartz.JobManager;
import io.tiklab.kaelthas.timedtask.quartz.RunJob;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Scorpio(limingliang)
 * @version 1.0
 * @className QuartzListener
 * @description TODO
 * @date 2021/8/18 10:24
 **/
@Component
public class QuartzListener implements TiklabApplicationRunner {


    @Autowired
    private JobManager jobManager;

    @Override
    public void run() {
        exec();
    }

    //定时任务执行
    public void exec(){
        try {
            jobManager.addJob(RunJob.class,"default");
        } catch (SchedulerException e) {
            throw new ApplicationException(e);
        }
    }
}