package com.fengyaodong.bloan.quartz;

import com.alibaba.fastjson.JSON;
import com.fengyaodong.bloan.common.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * ${TODO} 写点注释吧
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/7/4 15:28
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/7/4 15:28
 */
@Slf4j
@RestController
@RequestMapping("/quartz")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    /**
     * 查询所有的定时任务
     *
     * @param request
     * @return
     */
    @RequestMapping("/task/taskList.do")
    public ModelAndView taskList(HttpServletRequest request) {
        log.info("进入定时任务监控页面");

        List<ScheduleJob> taskList = taskService.getAllJobs();
        ModelAndView view = new ModelAndView();
        view.setViewName("/Contents/task/taskList.jsp");
        view.addObject("taskList", taskList);
        return view;
    }

    /**
     * 添加一个定时任务
     *
     * @param scheduleJob
     * @return retObj
     */
    @RequestMapping("/task/add.do")
    @ResponseBody
    public String addTask(HttpServletRequest request, ScheduleJob scheduleJob) {
        RetObj retObj = new RetObj();
        retObj.setFlag(false);
        try {
            CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        } catch (Exception e) {
            retObj.setMsg("cron表达式有误，不能被解析！");
            return JSON.toJSONString(retObj);
        }

        Object obj = null;
        try {
            if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
                obj = SpringUtils.getBean(scheduleJob.getSpringId());
            } else {
                Class clazz = Class.forName(scheduleJob.getBeanClass());
                obj = clazz.newInstance();
            }
        } catch (Exception e) {
            // do nothing.........
        }
        if (obj == null) {
            retObj.setMsg("未找到目标类！");
            return JSON.toJSONString(retObj);
        } else {
            Class clazz = obj.getClass();
            Method method = null;
            try {
                method = clazz.getMethod(scheduleJob.getMethodName(), null);
            } catch (Exception e) {
                // do nothing.....
            }
            if (method == null) {
                retObj.setMsg("未找到目标方法！");
                return JSON.toJSONString(retObj);
            }
        }

        try {
            taskService.addTask(scheduleJob);
        } catch (Exception e) {
            e.printStackTrace();
            retObj.setFlag(false);
            retObj.setMsg("保存失败，检查 name group 组合是否有重复！");
            return JSON.toJSONString(retObj);
        }

        retObj.setFlag(true);
        return JSON.toJSONString(retObj);
    }

    /**
     * 开启/关闭一个定时任务
     *
     * @param jobId
     * @param cmd
     * @return
     */
    @PostMapping("/changeSts")
    public BaseResult changeJobStatus(Long jobId, String cmd) throws Exception {
        taskService.changeStatus(jobId, cmd);
        return new BaseResult("操作成功");
    }

    /**
     * 修改定时任务的执行时间间隔
     *
     * @param request
     * @param jobId
     * @param cron
     * @return
     */
    @RequestMapping("/task/updateCron.do")
    @ResponseBody
    public String updateCron(HttpServletRequest request, Long jobId, String cron) {
        RetObj retObj = new RetObj();
        retObj.setFlag(false);
        try {
            CronScheduleBuilder.cronSchedule(cron);
        } catch (Exception e) {
            retObj.setMsg("cron表达式有误，不能被解析！");
            return JSON.toJSONString(retObj);
        }
        try {
            taskService.updateCron(jobId, cron);
        } catch (SchedulerException e) {
            retObj.setMsg("cron更新失败！");
            return JSON.toJSONString(retObj);
        }
        retObj.setFlag(true);
        return JSON.toJSONString(retObj);
    }

    @PostMapping("/runNow")
    public BaseResult runNow() throws Exception {

        ScheduleJob job = taskService.getTaskById(1L);
        log.info("任务={}", job);
        taskService.runAJobNow(job);
        return new BaseResult("运行成功");
    }
}
