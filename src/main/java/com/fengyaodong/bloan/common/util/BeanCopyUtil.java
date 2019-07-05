package com.fengyaodong.bloan.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Bean属性拷贝工具类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/9 19:03
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/9 19:03
 */
public class BeanCopyUtil {

    /**
     * 忽略null属性
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        if (source instanceof Map) {
            return getNullPropertyNames((Map<String, Object>) source);
        }

        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];

        return emptyNames.toArray(result);
    }

    /**
     * 忽略null属性
     *
     * @param source
     * @return
     */
    public static <T> String[] getNullPropertyNames(Map<String, T> source) {
        Set<String> emptyNames = new HashSet<String>();
        for (final Map.Entry<String, T> entry : source.entrySet()) {
            final Object srcValue = entry.getValue();
            if (srcValue == null) {
                emptyNames.add(entry.getKey());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 属性copy
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 属性copy
     *
     * @param src    Map
     * @param target
     */
    public static void copyProperties(Object src, Object target, String... ignoreProperties) {
        if (src instanceof Map) {
            copyProperties((Map<String, Object>) src, target, ignoreProperties);
        } else {
            BeanUtils.copyProperties(src, target, ignoreProperties);
        }
    }

    /**
     * 属性copy
     *
     * @param src    Map
     * @param target
     */
    public static <T> void copyProperties(Map<String, T> src, Object target, String... ignoreProperties) {
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        for (final Map.Entry<String, ?> entry : src.entrySet()) {
            final String name = entry.getKey();
            final Object value = entry.getValue();
            PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(target.getClass(), name);
            Method writeMethod;
            if (targetPd != null && (writeMethod = targetPd.getWriteMethod()) != null && (ignoreList == null
                    || !ignoreList.contains(targetPd.getName()))) {
                try {
                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                        writeMethod.setAccessible(true);
                    }
                    writeMethod.invoke(target, value);
                } catch (Throwable ex) {
                    throw new FatalBeanException(
                            "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                }
            }
        }
    }

}
