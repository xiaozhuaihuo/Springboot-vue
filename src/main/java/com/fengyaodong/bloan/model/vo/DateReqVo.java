package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期请求Vo
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 13:52
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 13:52
 */
@ApiModel
@Data
@Accessors(chain = true)
public class DateReqVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("请求日期")
    private Date date;

    @ApiModelProperty("账单期数")
    private Integer stageNo;
}
