package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.domain.BindCardDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 绑卡Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/12 17:37
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/12 17:37
 */
@Mapper
public interface BindCardMapper extends GenericMapper<BindCardDomain, String> {

    /**
     * 根据用户ID查询绑卡信息
     *
     * @param userId
     * @return
     */
    List<BindCardDomain> findAllByUserId(String userId);

    /**
     * 根据用户ID和默认卡查询绑卡信息
     *
     * @param userId
     * @return
     */
    BindCardDomain findAllByUserIdAndDefault(String userId);

    /**
     * 根据用户ID更新该用户所有卡信息
     *
     * @param userId
     */
    void updateByUserId(String userId);

    /**
     * 根据卡号更新该卡信息
     *
     * @param cardId
     */
    void updateByCardId(String cardId);
}
