package com.fengyaodong.bloan.model.condition;

import com.fengyaodong.bloan.model.domain.BankCardDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 银行卡Condition
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/13 16:42
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/13 16:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BankCardCondition extends BankCardDomain {

    @ApiModelProperty("页面容量")
    private Integer pageSize;

    @ApiModelProperty("页数")
    private Integer pageNum;
}
