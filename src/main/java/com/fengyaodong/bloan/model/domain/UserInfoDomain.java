package com.fengyaodong.bloan.model.domain;

import com.fengyaodong.bloan.common.codehandler.AutoRepayHandler;
import com.fengyaodong.bloan.common.codehandler.GenderHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息Domain
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/27 15:11
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/27 15:11
 */
@ApiModel
@Data
@Accessors(chain = true)
public class UserInfoDomain implements Serializable, GenderHandler, AutoRepayHandler {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户名")
    private String loginNo;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("脱敏用户姓名")
    private String userNameHid;

    @ApiModelProperty("性别")
    private String userSex;

    @ApiModelProperty("出生日期")
    private Date userBirthDate;

    @ApiModelProperty("身份证号")
    private String userIdNo;

    @ApiModelProperty("脱敏身份证号")
    private String userIdNoHid;

    @ApiModelProperty("手机号")
    private String userMobileNo;

    @ApiModelProperty("脱敏手机号")
    private String userMobileNoHid;

    @ApiModelProperty("联系地址")
    private String userAddress;

    @ApiModelProperty("电子邮件")
    private String userMail;

    @ApiModelProperty("脱敏电子邮件")
    private String userMailHid;

    @ApiModelProperty("注册日期")
    private Date registerDate;

    @ApiModelProperty("更新日期")
    private Date updateDate;

    @ApiModelProperty("信誉分数")
    private Integer creditScore;

    @ApiModelProperty("是否自动还款")
    private String isAutoRepay;

    @Override
    public String getGenderHandlerCode() {
        return this.getUserSex();
    }

    @Override
    public void setGenderHandlerText(String text) {
        this.setUserSex(text);
    }

    @Override
    public String getAutoRepayHandlerCode() {
        return this.getIsAutoRepay();
    }

    @Override
    public void setAutoRepayHandlerText(String text) {
        this.setIsAutoRepay(text);
    }
}
