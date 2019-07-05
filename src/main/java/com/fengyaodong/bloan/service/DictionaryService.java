package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.dao.config.AbstractService;
import com.fengyaodong.bloan.dao.mapper.DictionaryMapper;
import com.fengyaodong.bloan.model.domain.DictionaryDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/7 17:16
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/7 17:16
 */
@Service
public class DictionaryService implements AbstractService<DictionaryDomain, String> {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public DictionaryMapper getMapper() {
        return dictionaryMapper;
    }

    /**
     * 根据字典项编码查找数值
     *
     * @param itemCode
     * @return
     */
    public BigDecimal findValueByCode(String itemCode) {
        return dictionaryMapper.findValueByCode(itemCode);
    }

    /**
     * 根据字典的父级id得到Map
     *
     * @param itemCode
     * @return
     */
    public Map<String, String> findByParentCode(String itemCode) {
        Map<String, String> map = new HashMap<>(8);
        List<DictionaryDomain> dictionaryDomains = dictionaryMapper.findByParentItemCode(itemCode);
        if (dictionaryDomains == null) {
            return null;
        }
        for (DictionaryDomain dictionaryDomain : dictionaryDomains) {
            map.put(dictionaryDomain.getItemCode(), dictionaryDomain.getItemName());
        }
        return map;
    }
}
