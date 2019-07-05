package com.fengyaodong.bloan.quartz;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${TODO} 写点注释吧
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/7/4 15:14
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/7/4 15:14
 */
@Mapper
public interface TaskDao {

    List<ScheduleJob> getAllJobs();

    void addTask(ScheduleJob job);

    void updateJobStatusById(@Param("jobId") Long jobId, @Param("sts") String sts);

    ScheduleJob getJobById(Long jobId);

    void updateJobCronById(@Param("jobId") Long jobId, @Param("cron") String cron);
}
