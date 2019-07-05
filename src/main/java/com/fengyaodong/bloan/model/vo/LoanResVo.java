package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 贷款响应参数
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 15:50
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 15:50
 */
@ApiModel
@Data
@Accessors(chain = true)
public class LoanResVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("还款总金额")
    private BigDecimal repayTotalAmount;

    @ApiModelProperty("还款总利息")
    private BigDecimal repayInterestAmount;

    @ApiModelProperty("每月还款列表")
    private List<PerMonthRepayVo> perMonthRepayVoList;
}
