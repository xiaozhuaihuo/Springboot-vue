package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.exception.BankCardException;
import com.fengyaodong.bloan.common.exception.BusinessException;
import com.fengyaodong.bloan.common.util.DateUtils;
import com.fengyaodong.bloan.common.util.DesUtil;
import com.fengyaodong.bloan.common.util.SerialNoHelper;
import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.BillMapper;
import com.fengyaodong.bloan.model.domain.*;
import com.fengyaodong.bloan.model.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 总账单Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/5 10:44
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/5 10:44
 */
@Service
@Slf4j
public class BillService implements AbstractService<BillDomain, String> {


    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private BillMapper billMapper;

    @Autowired
    private CalculateAmountService calculateAmountService;

    @Autowired
    private PerBillService perBillService;

    @Autowired
    private UserQuotaService userQuotaService;

    @Autowired
    private BindCardService bindCardService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private DictionaryService dictionaryService;

    @Override
    public BillMapper getMapper() {
        return billMapper;
    }

    /**
     * 计算账单总金额与还款总金额
     *
     * @param userId
     * @return
     */
    public SumAmtVo countAmtByUserId(String userId) {
        return billMapper.countAmtByUserId(userId);

    }

    /**
     * 更新自动还款状态
     *
     * @param isAutoRepay
     * @param userId
     */
    public void autoRepayByUserId(String isAutoRepay, String userId) {
        billMapper.autoRepayByUserId(isAutoRepay, userId);
    }

    /**
     * 根据子账单更新所有父账单信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateBySubBill() {
        billMapper.updateBySubBill();
        billMapper.updateStatus();
    }

    /**
     * 更新账单信息（提前结清）
     *
     * @param repayVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateBillInfoAdvanceRepay(RepayVo repayVo) {

        BillDomain billDomain = billMapper.findOneById(repayVo.getBillNoList().get(0));
        billDomain.setBillStatus(LoanConstants.IS_CLEAR);
        billDomain.setIsAdvanceSettle(LoanConstants.IS_ADVANCE);
        billDomain.setAdvanceRepayAmt(repayVo.getRepayAmt());
        billDomain.setRepayDate(new Date());

        log.info("更新父账单信息，父账单信息={}", billDomain);
        billMapper.updateById(billDomain);
        //更新所有子账单信息
        perBillService.updateAllSubBillInfo(repayVo.getBillNoList().get(0));
        //更新用户额度
        userQuotaService.updateQuotaRepay(billDomain.getBillPrincipalAmount(), billDomain.getUserId());

    }

    /**
     * 根据账单号计算一键还清总金额
     *
     * @param billNo
     * @return
     */
    public BigDecimal calculateAdvanceRepayAmt(String billNo) {

        BillDomain billDomain = billMapper.findOneById(billNo);
        //提前还清总金额
        BigDecimal advanceRepayAmt;
        //判断该账单的还款方式进行一键提前结清计算
        if (LoanConstants.AVERAGE_CAPITAL_PLUS_INTEREST.equals(billDomain.getRepayMethod())) {
            advanceRepayAmt = calculateAmountService.advanceCalculateEqualPrincipalAndInterest(billDomain);
        } else {
            advanceRepayAmt = calculateAmountService.advanceCalculateEqualPrincipal(billDomain);
        }
        return advanceRepayAmt;
    }

    /**
     * 贷款成功后初始化账单信息
     *
     * @param loanReqVo
     * @param userId
     */
    public void initBill(LoanReqVo loanReqVo, String userId) {

        BillDomain billDomain = new BillDomain();
        DateReqVo dateReqVo = new DateReqVo();
        Date date = new Date();
        billDomain.setBillPrincipalAmount(loanReqVo.getLoanAmount());
        billDomain.setStagesNumber(loanReqVo.getLoanPeriod());
        billDomain.setRepayMethod(loanReqVo.getRepayMethod());
        billDomain.setBillNo(SerialNoHelper.getDefaultSerialNo(LoanConstants.BILL_PREFIX));
        billDomain.setUserId(userId);
        billDomain.setBillRegisterDate(date);
        billDomain.setBillStatus(LoanConstants.NOT_CLEAR);
        dateReqVo.setDate(date);
        dateReqVo.setStageNo(billDomain.getStagesNumber());
        DateResVo resVo = dateFormat(dateReqVo);
        billDomain.setBillFinalRepayDate(resVo.getEndDate());

        //如果还款方式为等额本息，则选用等额本息计算总金额与利息
        LoanResVo loanResVo;
        if (LoanConstants.AVERAGE_CAPITAL_PLUS_INTEREST.equals(loanReqVo.getRepayMethod())) {
            loanResVo = calculateAmountService.calculateEqualPrincipalAndInterest(loanReqVo);
            //如果还款方式为等额本金，则选用等额本金计算总金额与利息
        } else {
            loanResVo = calculateAmountService.calculateEqualPrincipal(loanReqVo);
        }

        billDomain.setBillTotalAmount(loanResVo.getRepayTotalAmount());
        billDomain.setBillInterestAmount(loanResVo.getRepayInterestAmount());
        billDomain.setBillFineAmount(BigDecimal.ZERO);
        billDomain.setBillFeeAmount(BigDecimal.ZERO);
        billDomain.setRepayTotalAmount(BigDecimal.ZERO);
        billDomain.setRepayPrincipalAmount(BigDecimal.ZERO);
        billDomain.setRepayInterestAmount(BigDecimal.ZERO);
        billDomain.setRepayFineAmount(BigDecimal.ZERO);
        billDomain.setRepayFeeAmount(BigDecimal.ZERO);
        billDomain.setRepayStagesNumber(0);
        billDomain.setIsAdvanceSettle(LoanConstants.NOT_ADVANCE);
        billDomain.setIsAutoRepay(LoanConstants.NOT_AUTO_REPAY);

        billMapper.add(billDomain);
        //初始化子账单
        initSubBill(billDomain, loanResVo);

    }

    /**
     * 初始化子账单信息
     *
     * @param billDomain
     * @param loanResVo
     */
    public void initSubBill(BillDomain billDomain, LoanResVo loanResVo) {

        PerBillDomain perBillDomain = new PerBillDomain();
        DateReqVo dateReqVo = new DateReqVo();

        for (int i = 1; i <= billDomain.getStagesNumber(); i++) {
            perBillDomain.setPerBillNo(SerialNoHelper.getDefaultSerialNo());
            perBillDomain.setBillNo(billDomain.getBillNo());
            perBillDomain.setUserId(billDomain.getUserId());
            dateReqVo.setDate(billDomain.getBillRegisterDate());
            dateReqVo.setStageNo(i);
            DateResVo dateResVo = dateFormat(dateReqVo);
            perBillDomain.setPerStartDate(dateResVo.getStartDate());
            perBillDomain.setPerEndDate(dateResVo.getEndDate());
            perBillDomain.setPerStatus(LoanConstants.NOT_CLEAR);
            perBillDomain.setCurrentStage(i);
            perBillDomain.setPerBillTotalAmount(loanResVo.getPerMonthRepayVoList().get(i - 1).getPerRepayTotalAmt());
            perBillDomain
                    .setPerBillPrincipalAmount(loanResVo.getPerMonthRepayVoList().get(i - 1).getPerRepayCapitalAmt());
            perBillDomain
                    .setPerBillInterestAmount(loanResVo.getPerMonthRepayVoList().get(i - 1).getPerRepayInterestAmt());
            perBillDomain.setPerBillFineAmount(BigDecimal.ZERO);
            perBillDomain.setPerBillFeeAmount(BigDecimal.ZERO);
            perBillDomain.setPerRepayTotalAmount(BigDecimal.ZERO);
            perBillDomain.setPerRepayPrincipalAmount(BigDecimal.ZERO);
            perBillDomain.setPerRepayInterestAmount(BigDecimal.ZERO);
            perBillDomain.setPerRepayFineAmount(BigDecimal.ZERO);
            perBillDomain.setPerRepayFeeAmount(BigDecimal.ZERO);
            perBillDomain.setIsOverdue(LoanConstants.NOT_OVERDUE);
            perBillDomain.setIsAdvanceSettle(LoanConstants.NOT_ADVANCE);
            perBillDomain.setIsAutoRepay(LoanConstants.NOT_AUTO_REPAY);

            perBillService.add(perBillDomain);
        }
    }

    /**
     * 请求贷款验证并返回对应信息
     *
     * @param userId
     * @return
     */
    public Map<String, Object> userRequestLoan(String userId) {

        //用户所有绑卡信息
        List<BindCardDomain> bindCardDomainList = bindCardService.findAllByUserId(userId);
        //用户默认卡信息
        BindCardDomain bindCardDomain = bindCardService.findAllByUserIdAndDefault(userId);
        //用户额度信息
        UserQuotaDomain userQuotaDomain = userQuotaService.findOneById(userId);
        if (bindCardDomainList.size() <= 0) {
            throw new BusinessException("您没有绑定银行卡，请先绑定银行卡");
        }
        if (bindCardDomain == null) {
            throw new BusinessException("请设置默认卡");
        }
        if (userQuotaDomain == null) {
            throw new BusinessException("您没有贷款额度，请先申请额度");
        }

        Map<String, String> repayMethodMap = dictionaryService.findByParentCode(LoanConstants.REPAY_METHOD);
        Map<Integer, String> repayStagesMap = new HashMap<>(10);
        repayStagesMap.put(1, "1期(1月)");
        repayStagesMap.put(3, "3期(3月)");
        repayStagesMap.put(6, "6期(6月)");
        repayStagesMap.put(12, "12期(1年)");
        repayStagesMap.put(24, "24期(2年)");
        repayStagesMap.put(36, "36期(3年)");
        repayStagesMap.put(48, "48期(4年)");
        repayStagesMap.put(60, "60期(5年)");
        repayStagesMap.put(120, "120期(10年)");
        Map<String, Object> map = new HashMap<>();
        map.put("repayMethod", repayMethodMap);
        map.put("repayStages", repayStagesMap);

        return map;
    }

    /**
     * 贷款
     *
     * @param loanReqVo
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void userLoan(LoanReqVo loanReqVo, String userId) {

        //用户额度信息
        UserQuotaDomain userQuotaDomain = userQuotaService.findOneById(userId);

        //用户默认卡信息
        BindCardDomain bindCardDomain = bindCardService.findAllByUserIdAndDefault(userId);

        if (null == userQuotaDomain) {
            throw new BusinessException("您没有贷款额度，请先申请额度");
        }
        if (loanReqVo.getLoanAmount().compareTo(userQuotaDomain.getLoanSurplusQuota()) == 1) {
            throw new BusinessException("您的额度不足，无法贷款");
        }
        if (null == bindCardDomain) {
            throw new BusinessException("您没有设置默认银行卡，请先设置默认卡");
        }
        //修改管理员余额
        adminService.updateAdminForLoan(loanReqVo.getLoanAmount());
        //修改用户额度信息
        userQuotaService.updateQuotaLoan(userQuotaDomain, loanReqVo.getLoanAmount());
        //更新用户银行卡余额
        bankCardService.updateBalanceForLoan(bindCardDomain.getBankCardNo(), loanReqVo.getLoanAmount());
        //初始化账单信息
        initBill(loanReqVo, userId);
    }

    /**
     * 还款
     *
     * @param repayVo
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BankCardException.class)
    public void userRepay(RepayVo repayVo, String userId) throws Exception {

        //获取还款卡信息
        BankCardDomain bankCardDomain = new BankCardDomain();
        bankCardDomain.setBankCardNo(repayVo.getRepayCardNo());
        bankCardDomain.setBankCardPassword(DesUtil.encrypt(repayVo.getRepayPassword()));
        //更新用户银行卡余额
        bankCardService.updateBalanceForRepay(bankCardDomain, repayVo.getRepayAmt());
        //更新管理员余额
        adminService.updateAdminForRepay(repayVo.getRepayAmt());

        //更新账单信息
        switch (repayVo.getRepayForm()) {
            //立即还款
            case LoanConstants.IMMEDIATE_REPAY:
                //一键还款（所有期）
            case LoanConstants.ONE_KEY_REPAY:
                //一键还款（逾期）
            case LoanConstants.ONE_KEY_REPAY_OVERDUE:
                perBillService.updateAllBillInfo(repayVo.getBillNoList());
                break;
            //提前结清
            case LoanConstants.ADVANCE_REPAY:
                updateBillInfoAdvanceRepay(repayVo);
                break;
            default:
        }
    }

    /**
     * 设置自动还款
     *
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void setOpenAutoRepay(String userId) {

        UserInfoDomain domain = new UserInfoDomain();
        domain.setIsAutoRepay(LoanConstants.IS_AUTO_REPAY);
        domain.setUserId(userId);
        userInfoService.updateById(domain);
        billMapper.autoRepayByUserId(LoanConstants.IS_AUTO_REPAY, userId);
        perBillService.autoRepayByUserId(LoanConstants.IS_AUTO_REPAY, userId);
    }

    /**
     * 停止自动还款
     *
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void setStopAutoRepay(String userId) {

        UserInfoDomain domain = new UserInfoDomain();
        domain.setIsAutoRepay(LoanConstants.NOT_AUTO_REPAY);
        domain.setUserId(userId);
        userInfoService.updateById(domain);
        billMapper.autoRepayByUserId(LoanConstants.NOT_AUTO_REPAY, userId);
        perBillService.autoRepayByUserId(LoanConstants.NOT_AUTO_REPAY, userId);
    }

    /**
     * 初始化日期并设置格式
     *
     * @param reqVo
     * @return
     */
    public DateResVo dateFormat(DateReqVo reqVo) {

        DateResVo resVo = new DateResVo();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reqVo.getDate());
        calendar.add(Calendar.MONTH, reqVo.getStageNo() - 1);
        resVo.setStartDate(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        resVo.setEndDate(calendar.getTime());

        resVo.setStartDate(DateUtils.getDateStart(resVo.getStartDate()));
        resVo.setEndDate(DateUtils.getDateEnd(resVo.getEndDate()));

        return resVo;
    }
}