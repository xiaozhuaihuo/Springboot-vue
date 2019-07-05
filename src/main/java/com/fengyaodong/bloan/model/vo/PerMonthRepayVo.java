package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 每月还款Vo
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/9 17:41
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/9 17:41
 */
@ApiModel
@Data
@Accessors(chain = true)
public class PerMonthRepayVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("月还款总额")
    private BigDecimal perRepayTotalAmt;

    @ApiModelProperty("月还款本金")
    private BigDecimal perRepayCapitalAmt;

    @ApiModelProperty("月还款利息")
    private BigDecimal perRepayInterestAmt;

    @ApiModelProperty("还款期数")
    private Integer repayStage;
}
