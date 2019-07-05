package com.fengyaodong.bloan.model.condition;

import com.fengyaodong.bloan.model.domain.UserMsgDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户消息Condition
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 17:03
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 17:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMsgCondition extends UserMsgDomain {

    @ApiModelProperty("页面容量")
    private Integer pageSize;

    @ApiModelProperty("页数")
    private Integer pageNum;
}
