package com.fengyaodong.bloan.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 管理员Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/12 20:34
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/12 20:34
 */
@ApiModel
@Data
@Accessors(chain = true)
public class AdminDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("管理员ID")
    private String adminId;

    @ApiModelProperty("管理员账号")
    private String adminLoginNo;

    @ApiModelProperty("管理员名称")
    private String adminName;

    @ApiModelProperty("管理员密码")
    private String adminPassword;

    @ApiModelProperty("管理员剩余金额")
    private BigDecimal adminSurplusAmt;
}
