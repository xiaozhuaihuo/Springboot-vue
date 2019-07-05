package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户消息请求Vo
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 17:36
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 17:36
 */
@ApiModel
@Data
@Accessors(chain = true)
public class UserMsgVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账单号")
    private String billNo;

    @ApiModelProperty("消息类型 0：还款提醒 1：扣款提醒 2：逾期提醒")
    private String msgType;

    @ApiModelProperty("用户ID")
    private String userId;
}
