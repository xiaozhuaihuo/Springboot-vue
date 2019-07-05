package com.fengyaodong.bloan.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 利率Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 10:11
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 10:11
 */
@ApiModel
@Data
@Accessors(chain = true)
public class RateDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("利率名称")
    private String rateName;

    @ApiModelProperty("利率值")
    private String rateValue;
}
