package com.fengyaodong.bloan.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据字典Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 17:10
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 17:10
 */
@ApiModel
@Data
@Accessors(chain = true)
public class DictionaryDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String uuid;

    @ApiModelProperty("字典项编码")
    private String itemCode;

    @ApiModelProperty("字典项名称")
    private String itemName;

    @ApiModelProperty("字典项数值")
    private BigDecimal itemValue;

    @ApiModelProperty("字典项描述")
    private String itemDesc;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("父级ID")
    private String parentId;

    @ApiModelProperty("级别")
    private Integer depth;
}
