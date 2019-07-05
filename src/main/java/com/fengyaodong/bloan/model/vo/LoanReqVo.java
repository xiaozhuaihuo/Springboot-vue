package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贷款请求参数
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 15:42
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 15:42
 */
@ApiModel
@Data
@Accessors(chain = true)
public class LoanReqVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("贷款金额")
    private BigDecimal loanAmount;

    @ApiModelProperty("贷款期限")
    private Integer loanPeriod;

    @ApiModelProperty("还款方式")
    private String repayMethod;
}
