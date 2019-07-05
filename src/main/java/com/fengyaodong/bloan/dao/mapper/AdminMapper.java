package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.domain.AdminDomain;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 20:38
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 20:38
 */
@Mapper
public interface AdminMapper extends GenericMapper<AdminDomain, String> {

    /**
     * 根据登录账号查询管理员信息
     *
     * @param loginNo
     * @return
     */
    AdminDomain findOneByLoginNo(String loginNo);
}
