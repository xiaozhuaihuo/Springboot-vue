package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.exception.BusinessException;
import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.UserQuotaMapper;
import com.fengyaodong.bloan.model.condition.UserMsgCondition;
import com.fengyaodong.bloan.model.domain.UserMsgDomain;
import com.fengyaodong.bloan.model.domain.UserQuotaDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户额度Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/5 15:18
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/5 15:18
 */
@Service
@Slf4j
public class UserQuotaService implements AbstractService<UserQuotaDomain, String> {

    @Autowired
    private UserQuotaMapper userQuotaMapper;

    @Autowired
    private UserMsgService userMsgService;

    @Override
    public UserQuotaMapper getMapper() {
        return userQuotaMapper;
    }

    /**
     * 添加用户额度信息（申请额度）
     *
     * @param userId
     * @param quotaAmt
     */
    public void addQuotaInfo(String userId, BigDecimal quotaAmt) {

        UserQuotaDomain userQuotaDomain = new UserQuotaDomain();
        userQuotaDomain.setUserId(userId);
        userQuotaDomain.setLoanTotalQuota(quotaAmt);
        userQuotaDomain.setLoanUsedQuota(BigDecimal.ZERO);
        userQuotaDomain.setLoanSurplusQuota(quotaAmt);
        userQuotaDomain.setApplyQuotaDate(new Date());

        userQuotaMapper.add(userQuotaDomain);
    }

    /**
     * 更新用户额度信息（提升额度）
     *
     * @param userId
     * @param quotaAmt
     */
    public void updateQuotaInfo(String userId, BigDecimal quotaAmt) {

        UserQuotaDomain userQuotaDomain = userQuotaMapper.findOneById(userId);
        userQuotaDomain.setLoanTotalQuota(userQuotaDomain.getLoanTotalQuota().add(quotaAmt));
        userQuotaDomain.setLoanSurplusQuota(userQuotaDomain.getLoanSurplusQuota().add(quotaAmt));
        userQuotaDomain.setPromoteQuotaDate(new Date());

        userQuotaMapper.updateById(userQuotaDomain);
    }

    /**
     * 用户还款更新额度信息
     *
     * @param moneyAmt
     * @param userId
     */
    public void updateQuotaRepay(BigDecimal moneyAmt, String userId) {

        UserQuotaDomain userQuotaDomain = findOneById(userId);
        BigDecimal usedQuota = userQuotaDomain.getLoanUsedQuota().subtract(moneyAmt);
        BigDecimal surplusQuota = userQuotaDomain.getLoanSurplusQuota().add(moneyAmt);
        userQuotaDomain.setLoanUsedQuota(usedQuota);
        userQuotaDomain.setLoanSurplusQuota(surplusQuota);

        log.info("更新用户额度，更新信息={}", userQuotaDomain);
        userQuotaMapper.updateById(userQuotaDomain);
    }

    /**
     * 用户贷款更新额度信息
     *
     * @param userQuotaDomain
     * @param moneyAmt
     */
    public void updateQuotaLoan(UserQuotaDomain userQuotaDomain, BigDecimal moneyAmt) {

        //修改已用额度
        userQuotaDomain.setLoanUsedQuota(userQuotaDomain.getLoanUsedQuota().add(moneyAmt));
        //修改剩余额度
        userQuotaDomain
                .setLoanSurplusQuota(userQuotaDomain.getLoanTotalQuota().subtract(userQuotaDomain.getLoanUsedQuota()));
        //更新用户额度表
        userQuotaMapper.updateById(userQuotaDomain);
    }

    /**
     * 申请额度
     *
     * @param userMsgDomain
     */
    public void applyQuota(UserMsgDomain userMsgDomain) {

        //判断是否有相同类型的申请在审批中
        UserMsgCondition condition = new UserMsgCondition();
        condition.setUserId(userMsgDomain.getUserId());
        condition.setMsgType(LoanConstants.APPLY_QUOTA);
        condition.setIsRead(LoanConstants.NOT_READ);
        List<UserMsgDomain> list = userMsgService.find(condition);
        if (null != list && list.size() > 0) {
            throw new BusinessException("您已有额度申请在审批中，请不要重复申请！");
        }
        UserQuotaDomain userQuotaDomain = userQuotaMapper.findOneById(userMsgDomain.getUserId());
        if (null == userQuotaDomain) {
            userMsgService.addUserMsg(userMsgDomain);
        } else {
            throw new BusinessException("该用户已拥有额度，不能再次申请");
        }
    }

    /**
     * 提升额度
     *
     * @param userMsgDomain
     */
    public void promoteQuota(UserMsgDomain userMsgDomain) {

        //判断是否有相同类型的申请在审批中
        UserMsgCondition condition = new UserMsgCondition();
        condition.setUserId(userMsgDomain.getUserId());
        condition.setMsgType(LoanConstants.PROMOTE_QUOTA);
        condition.setIsRead(LoanConstants.NOT_READ);
        List<UserMsgDomain> list = userMsgService.find(condition);
        if (null != list && list.size() > 0) {
            throw new BusinessException("您已有额度提升在审批中，请不要重复申请！");
        }
        UserQuotaDomain userQuotaDomain = userQuotaMapper.findOneById(userMsgDomain.getUserId());
        if (null == userQuotaDomain) {
            throw new BusinessException("该用户还没有额度，请先申请额度");
        } else {
            userMsgService.addUserMsg(userMsgDomain);
        }
    }
}
