package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.exception.BusinessException;
import com.fengyaodong.bloan.common.util.DesUtil;
import com.fengyaodong.bloan.common.util.DesensitizeUtil;
import com.fengyaodong.bloan.common.util.UUIDUtils;
import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.BindCardMapper;
import com.fengyaodong.bloan.model.condition.BindCardCondition;
import com.fengyaodong.bloan.model.domain.BankCardDomain;
import com.fengyaodong.bloan.model.domain.BindCardDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 绑卡Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 17:38
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 17:38
 */
@Service
@Slf4j
public class BindCardService implements AbstractService<BindCardDomain, String> {

    @Autowired
    private BindCardMapper bindCardMapper;

    @Autowired
    private BankCardService bankCardService;

    @Override
    public BindCardMapper getMapper() {
        return bindCardMapper;
    }

    /**
     * 根据用户ID查询绑卡信息
     *
     * @param userId
     * @return
     */
    public List<BindCardDomain> findAllByUserId(String userId) {
        return bindCardMapper.findAllByUserId(userId);
    }

    /**
     * 根据用户ID和默认卡查询绑卡信息
     *
     * @param userId
     * @return
     */
    public BindCardDomain findAllByUserIdAndDefault(String userId) {
        return bindCardMapper.findAllByUserIdAndDefault(userId);
    }

    /**
     * 根据用户ID更新该用户所有卡信息
     *
     * @param userId
     */
    public void updateByUserId(String userId) {
        bindCardMapper.updateByUserId(userId);
    }

    /**
     * 根据卡号更新该卡信息
     *
     * @param cardId
     */
    public void updateByCardId(String cardId) {
        bindCardMapper.updateByCardId(cardId);
    }

    /**
     * 处理查询条件
     *
     * @param condition
     * @throws Exception
     */
    public void handleCondition(BindCardCondition condition) throws Exception {

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
     * 绑定银行卡
     *
     * @param bindCardDomain
     * @param userId
     */
    public void bindCard(BindCardDomain bindCardDomain, String userId) throws Exception {

        //加密数据
        encryptInfo(bindCardDomain);
        BankCardDomain bankCardDomain = new BankCardDomain();
        BindCardDomain domain = findOneById(bindCardDomain.getBankCardNo());
        if (domain == null) {
            bankCardDomain.setBankCardNo(bindCardDomain.getBankCardNo());
            bankCardDomain.setBankCardName(bindCardDomain.getBankCardName());
            bankCardDomain.setBankMobNo(bindCardDomain.getBankMobNo());
            bankCardDomain.setCardType(bindCardDomain.getCardType());
            //核对绑卡信息
            List<BankCardDomain> list = bankCardService.find(bankCardDomain);
            if (list.size() == 0) {
                throw new BusinessException("银行卡信息不正确，请重新填写");
            }
            //初始化绑卡信息
            bindCardDomain.setBindId(UUIDUtils.getUUID());
            bindCardDomain.setIsDefault(LoanConstants.NOT_DEFAULT);
            bindCardDomain.setUserId(userId);
            bindCardDomain.setSignDate(new Date());
            bindCardMapper.add(bindCardDomain);
        } else {
            throw new BusinessException("该卡已被绑定，请重新选择银行卡");
        }
    }

    /**
     * 银行卡解除绑定
     *
     * @param cardNo
     */
    public void unBindCard(String cardNo) {

        BindCardDomain bindCardDomain = bindCardMapper.findOneById(cardNo);
        if (LoanConstants.IS_DEFAULT.equals(bindCardDomain.getIsDefault())) {
            throw new BusinessException("该卡为默认卡，无法解绑，请更换默认卡再进行解绑");
        }
        bindCardMapper.deleteById(cardNo);
    }

    /**
     * 设置默认卡
     *
     * @param cardNo
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void setDefaultCard(String cardNo, String userId) {


        //把其余卡都设置为非默认卡
        updateByUserId(userId);
        //设置该卡为默认卡
        updateByCardId(cardNo);
    }

    /**
     * 加密数据
     *
     * @param bindCardDomain
     * @throws Exception
     */
    private void encryptInfo(BindCardDomain bindCardDomain) throws Exception {

        bindCardDomain.setBankCardNoHid(DesensitizeUtil.cardNo(bindCardDomain.getBankCardNo()));
        bindCardDomain.setBankCardNameHid(DesensitizeUtil.right(bindCardDomain.getBankCardName(), 1));
        bindCardDomain.setBankMobNoHid(DesensitizeUtil.phone(bindCardDomain.getBankMobNo()));
        bindCardDomain.setBankCardNo(DesUtil.encrypt(bindCardDomain.getBankCardNo()));
        bindCardDomain.setBankCardName(DesUtil.encrypt(bindCardDomain.getBankCardName()));
        bindCardDomain.setBankMobNo(DesUtil.encrypt(bindCardDomain.getBankMobNo()));
    }
}
