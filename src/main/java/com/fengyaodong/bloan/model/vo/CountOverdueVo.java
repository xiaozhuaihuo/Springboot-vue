package com.fengyaodong.bloan.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 计算逾期账单数Vo
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/4/4 16:02
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/4/4 16:02
 */
@ApiModel
@Data
@Accessors(chain = true)
public class CountOverdueVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("逾期账单数")
    private Integer overdueNumber;
}
