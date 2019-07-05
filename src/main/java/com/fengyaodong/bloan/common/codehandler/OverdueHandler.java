package com.fengyaodong.bloan.common.codehandler;

/**
 * 逾期Handler
 * 0：未逾期 1：已逾期
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:32
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:32
 */
public interface OverdueHandler {

    /**
     * 获取逾期Code
     *
     * @return
     */
    String getOverdueHandlerCode();

    /**
     * 设置逾期Text
     *
     * @param text
     */
    void setOverdueHandlerText(String text);
}
