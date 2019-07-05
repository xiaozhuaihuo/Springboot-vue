package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.exception.BankCardException;
import com.fengyaodong.bloan.common.exception.BusinessException;
import com.fengyaodong.bloan.common.util.DesUtil;
import com.fengyaodong.bloan.common.util.DesensitizeUtil;
import com.fengyaodong.bloan.common.util.UUIDUtils;
import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.BankCardMapper;
import com.fengyaodong.bloan.model.condition.BankCardCondition;
import com.fengyaodong.bloan.model.domain.BankCardDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 银行卡Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 17:46
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 17:46
 */
@Service
public class BankCardService implements AbstractService<BankCardDomain, String> {

    @Autowired
    private BankCardMapper bankCardMapper;

    @Override
    public BankCardMapper getMapper() {
        return bankCardMapper;
    }

    /**
     * 查询对应的银行卡信息
     *
     * @param bankCardDomain
     * @return
     */
    public BankCardDomain findOne(BankCardDomain bankCardDomain) {
        return bankCardMapper.findOne(bankCardDomain);
    }

    /**
     * 更新所有银行卡信息
     *
     * @param bankCardDomain
     */
    public void updateAll(BankCardDomain bankCardDomain) {
        bankCardMapper.updateAll(bankCardDomain);
    }

    /**
     * 处理查询条件
     *
     * @param condition
     * @throws Exception
     */
    public void handleCondition(BankCardCondition condition) throws Exception {

        if (condition.getBankCardNo() != null && !condition.getBankCardNo().equals("")) {
            condition.setBankCardNo(DesUtil.encrypt(condition.getBankCardNo()));
        }
        if (condition.getBankMobNo() != null && !condition.getBankMobNo().equals("")) {
            condition.setBankMobNo(DesUtil.encrypt(condition.getBankMobNo()));
        }
        if (condition.getBankCardName() != null && !condition.getBankCardName().equals("")) {
            condition.setBankCardName(DesUtil.encrypt(condition.getBankCardName()));
        }
    }

    /**
     * 添加银行卡信息
     *
     * @param domain
     */
    public void addBankCard(BankCardDomain domain) throws Exception {

        domain.setCardId(UUIDUtils.getUUID());
        domain.setBankCardNoHid(DesensitizeUtil.cardNo(domain.getBankCardNo()));
        domain.setBankCardNameHid(DesensitizeUtil.right(domain.getBankCardName(), 1));
        domain.setBankMobNoHid(DesensitizeUtil.phone(domain.getBankMobNo()));
        domain.setBankCardNo(DesUtil.encrypt(domain.getBankCardNo()));
        domain.setBankCardPassword(DesUtil.encrypt(domain.getBankCardPassword()));
        domain.setBankCardName(DesUtil.encrypt(domain.getBankCardName()));
        domain.setBankMobNo(DesUtil.encrypt(domain.getBankMobNo()));
        domain.setErrorNumber(0);

        bankCardMapper.add(domain);
    }

    /**
     * 初始化银行卡密码错误次数
     */
    @Transactional(rollbackFor = Exception.class)
    public void initErrorNumber() {
        BankCardDomain bankCardDomain = new BankCardDomain();
        bankCardDomain.setErrorNumber(0);
        bankCardMapper.updateAll(bankCardDomain);
    }

    /**
     * 用户贷款成功后更新银行卡余额
     *
     * @param cardNo
     * @param moneyAmt
     */
    public void updateBalanceForLoan(String cardNo, BigDecimal moneyAmt) {

        BankCardDomain bankCardDomain = findOneById(cardNo);
        BigDecimal bankSurplusAmt = bankCardDomain.getBankCardBalance().add(moneyAmt);
        bankCardDomain.setBankCardBalance(bankSurplusAmt);
        bankCardMapper.updateById(bankCardDomain);
    }

    /**
     * 用户还款成功后更新银行卡余额
     *
     * @param bankCardDomain
     * @param moneyAmt
     */
    public void updateBalanceForRepay(BankCardDomain bankCardDomain, BigDecimal moneyAmt) {

        BankCardDomain domain = findOne(bankCardDomain);
        if (null == domain) {
            BankCardDomain domainNow = findOneById(bankCardDomain.getBankCardNo());
            if (domainNow.getErrorNumber() >= LoanConstants.ERROR_NUMBER) {
                throw new BusinessException("该银行卡密码今日错误次数已达3次，卡已被锁，请明日再试或更换银行卡支付！");
            } else {
                domainNow.setErrorNumber(1 + domainNow.getErrorNumber());
                bankCardMapper.updateById(domainNow);
            }
            throw new BankCardException("支付密码不正确");
        }
        if (moneyAmt.compareTo(domain.getBankCardBalance()) == 1) {
            throw new BusinessException("余额不足，支付失败");
        }
        BigDecimal bankSurplusAmt = domain.getBankCardBalance().subtract(moneyAmt);
        bankCardDomain.setBankCardBalance(bankSurplusAmt);
        bankCardMapper.updateById(bankCardDomain);
    }
}
