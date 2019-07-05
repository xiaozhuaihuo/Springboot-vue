package com.fengyaodong.bloan.common.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 9:53
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 9:53
 */
public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
