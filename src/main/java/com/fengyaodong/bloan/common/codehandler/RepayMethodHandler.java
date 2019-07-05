package com.fengyaodong.bloan.common.codehandler;

/**
 * 还款方式Handler
 * AVERAGE_CAPITAL_PLUS_INTEREST：等额本息 AVERAGE_CAPITAL：等额本金
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:30
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:30
 */
public interface RepayMethodHandler {

    /**
     * 获取还款方式Code
     *
     * @return
     */
    String getRepayMethodHandlerCode();

    /**
     * 设置还款方式Text
     *
     * @param text
     */
    void setRepayMethodHandlerText(String text);
}
