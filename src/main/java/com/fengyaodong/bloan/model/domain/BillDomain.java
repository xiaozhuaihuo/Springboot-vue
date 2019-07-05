package com.fengyaodong.bloan.model.domain;

import com.fengyaodong.bloan.common.codehandler.AdvanceHandler;
import com.fengyaodong.bloan.common.codehandler.AutoRepayHandler;
import com.fengyaodong.bloan.common.codehandler.ClearHandler;
import com.fengyaodong.bloan.common.codehandler.RepayMethodHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 总账单Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/27 16:01
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/27 16:01
 */
@ApiModel
@Data
@Accessors(chain = true)
public class BillDomain implements Serializable, ClearHandler, AdvanceHandler, RepayMethodHandler, AutoRepayHandler {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账单号")
    private String billNo;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("账单登记日期")
    private Date billRegisterDate;

    @ApiModelProperty("账单最终还款日期")
    private Date billFinalRepayDate;

    @ApiModelProperty("分期期数")
    private Integer stagesNumber;

    @ApiModelProperty("账单状态")
    private String billStatus;

    @ApiModelProperty("账单总金额")
    private BigDecimal billTotalAmount;

    @ApiModelProperty("账单本金金额")
    private BigDecimal billPrincipalAmount;

    @ApiModelProperty("账单利息金额")
    private BigDecimal billInterestAmount;

    @ApiModelProperty("账单罚金金额")
    private BigDecimal billFineAmount;

    @ApiModelProperty("账单滞纳金金额")
    private BigDecimal billFeeAmount;

    @ApiModelProperty("已还款总金额")
    private BigDecimal repayTotalAmount;

    @ApiModelProperty("已还款本金金额")
    private BigDecimal repayPrincipalAmount;

    @ApiModelProperty("已还款利息金额")
    private BigDecimal repayInterestAmount;

    @ApiModelProperty("已还款罚金金额")
    private BigDecimal repayFineAmount;

    @ApiModelProperty("已还款滞纳金金额")
    private BigDecimal repayFeeAmount;

    @ApiModelProperty("已还清期数")
    private Integer repayStagesNumber;

    @ApiModelProperty("是否提前结清")
    private String isAdvanceSettle;

    @ApiModelProperty("还款方式")
    private String repayMethod;

    @ApiModelProperty("是否主动还款")
    private String isAutoRepay;

    @ApiModelProperty("提前结清还款金额")
    private BigDecimal advanceRepayAmt;

    @ApiModelProperty("结清日期")
    private Date repayDate;

    @Override
    public String getClearHandlerCode() {
        return this.getBillStatus();
    }

    @Override
    public void setClearHandlerText(String text) {
        this.setBillStatus(text);
    }

    @Override
    public String getAdvanceHandlerCode() {
        return this.getIsAdvanceSettle();
    }

    @Override
    public void setAdvanceHandlerText(String text) {
        this.setIsAdvanceSettle(text);
    }

    @Override
    public String getRepayMethodHandlerCode() {
        return this.getRepayMethod();
    }

    @Override
    public void setRepayMethodHandlerText(String text) {
        this.setRepayMethod(text);
    }

    @Override
    public String getAutoRepayHandlerCode() {
        return this.getIsAutoRepay();
    }

    @Override
    public void setAutoRepayHandlerText(String text) {
        this.setIsAutoRepay(text);
    }
}
