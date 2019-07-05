package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.domain.BillDomain;
import com.fengyaodong.bloan.model.vo.SumAmtVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 总账单Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/28 9:17
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/28 9:17
 */
@Mapper
public interface BillMapper extends GenericMapper<BillDomain, String> {

    /**
     * 根据子账单更新所有父账单信息
     */
    void updateBySubBill();

    /**
     * 更新总账单状态
     */
    void updateStatus();

    /**
     * 计算账单总金额与还款总金额
     *
     * @param userId
     * @return
     */
    SumAmtVo countAmtByUserId(String userId);

    /**
     * 更新自动还款状态
     *
     * @param isAutoRepay
     * @param userId
     */
    void autoRepayByUserId(@Param("isAutoRepay") String isAutoRepay, @Param("userId") String userId);
}
