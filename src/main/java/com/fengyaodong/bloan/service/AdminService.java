package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.exception.BusinessException;
import com.fengyaodong.bloan.common.util.DesUtil;
import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.AdminMapper;
import com.fengyaodong.bloan.model.domain.AdminDomain;
import com.fengyaodong.bloan.model.domain.UserMsgDomain;
import com.fengyaodong.bloan.model.vo.ApprovalVo;
import com.fengyaodong.bloan.model.vo.ForbidVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 管理员Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/12 20:39
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/12 20:39
 */
@Service
public class AdminService implements AbstractService<AdminDomain, String> {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private UserQuotaService userQuotaService;

    @Override
    public AdminMapper getMapper() {
        return adminMapper;
    }

    /**
     * 根据登录账号查询余额
     *
     * @param loginNo
     * @return
     */
    public AdminDomain findOneByLoginNo(String loginNo) {
        return adminMapper.findOneByLoginNo(loginNo);
    }

    /**
     * 管理员登录
     *
     * @param adminDomain
     * @return
     * @throws Exception
     */
    public AdminDomain adminLogin(AdminDomain adminDomain) throws Exception {

        //加密数据
        adminDomain.setAdminPassword(DesUtil.encrypt(adminDomain.getAdminPassword()));
        List<AdminDomain> list = adminMapper.find(adminDomain);

        if (list == null || list.size() != 1) {

            throw new BusinessException("管理员用户名密码不匹配");
        }
        return list.get(0);
    }

    /**
     * 用户贷款成功后更新管理员余额
     *
     * @param moneyAmt
     */
    public void updateAdminForLoan(BigDecimal moneyAmt) {

        AdminDomain adminDomain = findOneByLoginNo(LoanConstants.ADMIN);
        if (moneyAmt.compareTo(adminDomain.getAdminSurplusAmt()) == 1) {
            throw new BusinessException("放款失败，请稍后贷款");
        }
        BigDecimal adminAmt = adminDomain.getAdminSurplusAmt().subtract(moneyAmt);
        adminDomain.setAdminSurplusAmt(adminAmt);
        adminMapper.updateById(adminDomain);
    }

    /**
     * 用户还款成功后更新管理员余额
     *
     * @param moneyAmt
     */
    public void updateAdminForRepay(BigDecimal moneyAmt) {

        AdminDomain adminDomain = findOneByLoginNo(LoanConstants.ADMIN);
        BigDecimal adminAmt = adminDomain.getAdminSurplusAmt().add(moneyAmt);
        adminDomain.setAdminSurplusAmt(adminAmt);
        adminMapper.updateById(adminDomain);
    }

    /**
     * 驳回申请
     *
     * @param vo
     */
    public void rejectApply(ForbidVo vo) {

        UserMsgDomain userMsgDomain = userMsgService.findOneById(vo.getMsgId());
        //更新待批准信息
        userMsgService.updateReadStatus(userMsgDomain);

        UserMsgDomain msgDomain = new UserMsgDomain();
        msgDomain.setUserId(userMsgDomain.getUserId());
        String msgContent = "您的申请被驳回，驳回原因：" + vo.getReason() + "!";
        msgDomain.setMsgContent(msgContent);
        msgDomain.setMsgType(LoanConstants.REJECT);
        //添加批准信息
        userMsgService.addUserMsg(msgDomain);
    }

    /**
     * 批准申请额度
     *
     * @param vo
     */
    @Transactional(rollbackFor = Exception.class)
    public void approvalApply(ApprovalVo vo) {

        UserMsgDomain userMsgDomain = userMsgService.findOneById(vo.getMsgId());
        //更新待批准信息
        userMsgService.updateReadStatus(userMsgDomain);
        //添加用户额度信息
        userQuotaService.addQuotaInfo(userMsgDomain.getUserId(), vo.getQuotaAmt());
        UserMsgDomain msgDomain = new UserMsgDomain();
        msgDomain.setUserId(userMsgDomain.getUserId());
        String msgContent = "恭喜您申请额度成功，您获得" + vo.getQuotaAmt() + "元贷款额度！";
        msgDomain.setMsgContent(msgContent);
        msgDomain.setMsgType(LoanConstants.APPROVAL);
        //添加批准信息
        userMsgService.addUserMsg(msgDomain);
    }

    /**
     * 批准提升额度
     *
     * @param vo
     */
    public void approvalPromote(ApprovalVo vo) {

        UserMsgDomain userMsgDomain = userMsgService.findOneById(vo.getMsgId());
        //更新待批准信息
        userMsgService.updateReadStatus(userMsgDomain);
        //更新用户额度信息
        userQuotaService.updateQuotaInfo(userMsgDomain.getUserId(), vo.getQuotaAmt());
        UserMsgDomain msgDomain = new UserMsgDomain();
        msgDomain.setUserId(userMsgDomain.getUserId());
        String msgContent = "恭喜您提升额度成功，您提升" + vo.getQuotaAmt() + "元贷款额度！";
        msgDomain.setMsgContent(msgContent);
        msgDomain.setMsgType(LoanConstants.APPROVAL);
        //添加批准信息
        userMsgService.addUserMsg(msgDomain);
    }
}
