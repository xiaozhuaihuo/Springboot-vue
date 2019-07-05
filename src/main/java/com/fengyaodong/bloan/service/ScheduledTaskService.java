package com.fengyaodong.bloan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 定时任务Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/14 10:13
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/14 10:13
 */
@Service
@Slf4j
public class ScheduledTaskService {

    @Autowired
    private PerBillService perBillService;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private BillService billService;

    @Scheduled(cron = "0 30 0 * * ?")
//    @Scheduled(cron = "0/20 * * * * ?")
    public void scheduledInitErrorNumber() {

        log.info("定时更新银行卡密码错误次数任务开始，时间={}", new Date());
        bankCardService.initErrorNumber();
        log.info("定时更新银行卡密码错误次数任务结束，时间={}", new Date());
    }

//    @Scheduled(cron = "0 0/5 * * * ?")
//    @Scheduled(cron = "0/20 * * * * ?")
    public void scheduledUpdateBill() {

        log.info("定时更新总账单信息开始，时间={}", new Date());
        billService.updateBySubBill();
        log.info("定时更新总账单信息结束，时间={}", new Date());
    }

    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0/20 * * * * ?")
    public void scheduledOverdue() {

        Date date = new Date();
        log.info("定时逾期任务开始，时间={}", date);
        //逾期计算与账单更新
        perBillService.updateBillInfoByOverdue(date);
        log.info("定时逾期任务结束，时间={}", new Date());
    }

    @Scheduled(cron = "0 0 8,20 * * ?")
//@Scheduled(cron = "0/20 * * * * ?")
    public void scheduledAutoDeduct() {

        Date date = new Date();
        log.info("定时扣款任务开始，时间={}", date);
        //自动还款
        perBillService.autoRepay(date);
        log.info("定时扣款任务结束，时间={}", new Date());
    }

    @Scheduled(cron = "0 0 10 * * ? ")
//    @Scheduled(cron = "0/20 * * * * ?")
    public void scheduledRepayRemind() {

        Date date = new Date();
        log.info("定时提醒任务开始，时间={}", date);
        //还款提醒
        perBillService.repayRemind(date);
        log.info("定时提醒任务开始，时间={}", new Date());
    }

    @Scheduled(cron = "0 0 1 1 * ? ")
//    @Scheduled(cron = "0/20 * * * * ?")
    public void scheduledCreditScore() {

        log.info("定时更新用户信誉分数开始，时间={}", new Date());
        perBillService.countUserOverdue();
        log.info("定时更新用户信誉分数结束，时间={}", new Date());
    }
}
