package com.fengyaodong.bloan.common.codehandler;

/**
 * 银行卡类型Handler
 * D：借记卡 C：贷记卡
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:24
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:24
 */
public interface CardTypeHandler {

    /**
     * 获取银行卡类型Code
     *
     * @return
     */
    String getCardTypeHandlerCode();

    /**
     * 设置银行卡类型Text
     *
     * @param text
     */
    void setCardTypeHandlerText(String text);
}
