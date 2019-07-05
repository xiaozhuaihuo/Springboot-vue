package com.fengyaodong.bloan.dao.mapper;

import com.fengyaodong.bloan.dao.config.GenericMapper;
import com.fengyaodong.bloan.model.domain.DictionaryDomain;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数据字典Mapper
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/7 17:16
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/7 17:16
 */
@Mapper
public interface DictionaryMapper extends GenericMapper<DictionaryDomain, String> {

    /**
     * 根据字典项编码查找数值
     *
     * @param itemCode
     * @return
     */
    BigDecimal findValueByCode(String itemCode);

    /**
     * 根据字典的父级id得到Map
     *
     * @param itemCode
     * @return
     */
    List<DictionaryDomain> findByParentItemCode(String itemCode);
}
