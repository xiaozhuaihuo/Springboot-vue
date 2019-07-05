package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.exception.BusinessException;
import com.fengyaodong.bloan.common.util.DesUtil;
import com.fengyaodong.bloan.common.util.DesensitizeUtil;
import com.fengyaodong.bloan.model.condition.PerBillCondition;
import com.fengyaodong.bloan.model.domain.*;
import com.fengyaodong.bloan.model.vo.LoanReqVo;
import com.fengyaodong.bloan.model.vo.LoanResVo;
import com.fengyaodong.bloan.model.vo.RepayVo;
import com.fengyaodong.bloan.model.vo.SumAmtVo;
import com.fengyaodong.bloan.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/12 21:10
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/12 21:10
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BillService billService;

    @Autowired
    private UserQuotaService userQuotaService;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private BindCardService bindCardService;

    @Autowired
    private PerBillService perBillService;

    @Autowired
    private CalculateAmountService calculateAmountService;

    /**
     * 用户注册
     *
     * @param domain
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/register")
    public BaseResult register(UserInfoDomain domain) throws Exception {

        log.info("用户注册，注册信息={}", domain);
        userInfoService.addUserInfo(domain);
        log.info("注册成功");

        return new BaseResult(LoanConstants.SUCCESS, "注册成功");
    }

    /**
     * 测试用户名
     *
     * @param loginNo
     * @return
     */
    @PostMapping(value = "/testLoginNo")
    public BaseResult testLoginNo(String loginNo) {

        log.info("用户名检测，用户名={}", loginNo);
        userInfoService.testUserLoginNo(loginNo);
        log.info("检测成功");

        return new BaseResult(LoanConstants.SUCCESS, "检测成功");
    }

    /**
     * 用户登录
     *
     * @param userInfoDomain
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/login")
    public BaseResult login(UserInfoDomain userInfoDomain) throws Exception {

        log.info("用户登录，登录信息={}", userInfoDomain);
        UserInfoDomain domain = userInfoService.userLogin(userInfoDomain);
        log.info("登录成功,返回信息={}", domain);

        return new BaseResult(domain.getUserId());
    }

    /**
     * 用户信息
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/findUser")
    public BaseResult findUser(String userId) {

        log.info("查询用户信息，用户ID={}", userId);
        UserInfoDomain userInfoDomain = userInfoService.findOneById(userId);

        return new BaseResult(userInfoDomain);
    }

    /**
     * 更新用户信息
     *
     * @param domain
     * @return
     */
    @PostMapping(value = "/update")
    public BaseResult update(UserInfoDomain domain) throws Exception {

        log.info("更新用户信息，用户信息={}", domain);
        domain.setUpdateDate(new Date());
        encryptInfo(domain);
        userInfoService.updateById(domain);
        log.info("更新成功");

        return new BaseResult(LoanConstants.SUCCESS, "更新成功");
    }

    /**
     * 根据当前日期查询用户所有逾期与未结清账单
     *
     * @param condition
     * @return
     */
    @PostMapping(value = "/findAllBill")
    public BaseResult findAllBillByDate(PerBillCondition condition) {

        Date date = new Date();
        log.info("查询账单信息，查询日期={}，用户ID={}", date, condition.getUserId());
        List<PerBillDomain> list = perBillService.findAllByDate(condition);
        BigDecimal repayTotAmt = perBillService.countTotAmtByDate(condition);
        if (null == repayTotAmt) {
            repayTotAmt = BigDecimal.ZERO;
        }
        Integer msgNumber = userMsgService.countByUserId(condition.getUserId());
        UserQuotaDomain userQuotaDomain = userQuotaService.findOneById(condition.getUserId());
        SumAmtVo sumAmt = billService.countAmtByUserId(condition.getUserId());
        if (null == sumAmt) {
            sumAmt = new SumAmtVo();
            sumAmt.setRepayTotalAmount(BigDecimal.ZERO);
            sumAmt.setTotalAmount(BigDecimal.ZERO);
        }
        BigDecimal waitRepayAmount = sumAmt.getTotalAmount().subtract(sumAmt.getRepayTotalAmount());
        List<PerBillDomain> clearBillList = perBillService.findClearBill(condition.getUserId());

        log.info("账单信息={}", list);

        return new BaseResult(list).putExtra("repayTotAmt", repayTotAmt).putExtra("msgNumber", msgNumber)
                .putExtra("userQuota", userQuotaDomain).putExtra("totalAmount", waitRepayAmount)
                .putExtra("repayTotalAmount", sumAmt.getRepayTotalAmount()).putExtra("clearBillList", clearBillList);
    }

    /**
     * 请求贷款
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/requestLoan")
    public BaseResult requestLoan(String userId) {

        log.info("请求贷款开始");
        if (userId == null) {
            throw new BusinessException("您没有登录");
        }
        Map<String, Object> map = billService.userRequestLoan(userId);
        log.info("返回贷款所需参数，参数={}", map);

        return new BaseResult(map);
    }

    /**
     * 贷款
     *
     * @param loanReqVo
     * @param userId
     * @return
     */
    @PostMapping(value = "/loan")
    public BaseResult loan(LoanReqVo loanReqVo, String userId) {

        log.info("用户贷款，贷款信息={}，用户ID={}", loanReqVo, userId);
        if (userId == null) {
            throw new BusinessException("您没有登录");
        }
        billService.userLoan(loanReqVo, userId);
        log.info("贷款成功");

        return new BaseResult(LoanConstants.SUCCESS, "贷款成功");
    }

    /**
     * 贷款金额试算
     *
     * @param loanReqVo
     * @return
     */
    @PostMapping("/trial")
    public BaseResult trial(LoanReqVo loanReqVo) {

        log.info("贷款金额试算，试算信息={}", loanReqVo);
        List<LoanResVo> list = new ArrayList<>();
        //等额本金计算
        LoanResVo resVo = calculateAmountService.calculateEqualPrincipal(loanReqVo);
        //等额本息计算
        LoanResVo resVo1 = calculateAmountService.calculateEqualPrincipalAndInterest(loanReqVo);
        list.add(resVo);
        list.add(resVo1);

        return new BaseResult(list);
    }

    /**
     * 还款 1.立即还款 2.一键还款 3.提前结清
     *
     * @param repayVo
     * @param userId
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/repay")
    public BaseResult repay(RepayVo repayVo, String userId) throws Exception {

        log.info("用户还款，还款信息={},用户ID={}", repayVo, userId);
        if (userId == null) {
            throw new BusinessException("您没有登录");
        }
        billService.userRepay(repayVo, userId);
        log.info("还款成功");
        billService.updateBySubBill();
        log.info("更新总账单成功");

        return new BaseResult(LoanConstants.SUCCESS, "还款成功");
    }

    /**
     * 开启自动还款
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/openAutoRepay")
    public BaseResult openAutoRepay(String userId) {

        log.info("开启自动还款，用户ID={}", userId);
        if (userId == null) {
            throw new BusinessException("您没有登录");
        }
        billService.setOpenAutoRepay(userId);
        log.info("开启自动还款成功");

        return new BaseResult(LoanConstants.SUCCESS, "开启自动还款成功");
    }

    /**
     * 关闭自动还款
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/stopAutoRepay")
    public BaseResult stopAutoRepay(String userId) {

        log.info("关闭自动还款，用户ID={}", userId);
        if (userId == null) {
            throw new BusinessException("您没有登录");
        }
        billService.setStopAutoRepay(userId);
        log.info("关闭自动还款成功");

        return new BaseResult(LoanConstants.SUCCESS, "关闭自动还款成功");
    }

    /**
     * 用户额度申请
     *
     * @param userMsgDomain
     * @return
     */
    @PostMapping(value = "/apply")
    public BaseResult apply(UserMsgDomain userMsgDomain) {

        log.info("申请贷款额度，申请信息={}", userMsgDomain);
        if (userMsgDomain.getUserId() == null) {
            throw new BusinessException("您没有登录");
        }
        userQuotaService.applyQuota(userMsgDomain);
        log.info("申请额度信息已提交，等待审核");

        return new BaseResult(LoanConstants.SUCCESS, "申请额度信息已提交，等待审核");
    }

    /**
     * 用户额度提升申请
     *
     * @param userMsgDomain
     * @return
     */
    @PostMapping(value = "/promote")
    public BaseResult promote(UserMsgDomain userMsgDomain) {

        log.info("申请提升贷款额度，申请信息={}", userMsgDomain);
        if (userMsgDomain.getUserId() == null) {
            throw new BusinessException("您没有登录");
        }
        userQuotaService.promoteQuota(userMsgDomain);
        log.info("申请提升额度信息已提交，等待审核");

        return new BaseResult(LoanConstants.SUCCESS, "申请提升额度信息已提交，等待审核");
    }

    /**
     * 用户绑卡
     *
     * @param bindCardDomain
     * @param userId
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/bind")
    public BaseResult bind(BindCardDomain bindCardDomain, String userId) throws Exception {

        log.info("绑定银行卡，绑卡信息={}，用户ID={}", bindCardDomain, userId);
        if (userId == null) {
            throw new BusinessException("您没有登录");
        }
        bindCardService.bindCard(bindCardDomain, userId);
        log.info("绑卡成功");

        return new BaseResult(LoanConstants.SUCCESS, "绑卡成功");
    }

    /**
     * 设置默认卡
     *
     * @param bankCardNo
     * @param userId
     * @return
     */
    @PostMapping(value = "/default")
    public BaseResult setDefault(String bankCardNo, String userId) {

        log.info("设置默认银行卡，银行卡号={}，用户ID={}", bankCardNo, userId);
        if (userId == null) {
            throw new BusinessException("您没有登录");
        }
        bindCardService.setDefaultCard(bankCardNo, userId);
        log.info("设置成功");

        return new BaseResult(LoanConstants.SUCCESS, "设置成功");
    }

    /**
     * 解绑银行卡
     *
     * @param bankCardNo
     * @return
     */
    @PostMapping(value = "/unBind")
    public BaseResult unBind(String bankCardNo) {

        log.info("银行卡解除绑定，银行卡号={}", bankCardNo);
        bindCardService.unBindCard(bankCardNo);
        log.info("解除绑定成功");

        return new BaseResult(LoanConstants.SUCCESS, "解除绑定成功");
    }

    /**
     * 提前还清金额计算 ！！！
     *
     * @param billNo
     * @return
     */
    @PostMapping(value = "/advanceRepay")
    public BigDecimal advanceRepay(String billNo) {

        log.info("提前还清计算，账单号={}", billNo);
        Integer overdueNumber = perBillService.countAllByOverdue(billNo);
        if (0 < overdueNumber) {
            throw new BusinessException(overdueNumber, "该账单中存在逾期子账单，请先还清逾期账单");
        }
        BigDecimal advanceRepayAmt = billService.calculateAdvanceRepayAmt(billNo);
        log.info("计算结果={}", advanceRepayAmt);

        return advanceRepayAmt;
    }

    /**
     * 查询用户未读消息
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/findMsg")
    public BaseResult findAllMsg(String userId) {

        log.info("查询用户消息，用户ID={}", userId);
        List<UserMsgDomain> list = userMsgService.findAllByUserId(userId);
        log.info("查询结果={}", list);

        return new BaseResult(list);
    }

    /**
     * 查询用户绑卡信息
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/findAllCard")
    public BaseResult findAllCard(String userId) {

        log.info("查询用户绑卡信息，用户ID={}", userId);
        List<BindCardDomain> list = bindCardService.findAllByUserId(userId);
        log.info("查询结果={}", list);

        return new BaseResult(list);
    }

    /**
     * 阅读单条消息
     *
     * @param msgId
     * @return
     */
    @PostMapping(value = "/readOne")
    public BaseResult readOne(String msgId) {

        log.info("阅读用户消息，消息ID={}", msgId);
        UserMsgDomain userMsgDomain = new UserMsgDomain();
        userMsgDomain.setMsgId(msgId);
        userMsgService.updateReadStatus(userMsgDomain);

        return new BaseResult(LoanConstants.SUCCESS, "阅读成功");
    }

    /**
     * 更新用户所有消息
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/readAll")
    public BaseResult readAll(String userId) {

        log.info("阅读全部消息，用户ID={}", userId);
        if (userId == null) {
            throw new BusinessException("您没有登录");
        }
        userMsgService.updateStatusByUserId(userId);
        log.info("更新成功");

        return new BaseResult(LoanConstants.SUCCESS, "阅读成功");
    }

    private void encryptInfo(UserInfoDomain userInfoDomain) throws Exception {

        if (null != userInfoDomain.getUserMobileNo()) {
            userInfoDomain.setUserMobileNoHid(DesensitizeUtil.phone(userInfoDomain.getUserMobileNo()));
            userInfoDomain.setUserMobileNo(DesUtil.encrypt(userInfoDomain.getUserMobileNo()));
        }
        if (null != userInfoDomain.getUserMail()) {
            userInfoDomain.setUserMailHid(DesensitizeUtil.email(userInfoDomain.getUserMail(), 3));
            userInfoDomain.setUserMail(DesUtil.encrypt(userInfoDomain.getUserMail()));
        }
        if (null != userInfoDomain.getPassword()) {
            userInfoDomain.setPassword(DesUtil.encrypt(userInfoDomain.getPassword()));
        }
    }
}
