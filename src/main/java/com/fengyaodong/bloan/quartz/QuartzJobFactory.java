package com.fengyaodong.bloan.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * ${TODO} 写点注释吧
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/7/4 14:52
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/7/4 14:52
 */
public class QuartzJobFactory implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);
    }
}
