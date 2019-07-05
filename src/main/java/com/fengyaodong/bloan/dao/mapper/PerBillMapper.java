package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.condition.PerBillCondition;
import com.fengyaodong.bloan.model.domain.PerBillDomain;
import com.fengyaodong.bloan.model.vo.CountOverdueVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 每期账单Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/2/28 9:19
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/2/28 9:19
 */
@Mapper
public interface PerBillMapper extends GenericMapper<PerBillDomain, String> {

    /**
     * 根据当前日期查询未结清的子账单与已逾期的子账单
     *
     * @param condition
     * @return
     */
    List<PerBillDomain> findAllByDate(PerBillCondition condition);

    /**
     * 根据当前日期查询未结清的子账单与已逾期的子账单应还总金额
     *
     * @param condition
     * @return
     */
    BigDecimal countTotAmtByDate(PerBillCondition condition);

    /**
     * 根据当前日期查询未结清与已逾期的自动还款账单
     *
     * @param date
     * @return
     */
    List<PerBillDomain> findAutoRepayByDate(Date date);

    /**
     * 根据当前日期查询当期未结清的账单
     *
     * @param date
     * @return
     */
    List<PerBillDomain> findNotAutoByDate(Date date);

    /**
     * 根据当前日期查询之前所有未结清账单
     *
     * @param date
     * @return
     */
    List<PerBillDomain> findAllByDateBefore(Date date);

    /**
     * 根据总账单号统计未结清已逾期的账单数
     *
     * @param billNo
     * @return
     */
    Integer countAllByOverdue(String billNo);

    /**
     * 根据父账单号更新所有子账单信息
     *
     * @param perBillDomain
     */
    void updateByBillNo(PerBillDomain perBillDomain);

    /**
     * 计算用户逾期账单总数
     *
     * @return
     */
    List<CountOverdueVo> countOverdue();

    /**
     * 更新自动还款状态
     *
     * @param isAutoRepay
     * @param userId
     */
    void autoRepayByUserId(@Param("isAutoRepay") String isAutoRepay, @Param("userId") String userId);

    /**
     * 查询已结清账单
     *
     * @param userId
     * @return
     */
    List<PerBillDomain> findClearBill(String userId);
}
