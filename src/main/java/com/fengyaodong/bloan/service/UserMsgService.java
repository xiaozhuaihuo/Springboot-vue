package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.util.UUIDUtils;
import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.UserMsgMapper;
import com.fengyaodong.bloan.dao.page.IPagination;
import com.fengyaodong.bloan.dao.page.PageImpl;
import com.fengyaodong.bloan.model.condition.UserMsgCondition;
import com.fengyaodong.bloan.model.domain.UserMsgDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户消息Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 17:09
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 17:09
 */
@Service
@Slf4j
public class UserMsgService implements AbstractService<UserMsgDomain, String> {

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Override
    public UserMsgMapper getMapper() {
        return userMsgMapper;
    }

    /**
     * 根据用户ID统计未读消息条数
     *
     * @param userId
     */
    public Integer countByUserId(String userId) {
        return userMsgMapper.countByUserId(userId);
    }

    /**
     * 根据用户ID查询用户未读消息
     *
     * @param userId
     * @return
     */
    public List<UserMsgDomain> findAllByUserId(String userId) {
        return userMsgMapper.findAllByUserId(userId);
    }

    /**
     * 更新用户所有消息
     *
     * @param userId
     */
    public void updateStatusByUserId(String userId) {
        userMsgMapper.updateStatusByUserId(userId);
    }

    /**
     * 查询所有待审批信息
     *
     * @return
     */
    public List<UserMsgDomain> findAllByMsgType(UserMsgCondition condition) {
        return userMsgMapper.findAllByMsgType(condition);
    }

    /**
     * 更新消息阅读状态
     *
     * @param userMsgDomain
     */
    public void updateReadStatus(UserMsgDomain userMsgDomain) {

        userMsgDomain.setIsRead(LoanConstants.IS_READ);
        userMsgMapper.updateById(userMsgDomain);
    }

    /**
     * 分页查询 <br/>
     * 先查询总记录数据，然后再获取当前页数据<br/>
     *
     * @param condition 查询条件
     * @param page      从1开始的页码
     * @param page
     * @return 分页结果
     */
    public  Page<UserMsgDomain> findByPageAll(UserMsgCondition condition, int page, int size) {

        Long totalCnt = countByMsgType(condition);
        if (null == totalCnt || totalCnt.intValue() < 0) {
            return null;
        }
        List<UserMsgDomain> list = null;
        int offset = (page - 1) * size;
        IPagination pagination = buildPagination(offset, size);
        if (null != totalCnt && totalCnt.intValue() > 0 && offset < totalCnt) {
            list = findAllByMsgType(condition, pagination);
        } else {
            list = new ArrayList<>();
        }
        return new PageImpl<>(list, pagination, totalCnt);

    }

    /**
     * 添加用户消息
     *
     * @param userMsgDomain
     */
    public void addUserMsg(UserMsgDomain userMsgDomain) {

        userMsgDomain.setMsgId(UUIDUtils.getUUID());
        userMsgDomain.setIsRead(LoanConstants.NOT_READ);
        userMsgDomain.setCreateDate(new Date());
        if (LoanConstants.REPAY_REMIND.equals(userMsgDomain.getMsgType())) {
            String msgContent = "账单号为" + userMsgDomain.getBillNo() + "的账单即将逾期，请尽快还款，避免对您造成不良影响！";
            userMsgDomain.setMsgContent(msgContent);
        }
        if (LoanConstants.DEEUCT_REMIND.equals(userMsgDomain.getMsgType())) {
            String msgContent = "账单号为" + userMsgDomain.getBillNo() + "的账单因银行卡余额不足，扣款失败，请尽快手动还款或保证银行卡余额充足，避免对您造成不良影响！";
            userMsgDomain.setMsgContent(msgContent);
        }
        if (LoanConstants.OVERDUE_REMIND.equals(userMsgDomain.getMsgType())) {
            String msgContent = "账单号为" + userMsgDomain.getBillNo() + "的账单已经逾期，为避免产生高额的逾期费用，请尽快还款，避免对您造成不良影响！";
            userMsgDomain.setMsgContent(msgContent);
        }
        if (LoanConstants.APPLY_QUOTA.equals(userMsgDomain.getMsgType())) {
            String msgContent = "申请贷款额度";
            userMsgDomain.setMsgContent(msgContent);
        }
        if (LoanConstants.PROMOTE_QUOTA.equals(userMsgDomain.getMsgType())) {
            String msgContent = "提升贷款额度";
            userMsgDomain.setMsgContent(msgContent);
        }

        log.info("添加用户消息，消息={}", userMsgDomain);
        userMsgMapper.add(userMsgDomain);
    }

}
