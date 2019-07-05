package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.domain.RateDomain;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

/**
 * 利率Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 10:42
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 10:42
 */
@Mapper
public interface RateMapper extends GenericMapper<RateDomain, String> {

    /**
     * 根据利率名称查询数值
     *
     * @param rateName
     * @return
     */
    BigDecimal findValueByName(String rateName);
}
