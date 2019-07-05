package com.fengyaodong.bloan.common.codehandler;

/**
 * 默认卡Handler
 * Y：是 N：否
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:26
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:26
 */
public interface DefaultHandler {

    /**
     * 获取默认卡Code
     *
     * @return
     */
    String getDefaultHandlerCode();

    /**
     * 设置默认卡Text
     *
     * @param text
     */
    void setDefaultHandlerText(String text);
}
