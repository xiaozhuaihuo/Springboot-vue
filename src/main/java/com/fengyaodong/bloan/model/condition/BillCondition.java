package com.fengyaodong.bloan.model.condition;

import com.fengyaodong.bloan.model.domain.BillDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 总账单Condition
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/7 16:01
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/7 16:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BillCondition extends BillDomain {

    @ApiModelProperty("页面容量")
    private Integer pageSize;

    @ApiModelProperty("页数")
    private Integer pageNum;
}
