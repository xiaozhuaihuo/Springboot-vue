package com.fengyaodong.bloan.common.codehandler;

/**
 * 性别Handler
 * M：男 F：女
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:27
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:27
 */
public interface GenderHandler {

    /**
     * 获取性别Code
     *
     * @return
     */
    String getGenderHandlerCode();

    /**
     * 设置性别Text
     *
     * @param text
     */
    void setGenderHandlerText(String text);
}
