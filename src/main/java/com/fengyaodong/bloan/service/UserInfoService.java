package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.exception.BusinessException;
import com.fengyaodong.bloan.common.util.DesUtil;
import com.fengyaodong.bloan.common.util.DesensitizeUtil;
import com.fengyaodong.bloan.common.util.UUIDUtils;
import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.UserInfoMapper;
import com.fengyaodong.bloan.model.domain.UserInfoDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户信息Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/5 15:16
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/5 15:16
 */
@Service
public class UserInfoService implements AbstractService<UserInfoDomain, String> {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfoMapper getMapper() {
        return userInfoMapper;
    }

    /**
     * 根据用户名查询用户
     *
     * @return
     */
    public UserInfoDomain findOneByLoginNo(String loginNo) {
        return userInfoMapper.findOneByLoginNo(loginNo);
    }

    /**
     * 检测用户名是否已被使用
     *
     * @param loginNo
     */
    public void testUserLoginNo(String loginNo) {

        //根据用户名查询用户信息
        UserInfoDomain userInfoDomain = findOneByLoginNo(loginNo);
        if (userInfoDomain != null) {
            throw new BusinessException("该用户名已被注册，请重新填写用户名");
        }
    }

    /**
     * 用户注册添加信息
     *
     * @param userInfoDomain
     * @throws Exception
     */
    public void addUserInfo(UserInfoDomain userInfoDomain) throws Exception {

        //测试用户名是否可用
        testUserLoginNo(userInfoDomain.getLoginNo());
        //加密数据
        encryptInfo(userInfoDomain);
        userInfoDomain.setUserId(UUIDUtils.getUUID());
        userInfoDomain.setRegisterDate(new Date());
        userInfoDomain.setCreditScore(LoanConstants.TOT_CREDIT_SCORE);
        userInfoDomain.setIsAutoRepay(LoanConstants.NOT_AUTO_REPAY);
        //新增用户信息
        userInfoMapper.add(userInfoDomain);

    }

    /**
     * 用户登录
     *
     * @param userInfoDomain
     * @return
     * @throws Exception
     */
    public UserInfoDomain userLogin(UserInfoDomain userInfoDomain) throws Exception {

        //加密数据
        userInfoDomain.setPassword(DesUtil.encrypt(userInfoDomain.getPassword()));
        List<UserInfoDomain> list = userInfoMapper.find(userInfoDomain);

        if (list == null || list.size() != 1) {

            throw new BusinessException("用户名密码不匹配");
        }
        return list.get(0);
    }

    /**
     * 更新修改后的用户信息
     *
     * @param userInfoDomain
     * @throws Exception
     */
    public void updateUserInfo(UserInfoDomain userInfoDomain) throws Exception {

        //加密数据
        encryptInfo(userInfoDomain);
        userInfoDomain.setUpdateDate(new Date());
        userInfoMapper.updateById(userInfoDomain);
    }

    /**
     * 加密数据
     *
     * @param userInfoDomain
     * @throws Exception
     */
    private void encryptInfo(UserInfoDomain userInfoDomain) throws Exception {

        userInfoDomain.setUserNameHid(DesensitizeUtil.right(userInfoDomain.getUserName(), 1));
        userInfoDomain.setUserIdNoHid(DesensitizeUtil.idNo(userInfoDomain.getUserIdNo()));
        userInfoDomain.setUserMobileNoHid(DesensitizeUtil.phone(userInfoDomain.getUserMobileNo()));
        userInfoDomain.setUserMailHid(DesensitizeUtil.email(userInfoDomain.getUserMail(), 3));
        userInfoDomain.setPassword(DesUtil.encrypt(userInfoDomain.getPassword()));
        userInfoDomain.setUserName(DesUtil.encrypt(userInfoDomain.getUserName()));
        userInfoDomain.setUserIdNo(DesUtil.encrypt(userInfoDomain.getUserIdNo()));
        userInfoDomain.setUserMobileNo(DesUtil.encrypt(userInfoDomain.getUserMobileNo()));
        userInfoDomain.setUserMail(DesUtil.encrypt(userInfoDomain.getUserMail()));
    }
}
