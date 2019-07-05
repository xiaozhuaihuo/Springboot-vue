package com.fengyaodong.bloan.common.codehandler;

/**
 * 结清Handler
 * 0：未结清 1：已结清
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:34
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:34
 */
public interface ClearHandler {

    /**
     * 获取结清Code
     *
     * @return
     */
    String getClearHandlerCode();

    /**
     * 设置结清Text
     *
     * @param text
     */
    void setClearHandlerText(String text);
}
