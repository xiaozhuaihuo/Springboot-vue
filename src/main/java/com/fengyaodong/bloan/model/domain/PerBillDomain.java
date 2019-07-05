package com.fengyaodong.bloan.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 每期账单Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/27 16:22
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/27 16:22
 */
@ApiModel
@Data
@Accessors(chain = true)
public class PerBillDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("当期账单号")
    private String perBillNo;

    @ApiModelProperty("总账单号")
    private String billNo;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("当期起始日期")
    private Date perStartDate;

    @ApiModelProperty("当期结束日期")
    private Date perEndDate;

    @ApiModelProperty("当期状态")
    private String perStatus;

    @ApiModelProperty("当前期数")
    private Integer currentStage;

    @ApiModelProperty("当期账单总金额")
    private BigDecimal perBillTotalAmount;

    @ApiModelProperty("当期账单本金金额")
    private BigDecimal perBillPrincipalAmount;

    @ApiModelProperty("当期账单利息金额")
    private BigDecimal perBillInterestAmount;

    @ApiModelProperty("当前账单罚金金额")
    private BigDecimal perBillFineAmount;

    @ApiModelProperty("当前账单滞纳金金额")
    private BigDecimal perBillFeeAmount;

    @ApiModelProperty("已还款账单总金额")
    private BigDecimal perRepayTotalAmount;

    @ApiModelProperty("已还款本金金额")
    private BigDecimal perRepayPrincipalAmount;

    @ApiModelProperty("已还款利息金额")
    private BigDecimal perRepayInterestAmount;

    @ApiModelProperty("已还款当期罚金金额")
    private BigDecimal perRepayFineAmount;

    @ApiModelProperty("已还款当期滞纳金金额")
    private BigDecimal perRepayFeeAmount;

    @ApiModelProperty("逾期标志 0:否 1:是")
    private String isOverdue;

    @ApiModelProperty("逾期天数")
    private Integer overdueDay;

    @ApiModelProperty("当期还清日期")
    private Date perRepayDate;

    @ApiModelProperty("是否提前结清")
    private String isAdvanceSettle;

    @ApiModelProperty("是否自动还款")
    private String isAutoRepay;
}
