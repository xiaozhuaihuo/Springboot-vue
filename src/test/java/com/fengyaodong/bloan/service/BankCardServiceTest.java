package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.BaseTest;
import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.util.DesUtil;
import com.fengyaodong.bloan.common.util.DesensitizeUtil;
import com.fengyaodong.bloan.common.util.UUIDUtils;
import com.fengyaodong.bloan.model.domain.BankCardDomain;
import com.fengyaodong.bloan.model.domain.BindCardDomain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 银行卡测试类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 11:23
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 11:23
 */
@Slf4j
public class BankCardServiceTest extends BaseTest {

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private BindCardService bindCardService;

    @Test
    public void add() throws Exception{

        BankCardDomain bankCardDomain = new BankCardDomain();

        bankCardDomain.setCardId(UUIDUtils.getUUID());
        bankCardDomain.setCardType(LoanConstants.DEBIT);
        bankCardDomain.setBankCardNo("6217000260011234568");
        bankCardDomain.setBankCardPassword("740195");
        bankCardDomain.setBankCardName("冯垚栋");
        bankCardDomain.setBankMobNo("15735170275");
        String bankCardNo = DesUtil.encrypt(bankCardDomain.getBankCardNo());
        String bankCardName = DesUtil.encrypt(bankCardDomain.getBankCardName());
        String bankCardPassword = DesUtil.encrypt(bankCardDomain.getBankCardPassword());
        String bankCardMobNo = DesUtil.encrypt(bankCardDomain.getBankMobNo());
        bankCardDomain.setBankCardNoHid(DesensitizeUtil.cardNo(bankCardDomain.getBankCardNo()));
        bankCardDomain.setBankCardNameHid(DesensitizeUtil.right(bankCardDomain.getBankCardName(), 1));
        bankCardDomain.setBankMobNoHid(DesensitizeUtil.phone(bankCardDomain.getBankMobNo()));
        bankCardDomain.setBankCardNo(bankCardNo);
        bankCardDomain.setBankCardPassword(bankCardPassword);
        bankCardDomain.setBankCardName(bankCardName);
        bankCardDomain.setBankMobNo(bankCardMobNo);
        bankCardDomain.setBankCardBalance(new BigDecimal(200000));
        bankCardDomain.setErrorNumber(0);

        log.info("银行卡信息={}", bankCardDomain);
        bankCardService.add(bankCardDomain);

    }

    @Test
    public void bind() throws Exception{

        String userId = "7b6eb3a2974a4b0391c0218a0028de97";
        BindCardDomain bindCardDomain = new BindCardDomain();

        bindCardDomain.setBankCardNo("6217000260001357123");
        bindCardDomain.setBankCardName("李鑫");
        bindCardDomain.setBankMobNo("15735170721");
        bindCardDomain.setCardType(LoanConstants.DEBIT);
        bindCardService.bindCard(bindCardDomain, userId);
    }
}
