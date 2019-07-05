package com.fengyaodong.bloan.model.condition;

import com.fengyaodong.bloan.model.domain.UserInfoDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息Condition
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 9:41
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 9:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoCondition extends UserInfoDomain {

    @ApiModelProperty("页面容量")
    private Integer pageSize;

    @ApiModelProperty("页数")
    private Integer pageNum;
}
