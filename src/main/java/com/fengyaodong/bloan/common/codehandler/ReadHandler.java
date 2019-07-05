package com.fengyaodong.bloan.common.codehandler;

/**
 * 阅读状态Handler
 * 0：未读 1：已读
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:19
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:19
 */
public interface ReadHandler {

    /**
     * 获取阅读状态Code
     *
     * @return
     */
    String getReadHandlerCode();

    /**
     * 设置阅读状态Text
     *
     * @param text
     */
    void setReadHandlerText(String text);
}
