package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.domain.UserInfoDomain;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/28 9:20
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/28 9:20
 */
@Mapper
public interface UserInfoMapper extends GenericMapper<UserInfoDomain, String> {

    /**
     * 根据用户名查询用户
     *
     * @param loginNo
     * @return
     */
    UserInfoDomain findOneByLoginNo(String loginNo);
}
