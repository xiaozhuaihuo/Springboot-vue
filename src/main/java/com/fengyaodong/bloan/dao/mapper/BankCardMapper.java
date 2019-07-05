package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.domain.BankCardDomain;
import org.apache.ibatis.annotations.Mapper;

/**
 * 银行卡Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/13 17:46
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/13 17:46
 */
@Mapper
public interface BankCardMapper extends GenericMapper<BankCardDomain, String> {

    /**
     * 查询对应的银行卡信息
     *
     * @param bankCardDomain
     * @return
     */
    BankCardDomain findOne(BankCardDomain bankCardDomain);

    /**
     * 更新所有银行卡信息
     *
     * @param bankCardDomain
     */
    void updateAll(BankCardDomain bankCardDomain);
}
