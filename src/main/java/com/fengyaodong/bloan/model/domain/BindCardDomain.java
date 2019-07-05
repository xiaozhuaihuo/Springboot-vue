package com.fengyaodong.bloan.model.domain;

import com.fengyaodong.bloan.common.codehandler.CardTypeHandler;
import com.fengyaodong.bloan.common.codehandler.DefaultHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 绑卡Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 17:32
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 17:32
 */
@ApiModel
@Data
@Accessors(chain = true)
public class BindCardDomain implements Serializable, CardTypeHandler, DefaultHandler {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("绑卡ID")
    private String bindId;

    @ApiModelProperty("签约日期")
    private Date signDate;

    @ApiModelProperty("是否为默认卡")
    private String isDefault;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("银行预留手机号")
    private String bankMobNo;

    @ApiModelProperty("脱敏银行预留手机号")
    private String bankMobNoHid;

    @ApiModelProperty("银行卡类型 D:借记卡 C：贷记卡")
    private String cardType;

    @ApiModelProperty("银行卡卡号")
    private String bankCardNo;

    @ApiModelProperty("脱敏银行卡卡号")
    private String bankCardNoHid;

    @ApiModelProperty("银行卡姓名")
    private String bankCardName;

    @ApiModelProperty("脱敏银行卡姓名")
    private String bankCardNameHid;

    @Override
    public String getCardTypeHandlerCode() {
        return this.getCardType();
    }

    @Override
    public void setCardTypeHandlerText(String text) {
        this.setCardType(text);
    }

    @Override
    public String getDefaultHandlerCode() {
        return this.getIsDefault();
    }

    @Override
    public void setDefaultHandlerText(String text) {
        this.setIsDefault(text);
    }
}
