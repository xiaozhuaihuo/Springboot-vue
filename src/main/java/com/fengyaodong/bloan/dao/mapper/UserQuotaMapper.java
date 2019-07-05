package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.domain.UserQuotaDomain;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

/**
 * 用户额度Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/28 9:20
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/28 9:20
 */
@Mapper
public interface UserQuotaMapper extends GenericMapper<UserQuotaDomain, String> {

    /**
     * 根据用户ID查询用户剩余额度
     *
     * @param userId
     * @return
     */
    BigDecimal findSurplusQuotaByUserId(String userId);
}
