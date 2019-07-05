package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.BaseTest;
import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.model.domain.BillDomain;
import com.fengyaodong.bloan.model.vo.LoanReqVo;
import com.fengyaodong.bloan.model.vo.SumAmtVo;
import com.fengyaodong.bloan.quartz.ScheduleJob;
import com.fengyaodong.bloan.quartz.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 总账单测试类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/5 10:47
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/5 10:47
 */
@Slf4j
public class BillServiceTest extends BaseTest {

    @Autowired
    private BillService billService;

    @Autowired
    private CalculateAmountService calculateAmountService;

    @Autowired
    private PerBillService perBillService;

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private TaskServiceImpl taskService;

    @Test
    public void add() throws Exception{

        ScheduleJob job = taskService.getTaskById(1L);
        taskService.runAJobNow(job);
    }


    @Test
    public void loan() {

        LoanReqVo loanReqVo = new LoanReqVo();
        loanReqVo.setLoanAmount(new BigDecimal(460000));
        loanReqVo.setLoanPeriod(300);
        loanReqVo.setRepayMethod(LoanConstants.AVERAGE_CAPITAL_PLUS_INTEREST);
        String userId = "7b6eb3a2974a4b0391c0218a0028de97";

        billService.initBill(loanReqVo,userId);
    }

    @Test
    public void calculate() {

        BillDomain billDomain = new BillDomain();
        billDomain.setBillPrincipalAmount(new BigDecimal(1000000));
        billDomain.setStagesNumber(360);
        billDomain.setRepayStagesNumber(10);

        BigDecimal advanceRepayAmt = calculateAmountService.advanceCalculateEqualPrincipal(billDomain);
        log.info("一次性还款总额={}", advanceRepayAmt);
    }

    @Test
    public void getTime(){

        Date date = new Date();
        log.info("日期={}", date);
    }


    @Test
    public void scheduledTask() {

       scheduledTaskService.scheduledCreditScore();
    }

    @Test
    public void countAmt() {

        SumAmtVo sumAmtVo = billService.countAmtByUserId("62c36b74e4c744e6980d282f2472c2e2");
        log.info("金额={}", sumAmtVo);
    }
}
