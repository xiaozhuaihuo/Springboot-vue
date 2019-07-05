package com.fengyaodong.bloan.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户额度Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/27 15:48
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/27 15:48
 */
@ApiModel
@Data
@Accessors(chain = true)
public class UserQuotaDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("贷款总额度")
    private BigDecimal loanTotalQuota;

    @ApiModelProperty("贷款已用额度")
    private BigDecimal loanUsedQuota;

    @ApiModelProperty("贷款剩余额度")
    private BigDecimal loanSurplusQuota;

    @ApiModelProperty("申请额度日期")
    private Date applyQuotaDate;

    @ApiModelProperty("提升额度日期")
    private Date promoteQuotaDate;
}

