package com.fengyaodong.bloan.quartz;

import java.util.Date;
import java.util.Map;

/**
 * ${TODO} 写点注释吧
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/7/4 14:51
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/7/4 14:51
 */
public class ScheduleJob {

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";
    private Long jobId;
    private Date createTime;
    private Date updateTime;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 是否启动任务
     */
    private String jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 描述
     */
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;
    /**
     * 任务是否有状态
     */
    private String isConcurrent;
    /**
     * spring bean
     */
    private String springId;
    /**
     * 任务调用的方法名
     */
    private String methodName;

    public ScheduleJob() {

    }

    public ScheduleJob(Long jobId, Date createTime, Date updateTime, String jobName, String jobGroup, String jobStatus,
            String cronExpression, String description, String beanClass, String isConcurrent, String springId,
            String methodName) {
        this.jobId = jobId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobStatus = jobStatus;
        this.cronExpression = cronExpression;
        this.description = description;
        this.beanClass = beanClass;
        this.isConcurrent = isConcurrent;
        this.springId = springId;
        this.methodName = methodName;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public static ScheduleJob transMap2Bean(Map<String, Object> map) {
        ScheduleJob job = new ScheduleJob();
        try {
            job.setJobId((Long) map.get("job_id"));
            job.setJobName((String) map.get("job_name"));
            job.setJobGroup((String) map.get("job_group"));
            job.setJobStatus((String) map.get("job_status"));
            job.setCronExpression((String) map.get("cron_expression"));
            job.setDescription((String) map.get("description"));
            job.setBeanClass((String) map.get("bean_class"));
            job.setIsConcurrent((String) map.get("is_concurrent"));
            job.setSpringId((String) map.get("spring_id"));
            job.setMethodName((String) map.get("method_name"));
            //job.setCreateTime(new Date((String) map.get("create_time")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return job;
    }
}
