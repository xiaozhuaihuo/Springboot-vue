package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.model.domain.BillDomain;
import com.fengyaodong.bloan.model.vo.LoanReqVo;
import com.fengyaodong.bloan.model.vo.LoanResVo;
import com.fengyaodong.bloan.model.vo.PerMonthRepayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 计算金额Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 10:39
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 10:39
 */
@Service
public class CalculateAmountService {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 等额本息计算
     *
     * @param reqVo
     * @return
     */
    public LoanResVo calculateEqualPrincipalAndInterest(LoanReqVo reqVo) {

        List<PerMonthRepayVo> perMonthRepayVoList = new ArrayList<>();
        BigDecimal months = BigDecimal.valueOf(reqVo.getLoanPeriod());
        //年利率
        BigDecimal yearRate = (dictionaryService.findValueByCode(LoanConstants.INTEREST_RATE))
                .divide(LoanConstants.bc, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //月利率
        BigDecimal monthRate = yearRate.divide(LoanConstants.MONTHS, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //每月还款总额
        BigDecimal perRepayTotAmt = reqVo.getLoanAmount().multiply(monthRate)
                .multiply((BigDecimal.ONE.add(monthRate).pow(reqVo.getLoanPeriod())))
                .divide(((BigDecimal.ONE.add(monthRate)).pow(reqVo.getLoanPeriod())).subtract(BigDecimal.ONE),
                        LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        for (Integer i = 1; i <= reqVo.getLoanPeriod(); i++) {
            PerMonthRepayVo perMonthRepayVo = new PerMonthRepayVo();
            //每月还款本金金额
            BigDecimal perRepayCapitalAmt = (reqVo.getLoanAmount().multiply(monthRate)
                    .multiply((BigDecimal.ONE.add(monthRate)).pow(i - 1)))
                    .divide((BigDecimal.ONE.add(monthRate)).pow(reqVo.getLoanPeriod()).subtract(BigDecimal.ONE),
                            LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
            BigDecimal perRepayInterestAmt = perRepayTotAmt.subtract(perRepayCapitalAmt);

            //为每月还款信息赋值
            perMonthRepayVo.setPerRepayTotalAmt(perRepayTotAmt);
            perMonthRepayVo.setPerRepayCapitalAmt(perRepayCapitalAmt);
            perMonthRepayVo.setPerRepayInterestAmt(perRepayInterestAmt);
            perMonthRepayVo.setRepayStage(i);
            perMonthRepayVoList.add(perMonthRepayVo);
        }

        //总还款金额
        BigDecimal repayTotalAmt = perRepayTotAmt.multiply(months);
        //总还款利息金额
        BigDecimal repayInterestAmt = repayTotalAmt.subtract(reqVo.getLoanAmount());

        LoanResVo resVo = new LoanResVo();
        resVo.setRepayTotalAmount(repayTotalAmt);
        resVo.setRepayInterestAmount(repayInterestAmt);
        resVo.setPerMonthRepayVoList(perMonthRepayVoList);
        return resVo;
    }

    /**
     * 一次性提前还款计算（等额本息）
     *
     * @param billDomain
     * @return
     */
    public BigDecimal advanceCalculateEqualPrincipalAndInterest(BillDomain billDomain) {

        //年利率
        BigDecimal yearRate = (dictionaryService.findValueByCode(LoanConstants.INTEREST_RATE))
                .divide(LoanConstants.bc, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //月利率
        BigDecimal monthRate = yearRate.divide(LoanConstants.MONTHS, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //每月还款总额
        BigDecimal perRepayTotAmt = billDomain.getBillPrincipalAmount().multiply(monthRate)
                .multiply((BigDecimal.ONE.add(monthRate).pow(billDomain.getStagesNumber())))
                .divide(((BigDecimal.ONE.add(monthRate)).pow(billDomain.getStagesNumber())).subtract(BigDecimal.ONE),
                        LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //N个月后的欠款
        BigDecimal leftTotAmt = (billDomain.getBillPrincipalAmount()
                .multiply(((BigDecimal.ONE.add(monthRate)).pow(billDomain.getRepayStagesNumber())))).subtract(
                perRepayTotAmt.multiply(((BigDecimal.ONE.add(monthRate)).pow(billDomain.getRepayStagesNumber()))
                        .subtract(BigDecimal.ONE)).divide(monthRate, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP));
        //提前一次还清总额
        BigDecimal advanceTotAmt = leftTotAmt.multiply(BigDecimal.ONE.add(monthRate));

        return advanceTotAmt;
    }

    /**
     * 等额本金计算
     *
     * @param reqVo
     * @return
     */
    public LoanResVo calculateEqualPrincipal(LoanReqVo reqVo) {

        List<PerMonthRepayVo> perMonthRepayVoList = new ArrayList<>();
        BigDecimal months = BigDecimal.valueOf(reqVo.getLoanPeriod());
        //年利率
        BigDecimal yearRate = (dictionaryService.findValueByCode(LoanConstants.INTEREST_RATE))
                .divide(LoanConstants.bc, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //月利率
        BigDecimal monthRate = yearRate.divide(LoanConstants.MONTHS, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //每月还款本金金额
        BigDecimal perPrincipal = reqVo.getLoanAmount()
                .divide(months, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //第一个月还款总额
        BigDecimal firstMonthAmt = perPrincipal.add(reqVo.getLoanAmount().multiply(monthRate));
        //每月利息递减额
        BigDecimal perDecreaseAmt = perPrincipal.multiply(monthRate);
        for (Integer i = 1; i <= reqVo.getLoanPeriod(); i++) {
            PerMonthRepayVo perMonthRepayVo = new PerMonthRepayVo();
            if (i == 1) {
                perMonthRepayVo.setPerRepayTotalAmt(firstMonthAmt);
                perMonthRepayVo.setPerRepayCapitalAmt(perPrincipal);
                perMonthRepayVo.setPerRepayInterestAmt(firstMonthAmt.subtract(perPrincipal));
                perMonthRepayVo.setRepayStage(i);
                perMonthRepayVoList.add(perMonthRepayVo);
                continue;
            }
            BigDecimal perRepayTotAmt = firstMonthAmt
                    .subtract(perDecreaseAmt.multiply(new BigDecimal(i).subtract(BigDecimal.ONE)));
            BigDecimal perRepayInterestAmt = perRepayTotAmt.subtract(perPrincipal);
            perMonthRepayVo.setPerRepayTotalAmt(perRepayTotAmt);
            perMonthRepayVo.setPerRepayCapitalAmt(perPrincipal);
            perMonthRepayVo.setPerRepayInterestAmt(perRepayInterestAmt);
            perMonthRepayVo.setRepayStage(i - 1);
            perMonthRepayVoList.add(perMonthRepayVo);
        }
        //还款利息总额
        BigDecimal repayInterestAmt = (months.add(BigDecimal.ONE)).multiply(reqVo.getLoanAmount()).multiply(monthRate)
                .divide(new BigDecimal(2), LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //还款总额
        BigDecimal repayTotalAmt = reqVo.getLoanAmount().add(repayInterestAmt);

        LoanResVo resVo = new LoanResVo();
        resVo.setRepayTotalAmount(repayTotalAmt);
        resVo.setRepayInterestAmount(repayInterestAmt);
        resVo.setPerMonthRepayVoList(perMonthRepayVoList);
        return resVo;
    }

    /**
     * 一次性提前还款计算(等额本金)
     *
     * @param billDomain
     * @return
     */
    public BigDecimal advanceCalculateEqualPrincipal(BillDomain billDomain) {

        BigDecimal months = BigDecimal.valueOf(billDomain.getStagesNumber());
        //年利率
        BigDecimal yearRate = (dictionaryService.findValueByCode(LoanConstants.INTEREST_RATE))
                .divide(LoanConstants.bc, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //月利率
        BigDecimal monthRate = yearRate.divide(LoanConstants.MONTHS, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //每月还款本金金额
        BigDecimal perPrincipal = billDomain.getBillPrincipalAmount()
                .divide(months, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //已还本金总金额
        BigDecimal repayPrincipalAmt = perPrincipal.multiply(BigDecimal.valueOf(billDomain.getRepayStagesNumber()));
        //提前一次还清总额
        BigDecimal advanceTotAmt = (billDomain.getBillPrincipalAmount().subtract(repayPrincipalAmt))
                .multiply(BigDecimal.ONE.add(monthRate));

        return advanceTotAmt;
    }

    /**
     * 罚金计算
     *
     * @return
     */
    public BigDecimal calculateFine(BigDecimal moneyAmt) {

        //罚金率
        BigDecimal fineRate = (dictionaryService.findValueByCode(LoanConstants.FINE_RATE))
                .divide(LoanConstants.bc, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //罚金
        BigDecimal fineAmt = moneyAmt.multiply(fineRate);

        //罚金最低金额
        BigDecimal minFineAmt = new BigDecimal(50);

        //如果计算出的罚金金额小于罚金最低金额，则罚金设置默认最低金额
        if (minFineAmt.compareTo(fineAmt) == 1) {
            fineAmt = minFineAmt;
        }
        return fineAmt;
    }

    /**
     * 滞纳金计算
     *
     * @return
     */
    public BigDecimal calculateFee(BigDecimal moneyAmt, Long overdueDay) {

        BigDecimal daysOneYear = new BigDecimal(360);
        //滞纳金率
        BigDecimal feeRate = (dictionaryService.findValueByCode(LoanConstants.FEE_RATE))
                .divide(LoanConstants.bc, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //日逾期利率
        BigDecimal dayFeeRate = (feeRate.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(0.5))))
                .divide(daysOneYear, LoanConstants.PLAN_TEN, BigDecimal.ROUND_HALF_UP);
        //滞纳金
        BigDecimal feeAmt = moneyAmt.multiply(BigDecimal.valueOf(overdueDay)).multiply(dayFeeRate);

        return feeAmt;
    }
}
