package com.fengyaodong.bloan.model.condition;

import com.fengyaodong.bloan.model.domain.BindCardDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 绑卡Condition
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 17:37
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 17:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BindCardCondition extends BindCardDomain {

    @ApiModelProperty("页面容量")
    private Integer pageSize;

    @ApiModelProperty("页数")
    private Integer pageNum;
}
