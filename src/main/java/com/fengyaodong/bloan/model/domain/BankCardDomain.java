package com.fengyaodong.bloan.model.domain;

import com.fengyaodong.bloan.common.codehandler.CardTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 银行卡Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/12 17:40
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/12 17:40
 */
@ApiModel
@Data
@Accessors(chain = true)
public class BankCardDomain implements Serializable, CardTypeHandler {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("银行卡ID")
    private String cardId;

    @ApiModelProperty("银行卡类型 D：借记卡 C：贷记卡")
    private String cardType;

    @ApiModelProperty("银行卡号")
    private String bankCardNo;

    @ApiModelProperty("脱敏银行卡号")
    private String bankCardNoHid;

    @ApiModelProperty("银行卡密码")
    private String bankCardPassword;

    @ApiModelProperty("银行预留手机号")
    private String bankMobNo;

    @ApiModelProperty("脱敏银行预留手机号")
    private String bankMobNoHid;

    @ApiModelProperty("银行卡余额")
    private BigDecimal bankCardBalance;

    @ApiModelProperty("银行卡姓名")
    private String bankCardName;

    @ApiModelProperty("脱敏银行卡姓名")
    private String bankCardNameHid;

    @ApiModelProperty("每日密码错误次数")
    private Integer errorNumber;

    @Override
    public String getCardTypeHandlerCode() {
        return this.getCardType();
    }

    @Override
    public void setCardTypeHandlerText(String text) {
        this.setCardType(text);
    }
}
