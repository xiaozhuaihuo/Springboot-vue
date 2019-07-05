package com.fengyaodong.bloan.dao;

import org.apache.commons.lang3.StringUtils;

/**
 * ${TODO} 写点注释吧
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/7 18:17
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/7 18:17
 */
public class Ognl {

    public Ognl() {
    }

    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        return o instanceof String ? StringUtils.isBlank((String) o) : null == o;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotEmpty(Long o) {
        return !isEmpty(o);
    }

    public static boolean isNumber(Object o) {
        return o instanceof Number;
    }
}
