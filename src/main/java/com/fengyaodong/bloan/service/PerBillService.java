package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.PerBillMapper;
import com.fengyaodong.bloan.model.condition.PerBillCondition;
import com.fengyaodong.bloan.model.domain.*;
import com.fengyaodong.bloan.model.vo.CountOverdueVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 每期账单Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/5 15:15
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/5 15:15
 */
@Service
@Slf4j
public class PerBillService implements AbstractService<PerBillDomain, String> {

    @Autowired
    private PerBillMapper perBillMapper;

    @Autowired
    private BillService billService;

    @Autowired
    private CalculateAmountService calculateAmountService;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BindCardService bindCardService;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserQuotaService userQuotaService;

    @Override
    public PerBillMapper getMapper() {
        return perBillMapper;
    }

    /**
     * 根据当前日期查询未结清与已逾期的账单
     *
     * @return
     */
    public List<PerBillDomain> findAllByDate(PerBillCondition condition) {
        Date date = new Date();
        condition.setDate(date);
        return perBillMapper.findAllByDate(condition);
    }

    /**
     * 查询已结清账单
     *
     * @param userId
     * @return
     */
    public List<PerBillDomain> findClearBill(String userId) {
        return perBillMapper.findClearBill(userId);
    }

    /**
     * 根据当前日期查询未结清的子账单与已逾期的子账单应还总金额
     *
     * @param condition
     * @return
     */
    public BigDecimal countTotAmtByDate(PerBillCondition condition) {

        return perBillMapper.countTotAmtByDate(condition);
    }

    /**
     * 更新自动还款状态
     *
     * @param isAutoRepay
     * @param userId
     */
    public void autoRepayByUserId(String isAutoRepay, String userId) {
        perBillMapper.autoRepayByUserId(isAutoRepay, userId);
    }

    /**
     * 根据当前日期查询未结清与已逾期的自动还款账单
     *
     * @param date
     * @return
     */
    public List<PerBillDomain> findAutoRepayByDate(Date date) {
        return perBillMapper.findAutoRepayByDate(date);
    }

    /**
     * 根据当前日期查询当期未结清的账单
     *
     * @param date
     * @return
     */
    public List<PerBillDomain> findNotAutoByDate(Date date) {
        return perBillMapper.findNotAutoByDate(date);
    }

    /**
     * 根据当前日期查询之前所有未结清账单
     *
     * @param date
     * @return
     */
    public List<PerBillDomain> findAllByDateBefore(Date date) {
        return perBillMapper.findAllByDateBefore(date);
    }

    /**
     * 根据总账单号统计未结清已逾期的账单数
     *
     * @param billNo
     * @return
     */
    public Integer countAllByOverdue(String billNo) {
        return perBillMapper.countAllByOverdue(billNo);
    }

    /**
     * 计算用户逾期账单总数
     *
     * @return
     */
    List<CountOverdueVo> countOverdue() {
        return perBillMapper.countOverdue();
    }

    /**
     * 根据父账单号更新所有子账单信息
     *
     * @param perBillDomain
     */
    public void updateByBillNo(PerBillDomain perBillDomain) {
        perBillMapper.updateByBillNo(perBillDomain);
    }

    /**
     * 立即还款（一期）与 一键还款（当前所有期） 更新账单信息
     *
     * @param billNoList
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAllBillInfo(List<String> billNoList) {

        for (String billNo : billNoList) {
            PerBillDomain perBillDomain = findOneById(billNo);
            perBillDomain.setPerStatus(LoanConstants.IS_CLEAR);
            perBillDomain.setPerRepayTotalAmount(perBillDomain.getPerBillTotalAmount());
            perBillDomain.setPerRepayPrincipalAmount(perBillDomain.getPerBillPrincipalAmount());
            perBillDomain.setPerRepayInterestAmount(perBillDomain.getPerBillInterestAmount());
            perBillDomain.setPerRepayFeeAmount(perBillDomain.getPerBillFeeAmount());
            perBillDomain.setPerRepayFineAmount(perBillDomain.getPerBillFineAmount());
            perBillDomain.setPerRepayDate(new Date());

            //更新子账单信息
            log.info("更新子账单信息，子账单信息={}", perBillDomain);
            perBillMapper.updateById(perBillDomain);
            //更新用户额度
            userQuotaService.updateQuotaRepay(perBillDomain.getPerRepayPrincipalAmount(), perBillDomain.getUserId());
        }
    }

    /**
     * 更新所有子账单信息
     *
     * @param billNo
     */
    public void updateAllSubBillInfo(String billNo) {

        PerBillDomain perBillDomain = new PerBillDomain();
        perBillDomain.setBillNo(billNo);
        perBillDomain.setPerStatus(LoanConstants.IS_CLEAR);
        perBillDomain.setIsAdvanceSettle(LoanConstants.IS_ADVANCE);
        perBillDomain.setPerRepayDate(new Date());

        log.info("更新所有子账单信息，更新信息={}", perBillDomain);
        updateByBillNo(perBillDomain);
    }

    /**
     * 定时计算逾期任务
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateBillInfoByOverdue(Date date) {

        BigDecimal fineAmt = new BigDecimal(0);
        UserMsgDomain userMsgDomain = new UserMsgDomain();
        List<PerBillDomain> perBillDomainList = findAllByDateBefore(date);
        if (null == perBillDomainList || perBillDomainList.size() == 0) {
            return;
        }

        for (PerBillDomain domain : perBillDomainList) {

            Long days = (date.getTime() - domain.getPerEndDate().getTime()) / (1000 * 3600 * 24) + 1;
            //滞纳金计算
            BigDecimal feeAmt = calculateAmountService.calculateFee(domain.getPerBillTotalAmount(), days);
            //滞纳金增额
            BigDecimal increaseFeeAmt = feeAmt.subtract(domain.getPerBillFeeAmount());
            if (LoanConstants.NOT_OVERDUE.equals(domain.getIsOverdue())) {
                domain.setIsOverdue(LoanConstants.IS_OVERDUE);
            }
            if (BigDecimal.ZERO.compareTo(domain.getPerBillFineAmount()) == 0) {
                //罚金计算
                fineAmt = calculateAmountService.calculateFine(domain.getPerBillTotalAmount());
                domain.setPerBillFineAmount(fineAmt);
            }
            domain.setPerBillTotalAmount(domain.getPerBillTotalAmount().add(increaseFeeAmt).add(fineAmt));
            domain.setPerBillFeeAmount(feeAmt);
            domain.setOverdueDay(days.intValue());

            log.info("逾期跑批更新子账单信息，子账单信息={}", domain);
            perBillMapper.updateById(domain);

            //添加用户逾期消息
            userMsgDomain.setBillNo(domain.getPerBillNo());
            userMsgDomain.setUserId(domain.getUserId());
            userMsgDomain.setMsgType(LoanConstants.OVERDUE_REMIND);
            userMsgService.addUserMsg(userMsgDomain);
        }
    }

    /**
     * 定时自动还款任务
     *
     * @param date
     */
    @Transactional(rollbackFor = Exception.class)
    public void autoRepay(Date date) {

        List<String> billNoList = new ArrayList<>();
        UserMsgDomain userMsgDomain = new UserMsgDomain();
        List<PerBillDomain> perBillDomainList = findAutoRepayByDate(date);
        if (null == perBillDomainList || perBillDomainList.size() == 0) {
            return;
        }

        for (PerBillDomain domain : perBillDomainList) {

            Long days = (domain.getPerEndDate().getTime() - date.getTime()) / (1000 * 3600 * 24);
            if (days <= 3) {

                BindCardDomain bindCardDomain = bindCardService.findAllByUserIdAndDefault(domain.getUserId());
                BankCardDomain bankCardDomain = bankCardService.findOneById(bindCardDomain.getBankCardNo());
                if (domain.getPerBillTotalAmount().compareTo(bankCardDomain.getBankCardBalance()) == 1) {
                    userMsgDomain.setUserId(domain.getUserId());
                    userMsgDomain.setBillNo(domain.getPerBillNo());
                    userMsgDomain.setMsgType(LoanConstants.DEEUCT_REMIND);
                    userMsgService.addUserMsg(userMsgDomain);
                } else {
                    //更新银行卡余额
                    BankCardDomain bankCardDomainNow = new BankCardDomain();
                    bankCardDomainNow.setBankCardNo(bankCardDomain.getBankCardNo());
                    bankCardService.updateBalanceForRepay(bankCardDomainNow, domain.getPerBillTotalAmount());
                    //更新管理员余额
                    adminService.updateAdminForRepay(domain.getPerBillTotalAmount());
                    billNoList.add(domain.getPerBillNo());
                }
            }
        }
        if (null == billNoList || billNoList.size() == 0) {
            return;
        }
        //更新所有账单信息
        updateAllBillInfo(billNoList);
    }

    /**
     * 定时还款提醒任务
     *
     * @param date
     */
    @Transactional(rollbackFor = Exception.class)
    public void repayRemind(Date date) {

        UserMsgDomain userMsgDomain = new UserMsgDomain();
        List<PerBillDomain> perBillDomainList = findNotAutoByDate(date);
        if (null == perBillDomainList || perBillDomainList.size() == 0) {
            return;
        }

        for (PerBillDomain perBillDomain : perBillDomainList) {

            Long days = (perBillDomain.getPerEndDate().getTime() - date.getTime()) / (1000 * 3600 * 24);
            if (days <= 3) {
                userMsgDomain.setUserId(perBillDomain.getUserId());
                userMsgDomain.setBillNo(perBillDomain.getPerBillNo());
                userMsgDomain.setMsgType(LoanConstants.REPAY_REMIND);
                userMsgService.addUserMsg(userMsgDomain);
            }
        }
    }

    /**
     * 更新用户信誉分数
     */
    public void countUserOverdue() {

        List<CountOverdueVo> list = perBillMapper.countOverdue();
        log.info("更新信息={}", list);

        if (null == list || list.size() == 0) {
            return;
        }

        for (CountOverdueVo vo : list) {

            UserInfoDomain userInfoDomain = userInfoService.findOneById(vo.getUserId());

            if (vo.getOverdueNumber() >= LoanConstants.MAX_OVERDUE_NUMBER) {
                //信誉分最低值设为最大逾期数时的分数
                userInfoDomain.setCreditScore(LoanConstants.TOT_CREDIT_SCORE - LoanConstants.MAX_OVERDUE_NUMBER * 5);
            } else {
                //如果小于最大逾期数，则按实际逾期数扣分
                userInfoDomain.setCreditScore(LoanConstants.TOT_CREDIT_SCORE - vo.getOverdueNumber() * 5);
            }
            userInfoService.updateById(userInfoDomain);
        }
    }
}
