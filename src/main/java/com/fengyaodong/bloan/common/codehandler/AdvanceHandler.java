package com.fengyaodong.bloan.common.codehandler;

/**
 * 提前结清Handler
 * 0：否 1：是
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:35
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:35
 */
public interface AdvanceHandler {

    /**
     * 获取提前结清Code
     *
     * @return
     */
    String getAdvanceHandlerCode();

    /**
     * 设置提前结清Text
     *
     * @param text
     */
    void setAdvanceHandlerText(String text);
}
