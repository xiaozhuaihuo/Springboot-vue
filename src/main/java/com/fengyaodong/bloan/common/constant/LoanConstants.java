package com.fengyaodong.bloan.common.constant;

import java.math.BigDecimal;

/**
 * 常量类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 16:30
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 16:30
 */
public class LoanConstants {

    /************************* 利率的百分数，利率数/100*************/
    public static final BigDecimal bc = new BigDecimal(100);

    /**
     * 利息率
     */
    public static final String INTEREST_RATE = "INTEREST_RATE";

    /**
     * 罚金率
     */
    public static final String FINE_RATE = "FINE_RATE";

    /**
     * 滞纳金率
     */
    public static final String FEE_RATE = "FEE_RATE";

    /**
     * 月数
     */
    public static final BigDecimal MONTHS = new BigDecimal(12);

    /**
     * 保留小数点后位数
     */
    public static final Integer PLAN_TEN = 10;

    /**
     * 卡类型
     */
    public static final String CARD_TYPE = "CARD_TYPE";

    /**
     * 借记卡
     */
    public static final String DEBIT = "D";

    /**
     * 贷记卡
     */
    public static final String CREDIT = "C";

    /**
     * 是否还清
     */
    public static final String CLEAR = "CLEAR";

    /**
     * 已还清
     */
    public static final String IS_CLEAR = "1";

    /**
     * 未还清
     */
    public static final String NOT_CLEAR = "0";

    /**
     * 是否默认卡
     */
    public static final String DEFAULT = "DEFAULT";

    /**
     * 是默认卡
     */
    public static final String IS_DEFAULT = "Y";

    /**
     * 不是默认卡
     */
    public static final String NOT_DEFAULT = "N";

    /**
     * 是否提前结清
     */
    public static final String ADVANCE = "ADVANCE";

    /**
     * 提前结清
     */
    public static final String IS_ADVANCE = "1";

    /**
     * 未提前结清
     */
    public static final String NOT_ADVANCE = "0";

    /**
     * 是否逾期
     */
    public static final String OVERDUE = "OVERDUE";

    /**
     * 已逾期
     */
    public static final String IS_OVERDUE = "1";

    /**
     * 未逾期
     */
    public static final String NOT_OVERDUE = "0";

    /**
     * 自动还款
     */
    public static final String AUTO_REPAY = "AUTO_REPAY";

    /**
     * 是自动还款
     */
    public static final String IS_AUTO_REPAY = "1";

    /**
     * 不是自动还款
     */
    public static final String NOT_AUTO_REPAY = "0";

    /**
     * 性别
     */
    public static final String GENDER = "GENDER";

    /**
     * 性别：男
     */
    public static final String MALE = "M";

    /**
     * 性别：女
     */
    public static final String FEMALE = "F";

    /**
     * 还款方式
     */
    public static final String REPAY_METHOD = "REPAY_METHOD";

    /**
     * 等额本息
     */
    public static final String AVERAGE_CAPITAL_PLUS_INTEREST = "AVERAGE_CAPITAL_PLUS_INTEREST";

    /**
     * 等额本金
     */
    public static final String AVERAGE_CAPITAL = "AVERAGE_CAPITAL";

    /**
     * 账单号前缀
     */
    public static final String BILL_PREFIX = "BILL";

    /**
     * 初始额度
     */
    public static final String INIT_QUOTA = "INIT_QUOTA";

    /**
     * 管理员
     */
    public static final String ADMIN = "admin";

    /**
     * 还款形式：立即还款（一期）
     */
    public static final String IMMEDIATE_REPAY = "0";

    /**
     * 一键还款（当前所有期）
     */
    public static final String ONE_KEY_REPAY = "1";

    /**
     * 一键还款（当前所有逾期账单）
     */
    public static final String ONE_KEY_REPAY_OVERDUE = "2";

    /**
     * 提前还款（父账单所有期）
     */
    public static final String ADVANCE_REPAY = "3";

    /**
     * 是否阅读
     */
    public static final String READ = "READ";

    /**
     * 已阅读
     */
    public static final String IS_READ = "1";

    /**
     * 未阅读
     */
    public static final String NOT_READ = "0";

    /**
     * 消息类型
     */
    public static final String MSG_TYPE = "MSG_TYPE";

    /**
     * 还款提醒
     */
    public static final String REPAY_REMIND = "0";

    /**
     * 扣款提醒
     */
    public static final String DEEUCT_REMIND = "1";

    /**
     * 逾期提醒
     */
    public static final String OVERDUE_REMIND = "2";

    /**
     * 申请额度提醒
     */
    public static final String APPLY_QUOTA = "3";

    /**
     * 提升额度提醒
     */
    public static final String PROMOTE_QUOTA = "4";

    /**
     * 驳回提醒
     */
    public static final String REJECT = "5";

    /**
     * 批准提醒
     */
    public static final String APPROVAL = "6";

    /**
     * 银行卡密码当日错误次数
     */
    public static final Integer ERROR_NUMBER = 3;

    /**
     * 最低贷款额
     */
    public static final String MIN_LOAN_AMT = "MIN_LOAN_AMT";
    /**
     * 请求成功Code
     */
    public static final int SUCCESS = 1;

    /**
     * 用户最大逾期账单数
     */
    public static final Integer MAX_OVERDUE_NUMBER = 6;

    /**
     * 初始信誉分
     */
    public static final Integer TOT_CREDIT_SCORE = 100;
}
