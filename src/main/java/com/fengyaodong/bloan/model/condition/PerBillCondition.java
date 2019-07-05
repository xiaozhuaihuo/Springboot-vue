package com.fengyaodong.bloan.model.condition;

import com.fengyaodong.bloan.model.domain.PerBillDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * ${TODO} 写点注释吧
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/4/1 23:15
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/4/1 23:15
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class PerBillCondition extends PerBillDomain {

    private Date date;

    @ApiModelProperty("页面容量")
    private Integer pageSize;

    @ApiModelProperty("页数")
    private Integer pageNum;
}
