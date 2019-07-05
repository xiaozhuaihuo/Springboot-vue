package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期返回Vo
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 13:54
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 13:54
 */
@ApiModel
@Data
@Accessors(chain = true)
public class DateResVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("起始日期")
    private Date startDate;

    @ApiModelProperty("结束日期")
    private Date endDate;
}
