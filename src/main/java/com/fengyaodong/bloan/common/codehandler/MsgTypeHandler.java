package com.fengyaodong.bloan.common.codehandler;

/**
 * 消息类型Handler
 * 0：还款提醒 1：扣款提醒 2：逾期提醒 3：申请额度提醒 4：提升额度提醒 5：驳回提醒 6：批准提醒
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:09
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:09
 */
public interface MsgTypeHandler {

    /**
     * 获取消息类型Code
     *
     * @return
     */
    String getMsgTypeHandlerCode();

    /**
     * 设置消息类型Text
     *
     * @param text
     */
    void setMsgTypeHandlerText(String text);
}
