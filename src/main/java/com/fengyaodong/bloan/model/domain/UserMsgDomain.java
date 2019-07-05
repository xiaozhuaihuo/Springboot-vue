package com.fengyaodong.bloan.model.domain;

import com.fengyaodong.bloan.common.codehandler.MsgTypeHandler;
import com.fengyaodong.bloan.common.codehandler.ReadHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户消息Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 16:59
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 16:59
 */
@ApiModel
@Data
@Accessors(chain = true)
public class UserMsgDomain implements Serializable, ReadHandler, MsgTypeHandler {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息ID")
    private String msgId;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("账单号")
    private String billNo;

    @ApiModelProperty("消息类型 0：还款提醒 1：扣款提醒 2：逾期提醒")
    private String msgType;

    @ApiModelProperty("消息内容")
    private String msgContent;

    @ApiModelProperty("是否阅读 0：否 1：是")
    private String isRead;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @Override
    public String getReadHandlerCode() {
        return this.getIsRead();
    }

    @Override
    public void setReadHandlerText(String text) {
        this.setIsRead(text);
    }

    @Override
    public String getMsgTypeHandlerCode() {
        return this.getMsgType();
    }

    @Override
    public void setMsgTypeHandlerText(String text) {
        this.setMsgType(text);
    }
}
