package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 还款Vo
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 18:08
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 18:08
 */
@ApiModel
@Data
@Accessors(chain = true)
public class RepayVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("还款账单号")
    private List<String> billNoList;

    @ApiModelProperty("还款金额")
    private BigDecimal repayAmt;

    @ApiModelProperty("还款银行卡号")
    private String repayCardNo;

    @ApiModelProperty("支付密码")
    private String repayPassword;

    @ApiModelProperty("还款形式 0:立即还款（一期）1:一键还款（当前所有期）2：一键还款（当前所有逾期账单） 3：提前还款（父账单所有期）")
    private String repayForm;
}
