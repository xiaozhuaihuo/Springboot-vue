package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 计算账单总金额与已还款总金额
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/4/6 19:16
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/4/6 19:16
 */
@ApiModel
@Data
@Accessors(chain = true)
public class SumAmtVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账单总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("已还款总金额")
    private BigDecimal repayTotalAmount;
}
