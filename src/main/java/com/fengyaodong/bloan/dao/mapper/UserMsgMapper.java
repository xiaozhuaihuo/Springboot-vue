package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.condition.UserMsgCondition;
import com.fengyaodong.bloan.model.domain.UserMsgDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户消息Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 17:02
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 17:02
 */
@Mapper
public interface UserMsgMapper extends GenericMapper<UserMsgDomain, String> {

    /**
     * 根据用户ID统计未读消息条数
     *
     * @param userId
     */
    Integer countByUserId(String userId);

    /**
     * 根据用户ID查询用户未读消息
     *
     * @param userId
     * @return
     */
    List<UserMsgDomain> findAllByUserId(String userId);

    /**
     * 查询所有待审批信息
     *
     * @param condition
     * @return
     */
    List<UserMsgDomain> findAllByMsgType(UserMsgCondition condition);

    /**
     * 更新用户所有消息
     *
     * @param userId
     */
    void updateStatusByUserId(String userId);
}
