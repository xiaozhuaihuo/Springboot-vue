package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 驳回Vo
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/14 10:55
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/14 10:55
 */
@ApiModel
@Data
@Accessors(chain = true)
public class ForbidVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息ID")
    private String msgId;

    @ApiModelProperty("驳回原因")
    private String reason;
}
