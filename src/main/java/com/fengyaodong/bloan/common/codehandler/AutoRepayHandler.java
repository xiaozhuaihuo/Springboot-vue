package com.fengyaodong.bloan.common.codehandler;

/**
 * 自动还款Handler
 * 0：否 1：是
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:21
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:21
 */
public interface AutoRepayHandler {

    /**
     * 获取自动还款Code
     *
     * @return
     */
    String getAutoRepayHandlerCode();

    /**
     * 设置自动还款Text
     *
     * @param text
     */
    void setAutoRepayHandlerText(String text);
}
